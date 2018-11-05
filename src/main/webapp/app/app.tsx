import 'react-toastify/dist/ReactToastify.css';
import './app.css';
import axios from 'axios';
import { Moment } from 'moment';
import React from 'react';
import { Card } from 'reactstrap';
import { HashRouter as Router } from 'react-router-dom';
import { ToastContainer, ToastPosition, toast } from 'react-toastify';
import Header from 'app/header/header';
import ErrorBoundary from 'app/error/error-boundary';

export interface IManufacturer {
  id?: number;
  name?: string;
}

export interface IModel {
  id?: number;
  name?: string;
  manufacturer?: IManufacturer;
}

export interface ICar {
  id?: number;
  variant?: string;
  power?: number;
  realWeight?: number;
  officialWeight?: number;
  options?: string;
  startDate?: Moment;
  endDate?: Moment;
  model?: IModel;
}

export interface IState {
  cars: ICar[];
}

export class App extends React.Component<{}, IState> {
  constructor(props: any) {
    super(props);
    this.state = {
      cars: undefined
    };
    this.getCars();
  }
  componentDidMount() {}

  getCars() {
    axios.get<ICar>(`api/cars?cacheBuster=${new Date().getTime()}`).then(response => this.setState({ cars: response.data as ICar[] }));
  }

  render() {
    const paddingTop = '60px';
    let table;
    if (this.state.cars) {
      table = this.state.cars.map(car => (
        <Card className="jh-card">
          <div className="row justify-content-around">
            <span className="col">
              {car.model.manufacturer.name} {car.model.name} {car.variant}
            </span>
            <span className="col badge badge-secondary">
              {car.power}
              Ch
            </span>
            <span className="col badge badge-primary">
              {car.realWeight}
              Kg (+
              {car.realWeight - car.officialWeight})
            </span>
            <span className="col">{car.options}</span>
            <span className="col">
              {car.startDate}-{car.endDate}
            </span>
          </div>
        </Card>
      ));
    } else {
      table = <div>No cars</div>;
    }
    return (
      <Router>
        <div className="app-container" style={{ paddingTop }}>
          <ToastContainer
            position={toast.POSITION.TOP_LEFT as ToastPosition}
            className="toastify-container"
            toastClassName="toastify-toast"
          />
          <ErrorBoundary>
            <Header menuOpen={false} />
          </ErrorBoundary>
          <div className="container-fluid view-container" id="app-view-container">
            {table}
          </div>
        </div>
      </Router>
    );
  }
}

export default App;
