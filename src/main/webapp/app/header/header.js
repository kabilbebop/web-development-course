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

  toggleMenu = () => {
    this.setState({ menuOpen: !this.state.menuOpen });
  };

  connectedCallback() {
    var shadow = this.attachShadow({mode: 'open'});
    this.render(shadow);
  }

  async render(shadow) {

    // Select the template and clone it. Finally attach the cloned node to the shadowDOM's root.
    // Current document needs to be defined to get DOM access to imported HTML
    const template = await getTemplate('/app/header/header.html');
    const instance = template.content.cloneNode(true);
    shadow.appendChild(instance);

  }
}

customElements.define('header-component', HeaderComponent);


