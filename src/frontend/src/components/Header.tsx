// Import the necessary components from the Material-UI library
import { AppBar } from "@mui/material";
import { Box } from "@mui/system";
import Grid2 from "@mui/material/Unstable_Grid2/Grid2";


function Header() {
  return (
    <AppBar
      sx={{ bgcolor: "transparent", boxShadow: "none" }} // Customize the style of the AppBar
      position="static" // Set the position of the AppBar
    >
      <Grid2 container>
        <Grid2 xs={12} padding={2}>
          <Box width={75} height={75} borderRadius={4} bgcolor={"white"}>
            <img
              src="F.png"
              width={70}
              height={70}
              style={{ borderRadius: 16 }}
              alt="logo"
            ></img>
          </Box>
        </Grid2>
      </Grid2>
    </AppBar>
  );
}

export default Header;
