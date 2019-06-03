import React from 'react';
import { Card } from 'reactstrap';

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

export class CarComponent {

  render() {
    // Size of the power bar
    const powerStyle = {
      width: `${props.data.power / POWER_PX_RATIO}px`
    };
    // Size of the weight bar
    const weightStyle = {
      width: `${props.data.realWeight / WEIGHT_PX_RATIO}px`
    };
    const year = props.data.startDate && !props.data.startDate.startsWith(NO_YEAR) ? props.data.startDate.slice(YEAR_START, YEAR_END) : null;

    const optionsIcon = props.data.options ? <span class="badge badge-pill badge-info hand">i</span> : '';

    const powerSpan = props.data.power ? (
      <span class="badge badge-danger" style="powerStyle">
        {props.data.power}
        Ch
    </span>
    ) : (
        ''
      );

    const weightSpan = props.data.realWeight ? (
      <span class="badge badge-primary" style="weightStyle">
        {props.data.realWeight}
        Kg (+
      {props.data.realWeight - props.data.officialWeight})
    </span>
    ) : (
        ''
      );

    const colImg = props.data.image ? (
      <div class="col-xl-1 col-lg-2 col-md-4 col-sm-6">
        <a href="props.data.image">
          <img src="'data:image/png;base64, ' + props.data.image" style="maxWidth: 100%" />
        </a>
      </div>
    ) : (
        ''
      );

    return (
      <Card key="props.data.id" class="jh-card">
        <div class="row">
          {colImg}
          <div class="col-xl-1 col-lg-2 col-md-4 col-sm-6">{props.data.model.manufacturer.name}</div>
          <div class="col-xl-4 col-lg-4 col-md-8 col-sm-12" data-toggle="tooltip" title="props.data.options">
            {props.data.variant} ({year}) {optionsIcon}
          </div>
          <div class="col col-xs-12">
            <div style="display: flex">
              {weightSpan}
              {powerSpan}
            </div>
          </div>
        </div>
      </Card>
    );
  }
}

customElements.define('car-component', CarComponent);
