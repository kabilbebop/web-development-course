import 'react-toastify/dist/ReactToastify.css';
import './app.css';
import axios from 'axios';
import React from 'react';
import {Card} from 'reactstrap';
import {HashRouter as Router} from 'react-router-dom';
import {toast, ToastContainer, ToastPosition} from 'react-toastify';
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
  searchFilter: string;
  previousFilterValue: string;
  filterInputTimeout: any;
}

export class App extends React.Component<{}, IState> {
  constructor(props: any) {
    super(props);
    this.state = {
      cars: undefined,
      searchFilter: undefined,
      previousFilterValue: undefined,
      filterInputTimeout: undefined
    };
    this.getCars();
  }
  componentDidMount() {}

  getCars() {
    axios.get<ICar>(`api/cars?cacheBuster=${new Date().getTime()}`).then(response => {
      this.setState({ cars: response.data as ICar[] });
    });
  }

  filterCars() {
    const input = document.getElementById('searchFilter') as HTMLInputElement;
    if (input.value !== this.state.previousFilterValue) {
      clearTimeout(this.state.filterInputTimeout);
      const timer = setTimeout(() => {
        if (input.value && input.value.trim() !== '') {
          axios.get('api/cars/search/' + input.value).then(response => {
            this.setState({ cars: response.data as ICar[] });
          });
        } else {
          this.getCars();
        }
      }, 500);
      this.setState({ filterInputTimeout: timer });
      this.setState({ previousFilterValue: input.value });
    }
  }

  render() {
    const paddingTop = '60px';
    let table;
    if (!this.state.cars) {
      table = <div>No cars</div>;
    } else {
      table = this.state.cars.map(car => {
        const powerStyle = {
          width: `${car.power / 3}px`
        };
        const weightStyle = {
          width: `${car.realWeight / 6}px`
        };
        const year = car.startDate && car.startDate.substring(0, 4) !== '0001' ? car.startDate.substring(0, 4) : null;

        const optionsIcon = car.options ? <span className="badge badge-pill badge-info hand">i</span> : '';

        const powerSpan = car.power ? (
          <span className="col-lg-auto badge badge-danger" style={powerStyle}>
            {car.power}
            Ch
          </span>
        ) : (
          ''
        );

        const weightSpan = car.realWeight ? (
          <span className="col-lg-auto badge badge-primary" style={weightStyle}>
            {car.realWeight}
            Kg (+
            {car.realWeight - car.officialWeight})
          </span>
        ) : (
          ''
        );

        return (
          <Card key={car.id} className="jh-card">
            <div className="row">
              <span className="col-1">{car.model.manufacturer.name}</span>
              <span className="col-4" data-toggle="tooltip" title={car.options}>
                {car.variant} ({year}) {optionsIcon}
              </span>
              {weightSpan}
              {powerSpan}
            </div>
          </Card>
        );
      });
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

          <div className="form-group">
            <input
              type="text"
              className="form-control"
              id="searchFilter"
              placeholder="Filter"
              onKeyUp={() => this.filterCars()}
              value={this.state.searchFilter}
            />
          </div>

          <div className="container-fluid view-container" id="app-view-container">
            {table}
          </div>
        </div>
      </Router>
    );
  }
}

export default App;
