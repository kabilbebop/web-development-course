import React from 'react';
import { shallow } from 'enzyme';

import { Nav, Navbar } from 'reactstrap';

import { Brand, Home } from 'src/main/webapp/app/header/header-components';
import Header from 'src/main/webapp/app/header/header';

describe('Header', () => {
  let mountedWrapper;

  const userProps = {
    menuOpen: false,
    top10Click: () => {}
  };

  const wrapper = (props = userProps) => {
    if (!mountedWrapper) {
      mountedWrapper = shallow(<Header {...props} />);
    }
    return mountedWrapper;
  };

  beforeEach(() => {});

  // All tests will go here
  it('Renders a Header component in dev profile with LoadingBar, Navbar, Nav and dev ribbon.', () => {
    const component = wrapper();
    // the created snapshot must be committed to source control
    expect(component).toMatchSnapshot();
    const navbar = component.find(Navbar);
    expect(navbar.length).toEqual(1);
    expect(navbar.find(Brand).length).toEqual(1);
    const nav = component.find(Nav);
    expect(nav.length).toEqual(1);
    expect(nav.find(Home).length).toEqual(1);
    const ribbon = component.find('.ribbon.dev');
    expect(ribbon.length).toEqual(1);
  });

  it('Renders a Header component in prod profile with LoadingBar, Navbar, Nav.', () => {
    const component = wrapper(userProps);
    // the created snapshot must be committed to source control
    expect(component).toMatchSnapshot();
    const navbar = component.find(Navbar);
    expect(navbar.length).toEqual(1);
    expect(navbar.find(Brand).length).toEqual(1);
    const nav = component.find(Nav);
    expect(nav.length).toEqual(1);
    expect(nav.find(Home).length).toEqual(1);
    const ribbon = component.find('.ribbon.dev');
    expect(ribbon.length).toEqual(0);
  });

  it('Renders a Header component in prod profile with logged in User', () => {
    const nav = wrapper(userProps).find(Nav);
  });
});
