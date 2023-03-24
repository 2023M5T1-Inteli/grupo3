import { Box, IconButton, TextField, Typography } from "@mui/material";
import Grid2 from "@mui/material/Unstable_Grid2/Grid2";
// import "../styles/pages/Home.css";

import Map from "../components/Map";
import CustomButton from "../components/CustomButton";
import { useNavigate, useSearchParams } from "react-router-dom";
import { LatLngExpression } from "leaflet";
import { motion } from "framer-motion";
import { PhotoCamera, ArrowBack } from "@mui/icons-material";
import React, { useEffect, useState } from "react";
import MapPreview from "../components/MapPreview";
import { getPath } from "../services/Graph";
import { IGraphLocation } from "../types";
import Lottie from "lottie-react";

import loadingAnimation from "../assets/animations/loadingMap.json";

function pathToPoints(path: IGraphLocation[]): LatLngExpression[] {
  return path.map((p) => ({ lat: p.latitude, lng: p.longitude }));
}

function Result() {
  const navigate = useNavigate();

  let [searchParams, setSearchParams] = useSearchParams();
  let [pathID, setPathID] = useState(searchParams.get("pathID"));

  const [path, setPath] = useState<IGraphLocation[]>([]);
  const [points, setPoints] = useState<LatLngExpression[]>([]);

  useEffect(() => {
    async function getPathAsync() {
      const path = await getPath(pathID || "");
      setPath(path);
    }
    getPathAsync();
  }, []);

  useEffect(() => {
    setPoints(pathToPoints(path));
  }, [path]);

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
                navigate("/");
              }}
            >
              <ArrowBack />
              <Typography color={"white"}>Voltar</Typography>
            </IconButton>
          </Grid2>
        </Grid2>
        <Grid2 xs={12} lg={10}>
          {/* <Box sx={{ width: 1250, height: 500 }}> */}
          <Box component="main" sx={{ width: "100%", height: "100%" }}>
            {points.length > 0 ? (
              <Map points={points} />
            ) : (
              <Grid2
                xs={12}
                sx={{ height: "100%" }}
                bgcolor={"white"}
                display={"flex"}
                justifyContent={"center"}
                padding={16}
                direction={"column"}
                container
              >
                <Lottie
                  animationData={loadingAnimation}
                  width={200}
                  style={{ height: 400 }}
                />
              </Grid2>
            )}
          </Box>
          {/* </Box> */}
        </Grid2>
      </Grid2>
    </motion.div>
  );
}

export default Result;
