import getTemplate from '/app/util.js';

const FILTER_INPUT_TIMEOUT = 500;

export class Manufacturer {
  constructor(id, name) {
    this.id = id;
    this.name = name;
  }
}

export class Model {
  constructor(id, name, manufacturer) {
    this.id = id;
    this.name = name;
    this.manufacturer = manufacturer;
  }
}

export class State {
  constructor(cars, searchFilter, previousFilterValue, filterInputTimeout) {
    this.cars = cars;
    this.searchFilter = searchFilter;
    this.previousFilterValue = previousFilterValue;
    this.filterInputTimeout = filterInputTimeout;
  }
}

export default class AppComponent extends HTMLElement {
  constructor(props) {
    super(props);
    this.state = {
      cars: undefined,
      filterInputTimeout: undefined,
      previousFilterValue: undefined,
      searchFilter: undefined
    };
    //this.getCars();
  }

  getCars() {
    fetch(`api/cars?cacheBuster=${new Date().getTime()}`).then(response => {
      this.state.cars = response ? response.data : undefined;
    });
  }

  filterCars() {
    const input = document.getElementById('searchFilter');
    if (input.value !== this.state.previousFilterValue) {
      clearTimeout(this.state.filterInputTimeout);
      const timer = setTimeout(() => {
        if (input.value && input.value.trim() !== '') {
          fetch('api/cars/search/' + input.value).then(response => this.setState({ cars: response.data }));
        } else {
          this.getCars();
        }
      }, FILTER_INPUT_TIMEOUT);
      this.setState({ filterInputTimeout: timer });
      this.setState({ previousFilterValue: input.value });
    }
  }

  top10Click(what) {
    fetch(`api/cars/top/${what}/10`).then(response => this.setState({ cars: response.data }));
  }

  connectedCallback() {
    var shadow = this.attachShadow({mode: 'open'});
    this.render(shadow);
  }

  async render(shadow) {

    // Select the template and clone it. Finally attach the cloned node to the shadowDOM's root.
    // Current document needs to be defined to get DOM access to imported HTML
    const template = await getTemplate('/app/app.html');
    const instance = template.content.cloneNode(true);
    shadow.appendChild(instance);

  }
}

customElements.define('app-component', AppComponent);

