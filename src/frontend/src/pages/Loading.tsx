import { Typography } from "@mui/material";
import Lottie from "lottie-react";

import Grid2 from "@mui/material/Unstable_Grid2/Grid2";

import { useNavigate, useSearchParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { checkRouteStatus } from "../services/Graph";

// Import the animation
import loadingAnimation from "../assets/animations/loading.json";

function Loading() {
  // Get the routeID from the search params
  const navigate = useNavigate();

  let [searchParams, setSearchParams] = useSearchParams();
  let [routeID, setRouteID] = useState(searchParams.get("routeID"));

  // function that checks the route status every x seconds, and if "Done", redirects to the route page
  useEffect(() => {
    const interval = setInterval(async () => {
      console.log("Checking route status...");
      const status = await checkRouteStatus(routeID || "");
      if (status === "DONE") {
        console.log("Route done!");
        navigate("/Result?pathID=" + routeID);
      }
    }, 6000);
    return () => clearInterval(interval);
  }, []);

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
        <Typography
          color={"white"}
          variant="h4"
          fontWeight={"bold"}
          textAlign="center"
        >
          PROCESSANDO...
        </Typography>
      </Grid2>
    </Grid2>
  );
}

export default Loading;
