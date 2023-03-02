import React, { useState } from 'react';
import logo from './logo.svg';
import './App.css';
import './components/Filter.css';
import Filter from './components/Filter';

function App() {
  const [showFilter, setShowFilter] = useState(false);

  const toogleFilter = () => {
    setShowFilter(!showFilter)
  }

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <button
          className="App-link"
          onClick={toogleFilter}
        >
          Learn React
        </button>
      </header>
      <Filter show={showFilter} />
    </div>
  );
}

export default App;
