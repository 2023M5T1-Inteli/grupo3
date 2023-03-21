import { Button, Typography } from "@mui/material";

interface ICustomButtonProps {
  height: string;
  backgroundColor: string;
  text: string;
  clickHandler: () => void;
}

function CustomButton(props: ICustomButtonProps) {
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

export default CustomButton;
