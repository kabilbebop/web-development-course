import React from 'react';
import { DropdownMenu, DropdownToggle, NavbarBrand, NavItem, NavLink, UncontrolledDropdown } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const BrandIcon = props => (
  <div {...props} className="brand-icon">
    <img src="content/images/logo.jpg" alt="Logo" />
  </div>
);

export const Brand = props => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    <BrandIcon />
    <span className="brand-title">Weight Cars</span>
  </NavbarBrand>
);

export const Home = props => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" />
      <span>Home</span>
    </NavLink>
  </NavItem>
);

export const Top10Weight = props => (
  <NavItem>
    <NavLink className="d-flex align-items-center" onClick={() => props.onClick()}>
      <FontAwesomeIcon icon="weight-hanging" />
      <span>TOP 10 Weight</span>
    </NavLink>
  </NavItem>
);

export const Top10Power = props => (
  <NavItem>
    <NavLink className="d-flex align-items-center" onClick={() => props.onClick()}>
      <FontAwesomeIcon icon="car-battery" />
      <span>TOP 10 Power</span>
    </NavLink>
  </NavItem>
);

export const Top10Ratio = props => (
  <NavItem>
    <NavLink className="d-flex align-items-center" onClick={() => props.onClick()}>
      <FontAwesomeIcon icon="stopwatch" />
      <span>TOP 10 Ratio</span>
    </NavLink>
  </NavItem>
);
