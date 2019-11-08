import React from 'react';
import './App.css';
import HeaderComponent from './components/header/header';
import ModelComponent from './components/model/model';

function App(props) {

  return (
    <div className="App">
      <header-component></header-component>
      <ModelComponent brand={ props.data[0] }></ModelComponent>
    </div>
  );
}

export default App;
