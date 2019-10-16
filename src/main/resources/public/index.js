
const POWER_MAX = 1000; // max 1000hp, else overflow
const WEIGHT_MAX = 2500; // max 2500kg, else overflow
const RATIO_MAX = 1; // max 1kg/hp else overflow

const data = [{
  "id": 160,
  "name": "FORD",
  "models": [
    {
      "id": 1601400,
      "name": "MUSTANG",
      "cars": [
        {
          "id": 16014004,
          "name": "SHELBY GT500",
          "power": 547.0,
          "weight": 1789.0,
          "startDate": "2011-01-01",
          "imageUrl": "http://www.regcheck.org.uk/image.aspx/@Zm9yZCBtdXN0YW5n"
        }
      ]
    }
  ]
},
{
  "id":40,
  "name":"AUDI",
  "models":[
    {
      "id":402000,
      "name":"R8",
      "cars":[
        {
          "id":40200016,
          "name":"V8 S-STRONIC",
          "power":430.0,
          "weight":1666.0,
          "startDate": "2008-01-01",
          "imageUrl":"http://www.regcheck.org.uk/image.aspx/@QVVESSBSOCBWOCBSLVRST05JQw=="
        }
      ]
    },
    {
      "id":401700,
      "name":"RS4",
      "cars":[
        {
          "id":4017005,
          "name":"AVANT",
          "power":420.0,
          "weight":1728.0,
          "startDate":"2006-01-01",
          "imageUrl":"http://www.regcheck.org.uk/image.aspx/@YXVkaSByczQgYjc="
        },
        {
          "id":4017006,
          "name":"AVANT",
          "power":450.0,
          "weight":1867.0,
          "startDate":"2013-01-01",
          "imageUrl":"http://www.regcheck.org.uk/image.aspx/@YXVkaSByczQ="
        }
      ]
    }
  ]
}];


document.addEventListener('DOMContentLoaded', () => {

  
  const modelContents = document.querySelectorAll('.model-content');
  let i = 0;
  
  data.forEach(brand => {

    brand.models.forEach(model => {

      const modelContent = modelContents.item(i++);

      modelContent.querySelector('.brand').innerText = brand.name;
  
      modelContent.querySelector('.model').innerText = model.name;
    
      modelContent.querySelector('.image-link').src = model.cars[0].imageUrl;
    
      modelContent.querySelector('.image').src = model.cars[0].imageUrl;
  
      const carContents = modelContent.querySelectorAll('.car-content');
      
      let j = 0;
      model.cars.forEach(car => {
        
        const carContent = carContents.item(j++);
        car = {
          ...car,
          year: car.startDate.match(/\d{4}/g)[0],
          ratio: Math.round(car.weight * 10 / car.power) / 10,
        };
    
        carContent.querySelector('.car').innerText = car.name;
    
        carContent.querySelector('.year').innerText = car.year;
    
        carContent.querySelector('.weight').innerText = car.weight;
    
        carContent.querySelector('.power').innerText = car.power;
    
        carContent.querySelector('.ratio').innerText = car.ratio;
    
        carContent.querySelector('.bar-power').style.width = (car.power * 100 / POWER_MAX) + '%';
    
        carContent.querySelector('.bar-weight').style.width = (car.weight * 100 / WEIGHT_MAX) + '%';
    
        carContent.querySelector('.bar-ratio').style.width = (RATIO_MAX * 100 / car.ratio) + '%';
      });

    });

  });



});
