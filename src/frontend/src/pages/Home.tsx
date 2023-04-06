import { Container, Typography } from "@mui/material";
import Grid2 from "@mui/material/Unstable_Grid2/Grid2";

import CustomButton from "../components/CustomButton";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import { useContext, useEffect, useRef } from "react";
import { ApplicationContext } from "../context/ApplicationContext";

function Home() {
  // Navigate to the AddCoordinates page
  const navigate = useNavigate();
  const clickHandler = () => navigate("/AddCoordinates");
  
  // Update the map bounds
  const context = useContext(ApplicationContext);
  const hasUpdated = useRef(false);
  useEffect(() => {
    if (hasUpdated.current === false) {
      context.updateMapBounds();
    }

    return () => {
      hasUpdated.current = true;
    };
  }, []);
  
  return (
    <motion.div>
      <Container
        maxWidth={false}
        disableGutters={true}
        style={{
          backgroundImage: `url("./images/landing.jpg")`,
          backgroundSize: "cover",
          backgroundRepeat: "no-repeat",
          backgroundPosition: "center center",
        }}
      >
        <Grid2
          container
          justifyContent="start"
          height={"100vh"}
          padding={2}
          sx={{ opacity: 0.8 }}
        >
          <Grid2
            xs={12}
            lg={4}
            bgcolor={"black"}
            display={"flex"}
            justifyContent={"center"}
            sx={{
              padding: {
                xs: 0,
                sm: 2,
                md: 4,
                lg: 6,
                xl: 12,
              },
            }}
            container
            direction={"column"}
            spacing={4}
          >
            <Grid2 xs={12}>
              <Typography
                textAlign={"left"}
                variant="h3"
                fontWeight={"bold"}
                color={"white"}
              >
                BEM-VINDO AO FLIGHTWISE!
              </Typography>
            </Grid2>
            <Grid2 xs={12}>
              <Typography
                textAlign={"left"}
                variant="body1"
                fontWeight={"bold"}
                color={"white"}
              >
                O software que oferece soluções eficientes e seguras para o
                planejamento de voos da AEL. Nossos algoritmos avançados
                determinam a rota ideal para voar em baixa altitude, levando em
                conta fatores como distância e variações de altitudes.
              </Typography>
            </Grid2>
            <Grid2 xs={12}>
              <CustomButton
                height="3.5em"
                backgroundColor="#E17F49"
                text="COMEÇAR"
                clickHandler={clickHandler}
              />
            </Grid2>
          </Grid2>
        </Grid2>
      </Container>
    </motion.div>
  );
}

export default Home;
