import getTemplate from '/app/util.js';

export class CarComponent extends HTMLElement {

  static get observedAttributes() {
    return ['brand', 'model'];
  }

  get variants() {
    return this._variants;
  }

  set variants(value) {
    this._variants = value;
    this.templatePromise.then(() => {
      this._variants.forEach(variant => {

        // object variant to insert as child element
        const carVariantComponent = document.createElement('car-variant-component');
        carVariantComponent.setAttribute('variant', variant.variant);
        carVariantComponent.setAttribute('year', variant.year);
        carVariantComponent.setAttribute('ratio', variant.ratio);
        carVariantComponent.setAttribute('weight', variant.weight);
        carVariantComponent.setAttribute('power', variant.power);
        this.shadowRoot.querySelector('.variant-placeholder').appendChild(carVariantComponent);

      });
    });
  }

  constructor() {
    super();
    this.attachShadow({ mode: 'open' });
    this.templatePromise = getTemplate('/app/car/car.html').then(template => {
      this.shadowRoot.appendChild(template.content.cloneNode(true));
    });
    this._variants = [];
  }

  attributeChangedCallback(name, _oldValue, newValue) {
    this.templatePromise.then(() => {
      if (newValue !== 'undefined') {
        this.shadowRoot.querySelector(`.${name}`).innerText = newValue;
      }
    });
  }
}

customElements.define('car-component', CarComponent);
