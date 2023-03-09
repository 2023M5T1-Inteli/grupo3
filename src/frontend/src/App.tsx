import { useState } from 'react';
import logo from './logo.svg';
import './App.css';
import './components/Filter.css';
import Filter from './components/Filter';
import { Box, TextField } from '@mui/material';
import Map from './components/Map';
import Container from '@mui/material/Container';
import Grid2 from '@mui/material/Unstable_Grid2/Grid2';

function App() {
  const [showFilter, setShowFilter] = useState(false);

  const toogleFilter = () => {
    setShowFilter(!showFilter)
  }

  return (
    <Container>
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
        <TextField id="outlisned-basic" label="Outlined" variant="outlined" />
          <TextField id="filled-basic" label="Filled" variant="filled" />
          <TextField id="standard-basic" label="Standard" variant="standard" />
      </header>
      <Grid2 container justifyContent={"center"}>
        <Grid2 xs={10} padding={2} >
        <Box component="main" sx={{ width:"100%", height:300 }}>
        <Map />
      </Box>
      </Grid2>
      
      </Grid2>
      <Filter show={showFilter} toggleFilter={toogleFilter}/>
    </Container>
  );
}

export default App;
