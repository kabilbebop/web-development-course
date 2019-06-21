import getTemplate from '/app/util.js';

const NO_YEAR = '0001';
const YEAR_START = 0;
const YEAR_END = 4;
const POWER_PX_RATIO = 3;
const WEIGHT_PX_RATIO = 6;

export class Car {
  constructor(id, variant, power, realWeight, officialWeight, options, startDate, model, image) {
    this.id = id;
    this.variant = variant;
    this.power = power;
    this.realWeight = realWeight;
    this.officialWeight = officialWeight;
    this.options = options;
    this.startDate = startDate;
    this.model = model;
    this.image = image;
  }
}

export class CarComponent extends HTMLElement {
  connectedCallback() {
    var shadow = this.attachShadow({ mode: 'open' });
    this.render(shadow);
  }

  async render(shadow) {

    // Select the template and clone it. Finally attach the cloned node to the shadowDOM's root.
    // Current document needs to be defined to get DOM access to imported HTML
    const template = await getTemplate('/app/car/car.html');
    const instance = template.content.cloneNode(true);

    shadow.appendChild(instance);

  }
}

customElements.define('car-component', CarComponent);
