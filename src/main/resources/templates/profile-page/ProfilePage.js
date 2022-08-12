import React from "react";
import NavBarComponent from "../general/NavBarComponent";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card"
import Button from "react-bootstrap/Button";
import MessageListComponent from "../main-page/components/MessageListComponent";

export default class ProfilePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            messages : frontendData.profile.messages,
            users : [frontendData.profile]
        }
    }

    componentDidMount() {
    }

    render() {
        return (
            <div>
                <NavBarComponent/>
                <Container style={{maxWidth : "60rem"}}>
                    <Row className="justify-content-md-center mb-4">
                        <Col md="auto" className="mt-3">
                            <Card className="m-1 h-100 p-4 pb-0" style={{backgroundColor : "#f1f1f1"}}>
                                <Card.Img className="mx-auto my-auto" style={{maxWidth : "15rem", borderRadius : "5px"}} src={this.userPic()} />
                                <Card.Body className="d-grid gap-2 px-4" >
                                    <Button className="w-100 mx-auto" style={{maxWidth : "15rem"}}>
                                        Change photo hgsjdk shjdg hskh jgsh hj hkj hj h j
                                    </Button>

                                    <Button className="w-100 mx-auto" style={{maxWidth : "15rem"}}>
                                        Change photo
                                    </Button>
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col xs className="mt-3">
                            <Card className="m-1 h-100" style={{backgroundColor : "#f1f1f1"}}>
                                <Card.Body>
                                    <Card.Title>
                                        {frontendData.profile.login}
                                    </Card.Title>
                                    <Card.Subtitle>Last seen: online</Card.Subtitle>

                                </Card.Body>
                            </Card>
                        </Col>
                    </Row>

                    <Row className="justify-content-md-center">
                        <Col xs className="m-1">
                            <MessageListComponent messages={this.state.messages}
                                                  users={this.state.users}
                                                  setMessages={(newMessages) => this.setState({messages : newMessages})}/>
                        </Col>
                    </Row>
                </Container>
            </div>
        )
    }

    userPic = () => {
        if (frontendData.profile.userPic !== null) {
            return frontendData.profile.userPic
        }
        else if (frontendData.profile.defaultUserPic !== null) {
            return frontendData.profile.defaultUserPic
        }
        else {
            return "/images/anonym.jpg"
        }
    }

}