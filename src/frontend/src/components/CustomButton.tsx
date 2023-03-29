import { Button, Typography } from "@mui/material";

// Define interface for CustomButton props
interface ICustomButtonProps {
  height: string;
  backgroundColor: string;
  text: string;
  clickHandler: () => void;
}

// Define CustomButton functional component
function CustomButton(props: ICustomButtonProps) {
  // Render a button with custom styles and text
  return (
    <Button
      fullWidth={true}
      variant={"contained"}
      sx={{
        height: props.height,
        borderRadius: "1.5rem",
        color:"white",
        backgroundColor: props.backgroundColor,
      }}
      onClick={props.clickHandler}
    >
      <Typography fontWeight={"bold"}>{props.text}</Typography>
    </Button>
  );
}

// Export CustomButton as default
export default CustomButton;
