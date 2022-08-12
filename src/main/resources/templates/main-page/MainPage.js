import React from 'react'
import "./main.css"
import {addHandler, connect} from "../utils/WebSocketUtils";
import Button from "react-bootstrap/Button";
import InputGroup from "react-bootstrap/InputGroup"
import FormControl from "react-bootstrap/FormControl"
import Container from "react-bootstrap/Container"
import Modal from 'react-bootstrap/Modal';
import MessageListComponent from "./components/MessageListComponent";
import NavBarComponent from "../general/NavBarComponent";

export default class MainPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            inputValue : "",
            messages : [],
            toastMessage : null,
            users : []
        }
    }

    componentDidMount() {
        // подключаемся к топику WebSocket только если авторизировались
        // после подключения сразу же загружаем сообщения
        if (frontendData !== null && frontendData.profile !== null) {
            connect();
            this.updateMessages()
            this.updateUsers()
        }

        // Обработчик входных сообщений через WebSocket
        addHandler(data => {
            let eventType = data.eventType
            let body = data.body
            let objectType = data.objectType;

            switch (objectType) {
                case "MESSAGE" : {
                    this.handleMessageWS(eventType, body)
                    break
                }
                default : {
                    console.error("unknown object type")
                    break
                }
            }
        })
    }

    // если сообщения подгружались, то переворачиваем их, чтобы последнее было ближе к форме
    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.state.messages !== prevState.messages) {
            let messages = this.state.messages
            messages.sort((a,b)=> -(a.id - b.id))
            this.setState(() => {
                return {messages : messages}
            })
        }
    }

    // обработчик сообщений от WS типа MESSAGE
    handleMessageWS(eventType, body) {
        switch (eventType) {
            case "CREATE" : {
                let messages = this.state.messages;
                // insert into the start
                messages.unshift(body)
                this.setState({
                    messages : messages
                })
                break
            }
            case "DELETE" : {
                let messages = this.state.messages
                let index = messages.findIndex(item => item.id === body.id)
                if (index !== -1) {
                    messages.splice(index, 1)
                    this.setState({messages : messages})
                }
                break
            }
            case "UPDATE" : {
                let messages = this.state.messages
                let updatedMessage = messages.find(item => item.id === body.id)
                updatedMessage.text = body.text
                this.setState({messages:messages})
                break
            }
            default: {
                console.log("Unknown eventType: " + eventType)
            }
        }
    }

    // загружает все сообщения
    updateMessages() {
        fetch("http://localhost:8080/api/v1/message", {
            method : "GET",
            headers : {
                "ACCEPT" : "application/json",
                "Content-Type" : "application/json"
            }
        })
            .then(res => res.json())
            .then(res => {
                this.setState({
                    messages : res
                })
            })
    }

    updateUsers() {
        fetch("http://localhost:8080/api/v1/user/all", {
            method : "GET",
            headers : {
                "Accept" : "application/json"
            }
        })
            .then(res => res.json())
            .then(data => {
                this.setState({users : data})
            })
    }

    onClickSendMessage() {
        if (this.state.inputValue === "") return
        fetch("http://localhost:8080/api/v1/message", {
            method: "POST",
            headers : {
                "ACCEPT" : "application/json",
                "Content-Type" : "application/json"
            },
            body : JSON.stringify({
                "text" : this.state.inputValue
            })
        })
            .then(res => {
                if (res.status === 201) {
                    this.setState({
                        inputValue : ""
                    })
                }
                else if (res.status <= 599 && res.status >= 400) {
                    res.json().then(body => {
                        this.setState({toastMessage : body.text})
                    })
                }
            })

    }

    getErrorToast = () => {
        return(
            <Modal show={this.state.toastMessage !== null} onHide={() => this.setState({toastMessage : null})}>
                <Modal.Header closeButton>
                    <Modal.Title>
                        Error!
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {this.state.toastMessage}
                </Modal.Body>
            </Modal>
        )
    }

    render() {
        return (
            <div>
                {/*   панель навигации   */}
                <NavBarComponent />

                <Container style={{maxWidth : "35rem"}} className="mx-auto mb-3">
                    <InputGroup className="my-3 " >
                        <FormControl placeholder="message..."
                                     value={this.state.inputValue}
                                     onChange={(e) => this.setState({inputValue : e.target.value})}/>
                        <Button
                            className="btn-secondary"
                            disabled={frontendData.profile == null}
                            onClick={this.onClickSendMessage.bind(this)}>
                            send
                        </Button>
                        <Button
                            disabled={frontendData.profile == null}
                            onClick={this.updateMessages.bind(this)}>
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 className="bi bi-arrow-counterclockwise" viewBox="0 0 16 16">
                                <path fillRule="evenodd"
                                      d="M8 3a5 5 0 1 1-4.546 2.914.5.5 0 0 0-.908-.417A6 6 0 1 0 8 2v1z"/>
                                <path
                                    d="M8 4.466V.534a.25.25 0 0 0-.41-.192L5.23 2.308a.25.25 0 0 0 0 .384l2.36 1.966A.25.25 0 0 0 8 4.466z"/>
                            </svg>
                        </Button>
                    </InputGroup>

                    {/*  List of Messages  */}
                    <MessageListComponent messages={this.state.messages}
                                          setMessages={(newMessages) => this.setState({messages : newMessages})}
                                          users={this.state.users}/>

                    {/*   error info   */}
                    {this.getErrorToast()}
                </Container>
            </div>
        );
    }


}