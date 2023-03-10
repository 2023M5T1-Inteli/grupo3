import { useState } from "react";
import "./App.css";
import "./components/Filter.css";
import Filter from "./components/Filter";
import FilterAltIcon from "@mui/icons-material/FilterAlt";
import "./App.css";
import "./components/Filter.css";
import { Container } from "@mui/system";
import PathInputBox from "./components/PathInputBox";
import Header from "./components/Header";
import { Box, TextField, Typography } from "@mui/material";
import Map from "./components/Map";
import Grid2 from "@mui/material/Unstable_Grid2/Grid2";
import Player from "lottie-react";

import animationData from "./lottieFiles/graph.json";

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
      <button className="App-link" onClick={toogleFilter}>
        <FilterAltIcon />
      </button>
      <Filter show={showFilter} toggleFilter={toogleFilter} />

      <Grid2 container justifyContent={"center"} alignItems={"center"}>
        {/* Loading box - To be implemented at Sprint3 */}
        {/* <Grid2 paddingTop={10}>
        <Box width={1250} height={500}  display={"flex"} flexDirection={"column"} justifyContent={"center"} alignItems={"center"}>
          <Typography variant={"h4"}>Processando rota...</Typography>
          <Box width={500} height={500}>
            <Player
              autoplay
              loop
              animationData={animationData}
            ></Player>
          </Box>
          </Box> 
        </Grid2> */}
        <Box width={1250} height={500}>
          
          <Box component="main" sx={{ width: "100%", height: "100%" }}>
            <Map />
          </Box>
        </Box>
        <svg className="graph" width="1250" height="500"></svg>
      </Grid2>
    </div>
  );
}

export default App;
