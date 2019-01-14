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

const FILTER_INPUT_TIMEOUT = 500;

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

  private getCars() {
    axios.get<ICar>(`api/cars?cacheBuster=${new Date().getTime()}`).then(response => {
      this.setState({ cars: response.data as ICar[] });
    });
  }

  private filterCars() {
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
      }, FILTER_INPUT_TIMEOUT);
      this.setState({ filterInputTimeout: timer });
      this.setState({ previousFilterValue: input.value });
    }
  }

  private top10Click(what: string) {
    axios.get<ICar>(`api/cars/top/${what}/10`).then(response => {
      this.setState({ cars: response.data as ICar[] });
    });
  }

  public render() {
    const paddingTop = '60px';
    const table =
      this.state && this.state.cars && this.state.cars.length > 0 ? (
        this.state.cars.map(car => <Car key={car.id} data={car} />)
      ) : (
        <div>No cars</div>
      );

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
