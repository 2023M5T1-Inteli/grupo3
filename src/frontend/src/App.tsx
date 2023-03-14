import { useState } from 'react';
import './App.css';
import './components/Filter.css';
import Filter from './components/Filter';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import { Container } from "@mui/system";
import PathInputBox from "./components/PathInputBox";
import Header from "./components/Header";
import { Box } from '@mui/material';
import Map from './components/Map';
import Grid2 from '@mui/material/Unstable_Grid2/Grid2';
import { LatLngExpression } from 'leaflet';

function App() {
  // Define a state variable called `showFilter` and its updater function `setShowFilter` using the `useState` hook, initialized to `false`
  const [showFilter, setShowFilter] = useState(false);

  // Define a function called `toggleFilter` that toggles the value of `showFilter` between `true` and `false`
  const toggleFilter = () => {
    setShowFilter(!showFilter);
  };


    let points: LatLngExpression[] = [
    {lat: -23.871744, lng: -47.075852}, 
    {lat:-23.869291, lng:-47.022447}, 
    {lat: -23.867960, lng:-46.989146}, 
    {lat: -23.870317, lng:-46.944851}, 
    {lat: -23.880317, lng:-46.924851}, 
    // {lat: 49.995, lng:  29.995}, 
  ]
  // Render the main JSX:
  return (
    <div className="App">
      {/* Use a `Container` component from Material-UI to set the maximum width of the app to full screen */}
      <Container maxWidth={false}>
        {/* Render a `Header` component */}
        <Header />
        {/* Render a `PathInputBox` component */}
        <PathInputBox />
      </Container>
      {/* Render a button that toggles the filter when clicked */}
      <button className="App-link" onClick={toggleFilter}>
        <FilterAltIcon />
      </button>
      {/* Render a `Filter` component with props `show` and `toggleFilter` */}
      <Filter show={showFilter} toggleFilter={toggleFilter} />
      {/* Render a `Grid2` component from Material-UI to center the content */}
      <Grid2 container justifyContent={"center"}>
        <Box width={1250} height={500}>
          <Box component="main" sx={{ width: "100%", height: "100%" }}>
            {/* Render a `Map` component */}
            <Map points={points} />
          </Box>
        </Box>
        {/* Render an SVG element with class `graph` */}
        <svg className='graph' width="1250" height="500"></svg>
      </Grid2>
    </div>
  );
}

// Export the `App` component as the default export of this module
export default App;
