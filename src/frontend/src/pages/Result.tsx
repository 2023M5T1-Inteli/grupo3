import { Box, IconButton, TextField, Typography} from "@mui/material";
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

function Result() {
  const navigate = useNavigate();

  let [searchParams, setSearchParams] = useSearchParams();
  let [originLat, setOriginLat] = useState(searchParams.get("originLat"));
  let [originLon, setOriginLon] = useState(searchParams.get("originLon"));
  let [destLat, setdestLat] = useState(searchParams.get("destLat"));
  let [destLon, setdestLon] = useState(searchParams.get("destLon"));

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

  let points: LatLngExpression[] = [
    { lat: Number(originLat) || 0.0, lng: Number(originLon) || 0.0 },
    { lat: Number(destLat) || 0.0, lng: Number(destLon) || 0.0 },
    // { lat: -23.871744, lng: -47.075852 },
    // { lat: -23.869291, lng: -47.022447 },
    // { lat: -23.86796, lng: -46.989146 },
    // { lat: -23.870317, lng: -46.944851 },
    // { lat: -23.880317, lng: -46.924851 },
    // {lat: 49.995, lng:  29.995},
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
          lg={2}
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
        </Grid2>
        <Grid2 xs={12} lg={10} bgcolor={"red"}>
          {/* <Box sx={{ width: 1250, height: 500 }}> */}
          <Box component="main" sx={{ width: "100%", height: "100%" }}>
            <MapPreview
              points={points}
              circleCenter = {[[-23.871744, -47.075852]]}
              circleRadius = {1000}
            />
          </Box>
          {/* </Box> */}
        </Grid2>
      </Grid2>
    </motion.div>
  );
}

export default Result;
