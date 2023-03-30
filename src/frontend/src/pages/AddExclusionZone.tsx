import { Box, IconButton, TextField, Typography } from "@mui/material";
import Grid2 from "@mui/material/Unstable_Grid2/Grid2";
// import "../styles/pages/Home.css";

import Map from "../components/Map";
import CustomButton from "../components/CustomButton";
import { useNavigate, useLocation, useSearchParams } from "react-router-dom";
import { LatLngBoundsExpression, LatLngExpression, LatLngTuple } from "leaflet";
import { motion } from "framer-motion";
import { ArrowBack } from "@mui/icons-material";
import { useState, useEffect } from "react";
import MapPreview from "../components/MapPreview";

function pointXtoLatLngTuple(pointX: string): LatLngTuple {
  let pointXArray = pointX.split(",");
  let lat = Number(pointXArray[0]) || 0.0;
  let lon = Number(pointXArray[1]) || 0.0;
  return [lat, lon];
}

function AddExclusionZone() {
  const navigate = useNavigate();
  const clickHandler = () => {
    setSearchParams({
      center: center || "",
      radius: radius || "",
    });
    navigate("/Loading");
  };
  const { state } = useLocation();
  let [searchParams, setSearchParams] = useSearchParams();
  let [center, setCenter] = useState(searchParams.get("center") || "");
  let [radius, setRadius] = useState(searchParams.get("radius") || "");
  let [circumference, setCircumference] = useState<LatLngExpression>([0,0]);

  const { originLat, originLon, destLat, destLon } = state;
  useEffect(() => {
    if (center && radius) {
      const centerCoords: LatLngTuple = pointXtoLatLngTuple(center);
      const radiusMeters = Number(radius) * 1000;
      const circumferencePoints = calculateCircumferencePoints(centerCoords, radiusMeters);
      setCircumference(pointXtoLatLngTuple(center));
      
    }
  }, [center, radius]);

  const calculateCircumferencePoints = (center: LatLngTuple, radius: number): LatLngExpression[] => {
    const circumferencePoints: LatLngExpression[] = [];
    const numPoints = 32;
    for (let i = 0; i < numPoints; i++) {
      const angle = (i / numPoints) * Math.PI * 2;
      const x = center[0] + radius * Math.cos(angle);
      const y = center[1] + radius * Math.sin(angle);
      circumferencePoints.push([x, y]);
    }
    return circumferencePoints;
  };

  const pointXtoLatLngTuple = (pointX: string): LatLngTuple => {
    let pointXArray = pointX.split(",");
    let lat = Number(pointXArray[0]) || 0.0;
    let lon = Number(pointXArray[1]) || 0.0;
    return [lat, lon];
  };

  let points: LatLngExpression[] = [];
  points = [
    { lat: originLat, lng: originLon },
    { lat: destLat, lng: destLon },
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
              Área de exclusão
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
                Pontos (Latitude, Longitude)
              </Typography>
            </Grid2>
            <Grid2>
              <TextField
                value={center}
                label="Ponto Central"
                variant="outlined"
                fullWidth={true}
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                  setCenter(e.target.value);
                }}
              />
            </Grid2>
            <Grid2>
              <TextField
                value={radius}
                label="Raio (em Km)"
                variant="outlined"
                fullWidth={true}
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                  setRadius(e.target.value);
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
        <Grid2 xs={12} lg={9} bgcolor={"red"}>
          {/* <Box sx={{ width: 1250, height: 500 }}> */}
          <Box component="main" sx={{ width: "100%", height: "100%" }}>
            <MapPreview points={points} circleCenter={[circumference]} circleRadius={Number(radius)||0}/>
          </Box>
          {/* </Box> */}
        </Grid2>
      </Grid2>
    </motion.div>
  );
}

export default AddExclusionZone;
