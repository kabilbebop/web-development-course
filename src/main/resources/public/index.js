const FILTER_INPUT_TIMEOUT = 500;
const BASE_URL = 'http://localhost:8745';

let filterInputTimeout, previousFilterValue, searchFilter;

function renderCars(cars) {

  const placeholder = document.querySelector('.app-content');

  placeholder.innerHTML = '';

  cars.forEach(brand => {
    brand.models.forEach(model => {
      const modelComponent = document.createElement('model-component');
      modelComponent.classList.add('card');

      // we can push brand and model as attribute because they are strings
      modelComponent.setAttribute('brand', brand.name)
      modelComponent.setAttribute('model', model.name);
      modelComponent.setAttribute('image', model.cars[0].image);
      modelComponent.setAttribute('image-link', model.cars[0].imageUrl);

      // push variants as property because it is an array
      modelComponent.cars = model.cars.map(car => ({
        variant: car.variant,
        year: car.startDate ? car.startDate.match(/\d{4}/g)[0] : undefined,
        ratio: Math.round(car.realWeight * 10 / car.power) / 10,
        weight: car.realWeight,
        power: car.power,
      }));

      placeholder.appendChild(modelComponent);
    });
  });
}

function searchCars() {
  const input = document.getElementById('searchFilter');
  if (input.value !== previousFilterValue) {
    clearTimeout(filterInputTimeout);
    const timer = setTimeout(() => {
      if (input.value && input.value.trim() !== '') {
        fetch('api/cars/search/' + input.value).then(response => response.json()).then(data => renderCars(data));
      } else {
        this.getCars();
      }
    },
      FILTER_INPUT_TIMEOUT
    );
    filterInputTimeout = timer;
    previousFilterValue = input.value;
  }
}

function top10Click(what) {
  fetch(`${BASE_URL}/api/cars/top/${what}/10`).then(response => response.json()).then(data => renderCars(data));
}

document.addEventListener('DOMContentLoaded', () => {

  // Header events binding
  const header = document.querySelector('header-component');
  header.onMenuSelect = menu => {
    top10Click(menu);
  };

  fetch(`${BASE_URL}/api/cars`).then(response => response.json()).then(data => renderCars(data));
});

const mockdata = [{
  "id": 40,
  "name": "AUDI",
  "models": [{
    "id": 403000,
    "name": "A3",
    "variants": [{
      "id": 4030000,
      "name": "A3 1,8T",
      "power": 150.0,
      "realWeight": 1213.0,
      "officialWeight": 1145.0,
      "options": "",
      "startDate": "2002-01-01",
    }, {
      "id": 4030001,
      "name": "A3 2,0 TFSi SPORTBACK",
      "power": 200.0,
      "realWeight": 1485.0,
      "officialWeight": 1410.0,
      "options": "",
      "startDate": "2010-01-01",
    },
    {
      "id": 4030002,
      "name": "A3 2,0L TFSI SPORTBACK S-LINE",
      "power": 190.0,
      "realWeight": 1432.0,
      "officialWeight": 1315.0,
      "options": "",
      "startDate": "2018-01-01",
    }]
  },
  {
    "id": 403000,
    "name": "A4",
    "variants": [
      {
        "id": 4030003,
        "name": "A4 3,0L TFSI SPORTBACK S-LINE",
        "power": 300.0,
        "realWeight": 1532.0,
        "officialWeight": 1415.0,
        "options": "",
        "startDate": "2018-01-01",
      }
    ]
  }]
},
{
  "id": 41,
  "name": "PEUGEOT",
  "models": [{
    "id": 403000,
    "name": "308",
    "variants": [
      {
        "id": 4030004,
        "name": "308 GTI",
        "power": 270.0,
        "realWeight": 1232.0,
        "officialWeight": 1215.0,
        "options": "",
        "startDate": "2018-01-01",
        "imageUrl": null,
        "image": null,

      }
    ]
  }]
}];




