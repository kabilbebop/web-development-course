import getTemplate from '/components/util.js';

export default class HeaderComponent extends HTMLElement {

  get onMenuSelect() {
    return this._onMenuSelect;
  }

  set onMenuSelect(value) {
    this._onMenuSelect = value;
  }

  constructor() {
    super();
    this.attachShadow({mode: 'open'});
    getTemplate('/components/header/header.html').then(template => {
      this.shadowRoot.appendChild(template.content.cloneNode(true));
    this.shadowRoot.querySelector('.top10-weight').addEventListener('click',() => this._onMenuSelect('weight'));
    this.shadowRoot.querySelector('.top10-power').addEventListener('click', () => this._onMenuSelect('power'));
    this.shadowRoot.querySelector('.top10-ratio').addEventListener('click', () => this._onMenuSelect('ratio'));
  })
    ;

    this._onMenuSelect = function () {}; // callback defined by parent
  }

}

customElements.define('header-component', HeaderComponent);


