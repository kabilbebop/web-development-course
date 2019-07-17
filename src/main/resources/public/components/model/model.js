import getTemplate from '/components/util.js';

export class ModelComponent extends HTMLElement {

  static get observedAttributes() {
    return ['brand', 'model', 'image', 'image-link'];
  }

  get cars() {
    return this._cars;
  }

  set cars(value) {
    this._cars = value;
    this.templatePromise.then(() => {
      this._cars.forEach(car => {

        // object variant to insert as child element
        const carVariantComponent = document.createElement('car-component');
        carVariantComponent.setAttribute('variant', `${this.getAttribute('model')} ${car.variant}`);
        carVariantComponent.setAttribute('year', car.year);
        carVariantComponent.setAttribute('ratio', car.ratio);
        carVariantComponent.setAttribute('weight', car.weight);
        carVariantComponent.setAttribute('power', car.power);
        this.shadowRoot.querySelector('.variant-placeholder').appendChild(
            carVariantComponent);

      });
    });
  }

  constructor() {
    super();
    this.attachShadow({mode: 'open'});
    this.templatePromise = getTemplate('/components/model/model.html').then(template => {
      this.shadowRoot.appendChild(template.content.cloneNode(true));
    });
    this._cars = [];
  }

  attributeChangedCallback(name, _oldValue, newValue) {
    this.templatePromise.then(() => {
      if(name && name != '' && newValue && newValue !== 'undefined') {
        switch (name) {

          case 'image':
          //   const img = this.shadowRoot.querySelector('img');
          //   img.src = 'data:image/png;base64, ' + newValue;
            break;

          case 'image-link':
            const imageLink = this.shadowRoot.querySelector(`.${name}`);
            imageLink.href = newValue;
            const img = this.shadowRoot.querySelector('img');
            img.src = newValue;
            break;
        
          default:
            this.shadowRoot.querySelector(`.${name}`).innerText = newValue;
            break;
        }
      }
    });
  }
}

customElements.define('model-component', ModelComponent);
