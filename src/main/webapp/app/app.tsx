import 'app/app.css';
import ErrorBoundary from 'app/error/error-boundary';
import Header from 'app/header/header';
import axios from 'axios';
import React from 'react';
import { HashRouter as Router } from 'react-router-dom';
import { toast, ToastContainer, ToastPosition } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Card } from 'reactstrap';
import { Car, ICar } from 'app/car/car';

export interface IManufacturer {
  id?: number;
  name?: string;
}

export interface IModel {
  id?: number;
  name?: string;
  manufacturer?: IManufacturer;
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
      filterInputTimeout: undefined,
      previousFilterValue: undefined,
      searchFilter: undefined
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

  top10Click(what: string) {
    console.error('à implémenter!', what);
  }

  render() {
    const paddingTop = '60px';
    let table;
    if (this.state.cars) {
      if (this.state.cars.length === 0) table = <div>No cars</div>;
      else {
        table = this.state.cars.map(car => <Car key={car.id} data={car} />);
      }
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
            <Header menuOpen={false} top10Click={what => this.top10Click(what)} />
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
