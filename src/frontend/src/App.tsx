import { useState } from 'react';
import './App.css';
import './components/Filter.css';
import Filter from './components/Filter';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import "./App.css";
import "./components/Filter.css";
import { Container } from "@mui/system";
import PathInputBox from "./components/PathInputBox";
import Header from "./components/Header";

function App() {
  const [showFilter, setShowFilter] = useState(false);

  const toogleFilter = () => {
    setShowFilter(!showFilter);
  };

  return (
    <div className="App">
        <Container maxWidth={false}>
          <Header />
          <PathInputBox />
        </Container>
        <button
          className="App-link"
          onClick={toogleFilter}
        >
          
          <FilterAltIcon />
        </button>
      <Filter show={showFilter} toggleFilter={toogleFilter} />
      <svg className='graph' width="600" height="500"></svg>
    </div>
  );
}

export default App;
