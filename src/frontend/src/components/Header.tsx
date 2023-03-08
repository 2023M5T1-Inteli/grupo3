import { AppBar, Button, Grid, TextField } from "@mui/material";
import { Box } from "@mui/system";
import Grid2 from "@mui/material/Unstable_Grid2/Grid2";
import React from "react";

function Header() {
  return (
    <AppBar
      sx={{ bgcolor: "transparent", boxShadow: "none" }}
      position="static"
    >
      <Grid2 container>
        <Grid2 xs={12} padding={2}>
          <Box width={75} height={75} borderRadius={4} bgcolor={"white"}>
            <img
              src="F.png"
              width={70}
              height={70}
              style={{ borderRadius: 16 }}
            ></img>
          </Box>
        </Grid2>
      </Grid2>
    </AppBar>
  );
}

export default Header;
