import getTemplate from '/app/util.js';

export class HeaderState {
  menuOpen;
}

export class HeaderProps {
  constructor(top10Click, menuOpen) {
    this.top10Click = top10Click;
    this.menuOpen = menuOpen;
  }
}

export default class HeaderComponent extends HTMLElement {
  state = {
    menuOpen: false
  };

  constructor() {
    super();
    this.attachShadow({ mode: 'open' });
    getTemplate('/app/header/header.html').then(template => {
      this.shadowRoot.appendChild(template.content.cloneNode(true));
    });
  }

  toggleMenu = () => {
    this.setState({ menuOpen: !this.state.menuOpen });
  };
}

customElements.define('header-component', HeaderComponent);


