import React from 'react';
import './model.css';

export default function ModelComponent(props) {

  //       // object car to insert as child element
  //       const carComponent = document.createElement('car-component');
  //       carComponent.setAttribute('name', `${this.getAttribute('model')} ${car.name}`);
  //       carComponent.setAttribute('year', car.year);
  //       carComponent.setAttribute('ratio', car.ratio);
  //       carComponent.setAttribute('weight', car.weight);
  //       carComponent.setAttribute('power', car.power);
  //       this.shadowRoot.querySelector('.car-content').appendChild(
  //           carComponent);
  console.log(props);

  return (
    <article className="card">
      <link rel="stylesheet" href="components/model/model.css" />
      <h3><span className="brand">{props.brand.name}</span>&nbsp;<span className="model">{props.brand.models[0].name}</span></h3>
      <a href={props.brand.models[0].cars[0].imageUrl} className="image-link">
        <img alt="car" src={props.brand.models[0].cars[0].imageUrl} />
      </a>
      <section className="car-content">

      </section>
    </article>);
}
