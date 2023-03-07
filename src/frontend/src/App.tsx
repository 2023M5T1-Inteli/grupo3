import { useState } from "react";
import logo from "./logo.svg";
import "./App.css";
import "./components/Filter.css";
import Filter from "./components/Filter";
import { AppBar, Button, TextField } from "@mui/material";
import { Box, Container, height } from "@mui/system";
import Grid2 from "@mui/material/Unstable_Grid2/Grid2";
import AdbIcon from "@mui/icons-material/Adb";
import Typography from "@mui/material/Typography";
import AccessAlarmIcon from "@mui/icons-material/AccessAlarm";
import GenerateBar from "./components/GenerateBar";
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
        <GenerateBar />
      </Container>

      <button className="App-link" onClick={toogleFilter}>
        Settings
      </button>
      <Filter show={showFilter} toggleFilter={toogleFilter} />
    </div>
  );
}

export default App;
