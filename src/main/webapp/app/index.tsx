import React from 'react';
import ReactDOM from 'react-dom';
import { bindActionCreators } from 'redux';
import { AppContainer } from 'react-hot-loader';

import ErrorBoundary from './error/error-boundary';
import AppComponent from './app';
import { loadIcons } from './config/icon-loader';

loadIcons();

const rootEl = document.getElementById('root');

const render = Component =>
  ReactDOM.render(
    <ErrorBoundary>
      <AppContainer>
        <div>
          <Component />
        </div>
      </AppContainer>
    </ErrorBoundary>,
    rootEl
  );

render(AppComponent);
