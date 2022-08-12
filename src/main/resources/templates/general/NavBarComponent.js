import React from "react";
import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";

export default class NavBarComponent extends React.Component {

    constructor(props) {
        super(props);

        this.state = {

        }
    }


    render() {
        return(
            <Navbar bg="light" className="mt-0" sticky="top" expand="lg">
                <Container fluid>
                    <Navbar.Brand className="ms-0">
                        {frontendData.profile === null ? "Anonymus" : (frontendData.profile.name == null
                            ? frontendData.profile.login : frontendData.profile.name)}
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                            <Nav.Link href="/"
                                      active={location.pathname === "/"}>Main page</Nav.Link>
                            <Nav.Link hidden={frontendData.profile === null}
                                      href="/profile"
                                      active={location.pathname === "/profile"}>Profile</Nav.Link>
                        </Nav>
                        <Nav className="ms-auto">
                            <Nav.Link hidden={frontendData.profile !== null}
                                      href="/login"
                                      active={location.pathname === "/login"}>Login</Nav.Link>
                            <Nav.Link hidden={frontendData.profile === null} href="/logout">Logout</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        )
    }


}