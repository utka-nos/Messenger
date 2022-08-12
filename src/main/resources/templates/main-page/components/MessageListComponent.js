import React from "react";
import Button from "react-bootstrap/Button";
import Card from 'react-bootstrap/Card';
import Modal from "react-bootstrap/Modal";
import Dropdown from 'react-bootstrap/Dropdown';
import Form from 'react-bootstrap/Form';
import Alert from 'react-bootstrap/Alert';
import Image from "react-bootstrap/Image";



export default class MessageListComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            // Id Текущего открытого модального окна для удаления сообщения
            messageDelete : null,
            // Id текущего выпадающего списка
            dropdownMenuId : null,
            // Флаг, показывающий, находится ли курсор в пределах dropdown сообщения
            dropdownMenuGone : true,
            // сообщение, которое будем редактировать
            messageEdit : null,
            // текущий текст который мы изменяем
            messageEditText : "",
            // Сообщения об ошибках в Modal
            errorMessage : null
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.state.messageEdit == null && prevState.messageEdit != null) {
            this.setState({errorMessage : null})
        }
        if (this.state.messageDelete == null && prevState.messageDelete != null) {
            this.setState({errorMessage : null})
        }
    }

    render() {
        if (this.props.messages.length !== 0) {
            return (
                <div>
                    {this.deleteMessageModal()}
                    {this.editMessageModal()}
                    {this.props.messages.map(message => {
                        return(
                            <Card className="my-2 mx-auto w-auto shadow" key={message.id}>
                                <Card.Header className="d-flex justify-content-between">
                                    {this.getUserPic(message.authorId)}
                                    <div className="d-inline-block me-auto ms-2 w-50">
                                        {this.getAuthorLogin(message.authorId)}
                                    </div>
                                    <div className="d-inline-block my-auto" >
                                        {this.dropdownMenu(message)}
                                    </div>
                                </Card.Header>
                                <Card.Body>
                                    <Card.Text>
                                        {message.text}
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        )
                    })}

                </div>
            )
        }
        else return (
            <div>
                No messages yet!
            </div>
        )
    }

    deleteMessageModal = () => {
        if (this.state.messageDelete != null) {
            return (
                <Modal show="true" onHide={() => this.setState({messageDelete: null})}>
                    <Modal.Header closeButton>
                        <Modal.Title>Delete this message?</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Card>
                            <Card.Header>{this.state.messageDelete.authorId}</Card.Header>
                            <Card.Body>{this.state.messageDelete.text}</Card.Body>
                        </Card>
                        {/* Error message */}
                        {this.getErrorMessage()}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={this.deleteMessage.bind(this, this.state.messageDelete.id)}>
                            Delete
                        </Button>
                        <Button onClick={() => this.setState({messageDelete: null})}>
                            Cancel
                        </Button>
                    </Modal.Footer>
                </Modal>
            )
        }
        else {
            return (
                <div/>
            )
        }
    }

    getUserPic = (userId) => {
        if (this.props.users === null || this.props.users.length === 0) {
            return (
                <svg className="ms-0 me-1" width="24px" height="24px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <g id="Iconly/Curved/2 User">
                        <g id="2 User">
                            <path id="Stroke 1" fillRule="evenodd" clipRule="evenodd" d="M9.55851 21.4562C5.88651 21.4562 2.74951 20.9012 2.74951 18.6772C2.74951 16.4532 5.86651 14.4492 9.55851 14.4492C13.2305 14.4492 16.3665 16.4342 16.3665 18.6572C16.3665 20.8802 13.2505 21.4562 9.55851 21.4562Z" stroke="#130F26" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
                            <path id="Stroke 3" fillRule="evenodd" clipRule="evenodd" d="M9.55849 11.2776C11.9685 11.2776 13.9225 9.32356 13.9225 6.91356C13.9225 4.50356 11.9685 2.54956 9.55849 2.54956C7.14849 2.54956 5.19449 4.50356 5.19449 6.91356C5.18549 9.31556 7.12649 11.2696 9.52749 11.2776H9.55849Z" stroke="#130F26" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
                            <path id="Stroke 5" d="M16.8013 10.0789C18.2043 9.70388 19.2383 8.42488 19.2383 6.90288C19.2393 5.31488 18.1123 3.98888 16.6143 3.68188" stroke="#130F26" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
                            <path id="Stroke 7" d="M17.4608 13.6536C19.4488 13.6536 21.1468 15.0016 21.1468 16.2046C21.1468 16.9136 20.5618 17.6416 19.6718 17.8506" stroke="#130F26" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
                        </g>
                    </g>
                </svg>
            )
        }
        else {
            let users = this.props.users
            let curUser = users.filter((user) => {
                return user.id === userId
            })[0]
            return (
                <Image width="24px" height="24px" style={{borderRadius : "2px"}} src={curUser.defaultUserPic === null ? curUser.userPic : curUser.defaultUserPic}/>
            )
        }
    }

    editMessageModal = () => {
        if (this.state.messageEdit != null) {
            return (
                <Modal show="true" onHide={() => this.setState({messageEdit : null, messageEditText : ""})}>
                    <Modal.Header closeButton>
                        <Modal.Title>Edit message</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Card>
                            <Card.Header>{this.state.messageEdit.authorId}</Card.Header>
                            <Card.Body>
                                <Form.Control
                                    as="textarea"
                                    onChange={(e) =>
                                        this.setState({messageEditText : e.target.value})}
                                    value={this.state.messageEditText}/>
                            </Card.Body>
                        </Card>
                        {/* Error message */}
                        {this.getErrorMessage()}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={this.updateMessage.bind(this, this.state.messageEdit, this.state.messageEditText)}>
                            Save
                        </Button>
                        <Button onClick={() => this.setState({messageEdit : null, messageEditText : ""})}>
                            Cancel
                        </Button>
                    </Modal.Footer>
                </Modal>
            )
        }
        else {
            return (
                <div/>
            )
        }
    }

    getErrorMessage = () => {
        if (this.state.errorMessage != null) {
            return (
                <Alert variant={"danger"} className="mt-2 mb-0" dismissible onClose={() => this.setState({errorMessage : null})}>
                    {this.state.errorMessage}
                </Alert>
            )
        }
    }

    messageMenu = (message) => (
        <Dropdown.Menu onClick={() => this.setState({dropdownMenuGone : true, dropdownMenuId : null})}>
            <Dropdown.Item onClick={() => this.setState({messageDelete: message})}>
                Delete
            </Dropdown.Item>
            <Dropdown.Item onClick={() => this.setState({messageEdit : message, messageEditText : message.text})}>
                Edit
            </Dropdown.Item>
        </Dropdown.Menu>
    )

    dropdownMenu = (message) => (
        <Dropdown style={{width : "20px"}}
                  onMouseEnter={() => this.setState({dropdownMenuId : message.id, dropdownMenuGone : false})}
                  onMouseLeave={() => {
                      this.setState({dropdownMenuGone : true})
                      setTimeout(() => {if(this.state.dropdownMenuGone === true)
                          this.setState({dropdownMenuId : null})}, 300)
                  }}
                  show={this.state.dropdownMenuId != null && this.state.dropdownMenuId === message.id}>
            <Dropdown.Toggle  bsPrefix="div"
                              as={"div"}
                              className="px-2 border-0 my-auto"
                              style={{color : "gray"}}
                              hidden={frontendData.profile.id !== message.authorId}>

                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                     fill="currentColor" className="bi bi-three-dots" viewBox="0 0 16 16">
                    <path
                        d="M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3z"/>
                </svg>
            </Dropdown.Toggle>

            {this.messageMenu(message)}
        </Dropdown>
    )

    getAuthorLogin = (userId) => {
        if (this.props.users === null || this.props.users.length === 0) return (<div>{userId}</div>)
        else {
            let user = this.props.users.filter(user => user.id === userId)[0]
            return (<div>{user.login}</div>)
        }
    }

    updateMessage(message, newText) {
        let oldText = message.text;
        message.text = newText
        fetch("http://localhost:8080/api/v1/message/" + message.id.toString(), {
            method : "PUT",
            headers : {
                "Accept" : "application/json",
                "Content-Type" : "application/json"
            },
            body : JSON.stringify(message)
        })
            .then(res => {
                if (res.ok) {
                    this.setState({messageEdit : null, messageEditText : ""})
                } else {
                    message.text = oldText
                    res.json()
                        .then(error => this.setState({messageEditText : oldText, errorMessage : error.text}))
                }
            })
    }

    deleteMessage(messageId) {
        fetch("http://localhost:8080/api/v1/message/" + messageId.toString(), {
            method : "DELETE",
            headers : {
                "Accept" : "application/json",
                "Content-Type" : "application/json"
            }
        })
            .then(res => {
                if (res.status === 204) {
                    let newMessages = this.props.messages
                    let indexToDelete = newMessages.findIndex(item => item.id === messageId);
                    if(indexToDelete !== -1) {
                        newMessages.splice(indexToDelete, 1)
                        this.props.setMessages(newMessages)
                    }
                    this.setState({messageDelete: null})
                }
                else if (!res.ok) {
                    res.json().then(error => this.setState({errorMessage : error.text}))
                }
            })
    }


}