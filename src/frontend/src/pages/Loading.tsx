import { Typography } from "@mui/material";
import Lottie from "lottie-react";
import loadingAnimation from "../assets/animations/loading.json";

import Grid2 from "@mui/material/Unstable_Grid2/Grid2";

import { useNavigate } from "react-router-dom";

function Loading() {
  const navigate = useNavigate();
  return (
    <Grid2
      container
      justifyContent="start"
      height={"100vh"}
      spacing={0}
      overflow={"hidden"}
    >
      <Grid2
        xs={12}
        bgcolor={"black"}
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
        <Typography color={"white"} variant="h4" fontWeight={"bold"}>
          PROCESSANDO...
        </Typography>
      </Grid2>
    </Grid2>
  );
}

export default Loading;
