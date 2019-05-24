import getTemplate from '/app/util.js';

export default class MenuComponent extends HTMLElement {

  connectedCallback() {
    var shadow = this.attachShadow({mode: 'open'});
    this.render(shadow);
  }

  async render(shadow) {
    const template = await getTemplate('/app/menu/menu.html');
    const instance = template.content.cloneNode(true);
    shadow.appendChild(instance);
  }
}

customElements.define('menu-component', MenuComponent);


