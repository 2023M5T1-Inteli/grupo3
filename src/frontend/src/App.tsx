import { useState } from 'react';
import './App.css';
import './components/Filter.css';
import Filter from './components/Filter';
import FilterAltIcon from '@mui/icons-material/FilterAlt';

function App() {
  const [showFilter, setShowFilter] = useState(false);

  const toogleFilter = () => {
    setShowFilter(!showFilter)
  }

  return (
    <div className="App">
      <header className="App-header">
        <svg width="600" height="500"></svg>
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <button
          className="App-link"
          onClick={toogleFilter}
        >
          
          <FilterAltIcon />
        </button>
      </header>
      <Filter show={showFilter} toggleFilter={toogleFilter}/>
    </div>
  );
}

export default App;
