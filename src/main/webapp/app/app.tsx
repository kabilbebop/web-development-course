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
  startDate?: string;
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
    axios.get<ICar>(`api/cars?cacheBuster=${new Date().getTime()}`).then(response => {
      this.setState({ cars: response.data as ICar[] });
    });
  }

  render() {
    const paddingTop = '60px';
    let table;
    if (this.state.cars) {
      table = this.state.cars.map(car => {
        const powerStyle = {
          width: `${car.power / 3}px`
        };
        const weightStyle = {
          width: `${car.realWeight / 6}px`
        };
        const rowStyle = { display: 'inline' };
        const year = car.startDate && car.startDate.substring(0, 4) !== '0001' ? car.startDate.substring(0, 4) : null;
        return (
          <Card key={car.id} className="jh-card">
            <div className="row justify-content-around" style={rowStyle}>
              <span className="col">{car.model.manufacturer.name}</span>
              <span className="col" data-toggle="tooltip" title={car.options}>
                {car.variant} ({year})
              </span>
              <span className="col badge badge-primary" style={weightStyle}>
                {car.realWeight}
                Kg (+
                {car.realWeight - car.officialWeight})
              </span>
              <span className="col badge badge-danger" style={powerStyle}>
                {car.power}
                Ch
              </span>
            </div>
          </Card>
        );
      });
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
