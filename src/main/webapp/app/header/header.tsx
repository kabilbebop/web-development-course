import './header.css';
import React from 'react';
import { Navbar, Nav, NavbarToggler, NavbarBrand, Collapse } from 'reactstrap';
import { Home, Brand } from './header-components';

export interface IHeaderState {
  menuOpen: boolean;
}

export default class Header extends React.Component<IHeaderState> {
  state: IHeaderState = {
    menuOpen: false
  };

  private toggleMenu = () => {
    this.setState({ menuOpen: !this.state.menuOpen });
  };

  public render() {
    return (
      <div id="app-header">
        <Navbar dark expand="sm" fixed="top" className="jh-navbar">
          <NavbarToggler aria-label="Menu" onClick={this.toggleMenu} />
          <Brand />
          <Collapse isOpen={this.state.menuOpen} navbar>
            <Nav id="header-tabs" className="ml-auto" navbar>
              <Home />
            </Nav>
          </Collapse>
        </Navbar>
      </div>
    );
  }
}
