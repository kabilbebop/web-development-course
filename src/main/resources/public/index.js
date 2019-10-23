
const BASE_URL = 'http://localhost:8745';

let loading, searchFilter;

function renderCars(response) {

  const placeholder = document.querySelector('.app-content');

  placeholder.innerHTML = '';

  response.forEach(brand => {
    brand.models.forEach(model => {
      const modelComponent = document.createElement('model-component');
      modelComponent.classList.add('card');

      // we can push brand and model as attribute because they are strings
      modelComponent.setAttribute('brand', brand.name);
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

function searchCars(value) {
    showLoading();
    const url = `${BASE_URL}/api/cars${value && value.trim() !== '' ? '/search/' + value : ''}`;
    fetch(url)
      .then(response => response.json())
      .then(data => {
        renderCars(data);
        hideLoading();
      });
}

function top10Click(what) {
  showLoading();
  fetch(`${BASE_URL}/api/cars/top/${what}/10`)
    .then(response => response.json())
    .then(data => {
      renderCars(data);
      hideLoading();
    });
}

function showLoading() {
  document.querySelector('.loading').style.display = 'block';
  loading = setTimeout(() => {
    if (document.querySelector('.loading').innerText === '...') {
      document.querySelector('.loading').innerText = '';
    } else {
      document.querySelector('.loading').innerText += '.';
    }
  }, 500); 
}

function hideLoading() {
  clearTimeout(loading);
  document.querySelector('.loading').innerText = '';
  document.querySelector('.loading').style.display = 'none';
}

document.addEventListener('DOMContentLoaded', () => {
  showLoading();

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
      hideLoading();
    });
});
