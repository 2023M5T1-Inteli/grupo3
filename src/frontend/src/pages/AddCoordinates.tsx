import { Box, IconButton, TextField, Typography } from "@mui/material";
import Grid2 from "@mui/material/Unstable_Grid2/Grid2";
// import "../styles/pages/Home.css";

import Map from "../components/Map";
import CustomButton from "../components/CustomButton";
import { useNavigate, useSearchParams } from "react-router-dom";
import { LatLngExpression } from "leaflet";
import { motion } from "framer-motion";
import { PhotoCamera, ArrowBack } from "@mui/icons-material";
import React, { useState } from "react";
import MapPreview from "../components/MapPreview";

function AddCoordinates() {
  // Navigation
  const navigate = useNavigate();

  // Search params
  let [searchParams, setSearchParams] = useSearchParams();
  let [originLat, setOriginLat] = useState(searchParams.get("originLat"));
  let [originLon, setOriginLon] = useState(searchParams.get("originLon"));
  let [destLat, setdestLat] = useState(searchParams.get("destLat"));
  let [destLon, setdestLon] = useState(searchParams.get("destLon"));

  // Load local variables according to search params
  const clickHandler = () => {
    setSearchParams({
      originLat: originLat || "0.0",
      originLon: originLon || "0.0",
      destLat: destLat || "0.0",
      destLon: destLon || "0.0",
    });
    navigate("/AddExclusionZone", {
      state: { originLat, originLon, destLat, destLon },
    });
  };

  // Sets the entry and exti points
  let points: LatLngExpression[] = [
    { lat: Number(originLat) || 0.0, lng: Number(originLon) || 0.0 },
    { lat: Number(destLat) || 0.0, lng: Number(destLon) || 0.0 },
  ];
  return (
    <motion.div>
      <Grid2
        container
        justifyContent="start"
        height={"100vh"}
        spacing={0}
        overflow={"hidden"}
      >
        <Grid2
          xs={12}
          lg={3}
          bgcolor={"black"}
          display={"flex"}
          justifyContent={"start"}
          sx={{
            padding: {
              xs: 0,
              sm: 2,
              lg: 1,
              xl: 6,
            },
          }}
          container
          direction={"column"}
          spacing={8}
        >
          <Grid2
            xs={12}
            container
            direction={"row"}
            justifyContent={"flex-start"}
            alignItems={"center"}
            padding={6}
          >
            <IconButton
              onClick={() => {
                navigate(-1);
              }}
            >
              <ArrowBack />
              <Typography color={"white"}>Voltar</Typography>
            </IconButton>
          </Grid2>

          <Grid2 xs={12}>
            <Typography
              textAlign={"left"}
              variant="h3"
              fontWeight={"bold"}
              color={"white"}
            >
              Adicione as coordenadas
            </Typography>
          </Grid2>
          <Grid2 container xs={12} spacing={2} direction={"column"}>
            <Grid2>
              <Typography
                textAlign={"left"}
                variant="h5"
                fontWeight={"bold"}
                color={"white"}
              >
                Origem
              </Typography>
            </Grid2>
            <Grid2>
              <TextField
                label="Latitude"
                value={originLat}
                variant="outlined"
                fullWidth={true}
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                  setOriginLat(e.target.value);
                }}
              />
            </Grid2>
            <Grid2>
              <TextField
                label="Longitude"
                variant="outlined"
                fullWidth={true}
                value={originLon}
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                  setOriginLon(e.target.value);
                }}
              />
            </Grid2>
          </Grid2>
          <Grid2 container xs={12} spacing={2} direction={"column"}>
            <Grid2>
              <Typography
                textAlign={"left"}
                variant="h5"
                fontWeight={"bold"}
                color={"white"}
              >
                Destino
              </Typography>
            </Grid2>
            <Grid2>
              <TextField
                label="Latitude"
                variant="outlined"
                fullWidth={true}
                value={destLat}
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                  setdestLat(e.target.value);
                }}
              />
            </Grid2>
            <Grid2>
              <TextField
                label="Longitude"
                variant="outlined"
                fullWidth={true}
                value={destLon}
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                  setdestLon(e.target.value);
                }}
              />
            </Grid2>
          </Grid2>
          <Grid2 xs={12}>
            <CustomButton
              height="3.5em"
              backgroundColor="#E17F49"
              text="PRÓXIMO"
              clickHandler={clickHandler}
            />
          </Grid2>
        </Grid2>
        <Grid2 xs={12} lg={9}>
          <Box component="main" sx={{ width: "100%", height: "100%" }}>
            <MapPreview
              points={points}
              bounds={[
                [
                  [0.0, 0.0],
                  [0.0, 0.0],
                ],
              ]}
            />
          </Box>
        </Grid2>
      </Grid2>
    </motion.div>
  );
}

export default AddCoordinates;
