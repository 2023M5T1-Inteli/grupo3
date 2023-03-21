import { useEffect, useState } from 'react';
import './App.css';
import './components/Filter.css';
import { Container, Box } from "@mui/system";
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import Grid from '@mui/material/Grid';
import { LatLngExpression } from 'leaflet';
import { IGraphLocation } from './types';
import { getPath } from './services/Graph';
import Header from "./components/Header";
import PathInputBox from "./components/PathInputBox";
import Filter from './components/Filter';
import Map from './components/Map';


function pathToPoints(path: IGraphLocation[]): LatLngExpression[] {
  return path.map(p => ({ lat: p.latitude, lng: p.longitude }));
}

function App() {
  const [showFilter, setShowFilter] = useState(false);
  const [path, setPath] = useState<IGraphLocation[]>([]);
  const [points, setPoints] = useState<LatLngExpression[]>([]);

  useEffect(() => {
    async function getPathAsync() {
      const path = await getPath("123");
      setPath(path);
    }
    getPathAsync();
  }, []);

  useEffect(() => {
    setPoints(pathToPoints(path));
  }, [path]);

  const toggleFilter = () => setShowFilter(prev => !prev);

  return (
    <div className="App">
      <Container maxWidth={false}>
        <Header />
        <PathInputBox />
      </Container>
      <button className="App-link" onClick={toggleFilter}>
        <FilterAltIcon />
      </button>
      <Filter show={showFilter} toggleFilter={toggleFilter} />
      <Grid container justifyContent="center">
        <Box sx={{ width: 1250, height: 500 }}>
          <Box component="main" sx={{ width: "100%", height: "100%" }}>
            {points.length > 0 && <Map points={points} />}
          </Box>
        </Box>
        <svg className="graph" width="1250" height="500"></svg>
      </Grid>
    </div>
  );
}

export default App;
