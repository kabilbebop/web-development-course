import getTemplate from '/app/util.js';

export class ModelComponent extends HTMLElement {

  static get observedAttributes() {
    return ['brand', 'model', 'image', 'imageUrl'];
  }

  get variants() {
    return this._variants;
  }

  set variants(value) {
    this._variants = value;
    this.templatePromise.then(() => {
      this._variants.forEach(variant => {

        // object variant to insert as child element
        const carVariantComponent = document.createElement('car-component');
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
    this.templatePromise = getTemplate('/app/model/model.html').then(template => {
      this.shadowRoot.appendChild(template.content.cloneNode(true));
    });
    this._variants = [];
  }

  attributeChangedCallback(name, _oldValue, newValue) {
    this.templatePromise.then(() => {
      if (newValue !== 'undefined') {
        this.shadowRoot.querySelector(`.${name}`).innerText = newValue;
        if (name === 'image' && newValue) {
          const img = this.shadowRoot.querySelector('img');
          img.src = 'data:image/png;base64, ' + newValue;
        }
        if (name === 'imageUrl' && newValue) {
          const imageLink = this.shadowRoot.querySelector('image-link');
          imageLink.href = newValue;
        }
      }
    });
  }
}

customElements.define('model-component', ModelComponent);
