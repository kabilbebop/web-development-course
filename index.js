const BASE_URL = 'https://sport-cars.cfapps.io/';

function renderCars(data) {

  const modelContent = document.querySelector('.model-content');
  modelContent.innerHTML = '';
  let i = 0;

  data.forEach(brand => {
      brand.models.forEach(model => {
      const modelComponent = document.createElement('model-component');
      modelComponent.classList.add('card');

      // we can push brand and model as attribute because they are strings
      modelComponent.setAttribute('brand', brand.name);
      modelComponent.setAttribute('model', model.name);
      modelComponent.setAttribute('image-url', model.cars[0].imageUrl);

      // push car as property because it is an array
      modelComponent.cars = model.cars.map(car => ({
        name: car.name,
        year: car.startDate ? car.startDate.match(/\d{4}/g)[0] : undefined,
        ratio: Math.round(car.weight * 10 / car.power) / 10,
        weight: car.weight,
        power: car.power,
      }));

      modelContent.appendChild(modelComponent);
    });
  });
}


function searchCars(value) {

  const url = `${BASE_URL}/api/cars${value && value.trim() !== '' ? '/search/' + value : ''}`;
  fetch(url)
    .then(response => response.json())
    .then(data => {
      renderCars(data);
    });
}

function top10Click(what) {

  fetch(`${BASE_URL}/api/cars/top/${what}/10`)
    .then(response => response.json())
    .then(data => {
      renderCars(data);
    });
}

document.addEventListener('DOMContentLoaded', () => {

  // Events binding
  const header = document.querySelector('header-component');
  header.onMenuSelect = menu => {
    if (menu === 'home') {
      searchCars();
    } else {
      top10Click(menu);
    }
  };

  header.onSearchChange = val => {
    searchCars(val);
  };

  // Initialize data
  fetch(`${BASE_URL}/api/cars`)
    .then(response => response.json())
    .then(data => {
      renderCars(data);
    });

});

