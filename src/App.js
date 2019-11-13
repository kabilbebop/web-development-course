import React from 'react';
import './App.css';
import HeaderComponent from './components/header/header';
import ModelComponent from './components/model/model';

function App(props) {

  const modelComponents = props.data.map(brand => <ModelComponent brand={ brand }></ModelComponent>);

  return (
    <div className="App">
      <header-component></header-component>
      <section className="model-content">{ modelComponents }</section>
    </div>
  );
}

export default App;
