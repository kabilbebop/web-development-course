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

const state = new State({
  cars: undefined,
  filterInputTimeout: undefined,
  previousFilterValue: undefined,
  searchFilter: undefined
});

function refreshCars() {
  /* fetch(`api/cars?cacheBuster=${new Date().getTime()}`).then(response => { */
  state.cars = mockdata;/* response ? response.data : undefined; */
  /* }); */
  const appContent = document.querySelector('.app-content');
  appContent.innerHTML = '';
  state.cars.forEach(car => {
    const element = document.createElement("car-component");
    // element.innerText = `${car.model.manufacturer.name} ${car.model.name} ${car.variant}`;
    appContent.appendChild(element);
  });
}

function searchCars() {
  const input = document.getElementById('searchFilter');
  if (input.value !== state.previousFilterValue) {
    clearTimeout(state.filterInputTimeout);
    const timer = setTimeout(() => {
      if (input.value && input.value.trim() !== '') {
        fetch('api/cars/search/' + input.value).then(response => state.cars = response.data);
      } else {
        this.getCars();
      }
    }, FILTER_INPUT_TIMEOUT);
    state.filterInputTimeout = timer;
    state.previousFilterValue = input.value;
  }
}

function top10Click(what) {
  fetch(`api/cars/top/${what}/10`).then(response => state.cars = response.data);
}


const mockdata = [{
  "id": 4030000,
  "variant": "A3 1,8T",
  "power": 150.0,
  "realWeight": 1213.0,
  "officialWeight": 1145.0,
  "options": "",
  "startDate": "2002-01-01",
  "imageUrl": null,
  "image": null,
  "model": {
    "id": 403000,
    "name": "A3",
    "manufacturer": {
      "id": 40,
      "name": "AUDI"
    }
  }
}, {
  "id": 4030001,
  "variant": "A3 2,0 TFSi SPORTBACK",
  "power": 200.0,
  "realWeight": 1485.0,
  "officialWeight": 1410.0,
  "options": "",
  "startDate": "2010-01-01",
  "imageUrl": null,
  "image": null,
  "model": {
    "id": 403000,
    "name": "A3",
    "manufacturer": {
      "id": 40,
      "name": "AUDI"
    }
  }
}, {
  "id": 4030002,
  "variant": "A3 2,0L TFSI SPORTBACK S-LINE",
  "power": 190.0,
  "realWeight": 1432.0,
  "officialWeight": 1315.0,
  "options": "",
  "startDate": "2018-01-01",
  "imageUrl": null,
  "image": null,
  "model": {
    "id": 403000,
    "name": "A3",
    "manufacturer": {
      "id": 40,
      "name": "AUDI"
    }
  }
},];



refreshCars();
