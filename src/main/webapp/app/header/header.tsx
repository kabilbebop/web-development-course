import './header.css';
import React from 'react';
import { Collapse, Nav, Navbar, NavbarBrand, NavbarToggler } from 'reactstrap';
import { Brand, Home, Top10Power, Top10Ratio, Top10Weight } from './header-components';

export interface IHeaderState {
  menuOpen: boolean;
}

export interface IHeaderProps {
  top10Click: any;
  menuOpen: any;
}

export default class Header extends React.Component<IHeaderProps, IHeaderState> {
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
              <Top10Weight onClick={() => this.props.top10Click('weight')} />
              <Top10Power onClick={() => this.props.top10Click('power')} />
              <Top10Ratio onClick={() => this.props.top10Click('ratio')} />
              <Home />
            </Nav>
          </Collapse>
        </Navbar>
      </div>
    );
  }
}
