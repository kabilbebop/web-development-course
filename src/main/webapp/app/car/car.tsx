import { IModel } from 'app/app';
import React from 'react';
import { Card } from 'reactstrap';

const NO_YEAR = '0001';
const YEAR_START = 0;
const YEAR_END = 4;
const WEIGHT_PX_RATIO = 3;

export interface ICar {
  id?: number;
  variant?: string;
  power?: number;
  realWeight?: number;
  officialWeight?: number;
  options?: string;
  startDate?: string;
  model?: IModel;
}

export const Car = props => {
  // Size of the power bar
  const powerStyle = {
    width: `${props.data.power / WEIGHT_PX_RATIO}px`
  };
  // Size of the weight bar
  const weightStyle = {
    width: `${props.data.realWeight / 6}px`
  };
  const year = props.data.startDate && !props.data.startDate.startsWith(NO_YEAR) ? props.data.startDate.slice(YEAR_START, YEAR_END) : null;

  const optionsIcon = props.data.options ? <span className="badge badge-pill badge-info hand">i</span> : '';

  const powerSpan = props.data.power ? (
    <span className="badge badge-danger" style={powerStyle}>
      {props.data.power}
      Ch
    </span>
  ) : (
    ''
  );

  const weightSpan = props.data.realWeight ? (
    <span className="badge badge-primary" style={weightStyle}>
      {props.data.realWeight}
      Kg (+
      {props.data.realWeight - props.data.officialWeight})
    </span>
  ) : (
    ''
  );

  return (
    <Card key={props.data.id} className="jh-card">
      <div className="row">
        <div className="col-xl-1 col-lg-2 col-md-4 col-sm-12">{props.data.model.manufacturer.name}</div>
        <div className="col-xl-4 col-lg-4 col-md-8 col-sm-12" data-toggle="tooltip" title={props.data.options}>
          {props.data.variant} ({year}) {optionsIcon}
        </div>
        <div className="col col-xs-12">
          <div style={{ display: 'flex' }}>
            {weightSpan}
            {powerSpan}
          </div>
        </div>
      </div>
    </Card>
  );
};
