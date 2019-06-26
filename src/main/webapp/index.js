const FILTER_INPUT_TIMEOUT = 500;

let cars, filterInputTimeout, previousFilterValue, searchFilter;

function refreshCars() {
  /* fetch(`api/cars?cacheBuster=${new Date().getTime()}`).then(response => { */
  cars = mockdata;/* response ? response.data : undefined; */
  /* }); */
  const appContent = document.querySelector('.app-content');
  appContent.innerHTML = '';
  let previousModel;
  let carComponent;
  let variants = [];
  cars.forEach(car => {
    if (!previousModel || previousModel !== car.model.name) {
      if (previousModel && previousModel !== car.model.name) {
        carComponent.variants = variants; // push property before creating new element
      }
      carComponent = document.createElement('car-component');
      carComponent.classList.add('card');
      carComponent.setAttribute('brand', car.model.manufacturer.name)
      carComponent.setAttribute('model', car.model.name);
      variants = [];
    }
    variants.push({
      variant: car.variant,
      year: car.startDate ? car.startDate.match(/\d{4}/g)[0] : undefined,
      ratio: Math.round(car.realWeight / car.power),
      weight: car.realWeight,
      power: car.power
    });
    appContent.appendChild(carComponent);
    previousModel = car.model.name;
  });
  carComponent.variants = variants; // push variants for last element 
}

function searchCars() {
  const input = document.getElementById('searchFilter');
  if (input.value !== previousFilterValue) {
    clearTimeout(filterInputTimeout);
    const timer = setTimeout(() => {
      if (input.value && input.value.trim() !== '') {
        fetch('api/cars/search/' + input.value).then(response => cars = response.data);
      } else {
        this.getCars();
      }
    }, FILTER_INPUT_TIMEOUT);
    filterInputTimeout = timer;
    previousFilterValue = input.value;
  }
}

function top10Click(what) {
  fetch(`api/cars/top/${what}/10`).then(response => cars = response.data);
}


const header = document.querySelector('header-component');
// header. = () => {
//   console.log('', header.shadowRoot)
//   const top10Weight = header.shadowRoot.querySelector('.top10-weight');
//   top10Weight.addEventListener('click', _event => console.log(_event) /* document.querySelector('car-component').setAttribute('model', 'TOTO') */);
// };

document.addEventListener('DOMContentLoaded', () => refreshCars());



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




