import { Button, Grid, TextField } from "@mui/material";
import { Box } from "@mui/system";
import Grid2 from "@mui/material/Unstable_Grid2/Grid2";

function PathInputBox() {
  return (
    <Grid2
      container
      direction="column"
      alignItems="center"
      justifyContent="center"
    >
      <Grid2 bgcolor={"#F1F1F1"} padding={3} borderRadius={4} xs={10}>
        <Grid2 container spacing={4}>
          <Grid2 xs={5}>
            <Grid2
              container
              bgcolor={"#D9D9D9"}
              spacing={2}
              padding={2}
              borderRadius={4}
            >
              <Grid2 xs={12}textAlign={"start"}>
                Origem
              </Grid2>
              <Grid2 xs={6}>
                <TextField
                  id="standard-basic"
                  label="Latitude"
                  variant="standard"
                  sx={{ width: "100%" }}
                />
              </Grid2>
              <Grid2 xs={6}>
                <TextField
                  id="standard-basic"
                  label="Longitude"
                  variant="standard"
                  sx={{ width: "100%" }}
                />
              </Grid2>
            </Grid2>
          </Grid2>
          <Grid2 xs={5}>
            <Grid2
              container
              bgcolor={"#D9D9D9"}
              spacing={2}
              padding={2}
              borderRadius={4}
            >
               <Grid2 xs={12}textAlign={"start"}>
                Destino
              </Grid2>
              <Grid2 xs={6}>
                <TextField
                  id="standard-basic"
                  label="Latitude"
                  variant="standard"
                  sx={{ width: "100%" }}
                />
              </Grid2>
              <Grid2 xs={6}>
                <TextField
                  id="standard-basic"
                  label="Longitude"
                  variant="standard"
                  sx={{ width: "100%" }}
                />
              </Grid2>
            </Grid2>
          </Grid2>
          <Grid2
            padding={3}
            container
            direction="column"
            alignItems="center"
            justifyContent="center"
            xs={2}
          >
            <Button variant="contained">
              <Box padding={1} width={100}>
                Gerar
              </Box>
            </Button>
          </Grid2>
        </Grid2>
      </Grid2>
    </Grid2>
  );
}

export default PathInputBox;
