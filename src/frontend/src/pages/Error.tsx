import { Typography, Container } from "@mui/material";

import CustomButton from "../components/CustomButton";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";

function Error() {
    // Navigation
    const navigate = useNavigate();

    // Go back to home page
    const clickHandler = () => {
        navigate("/", {
        });
    };

    return (
        <motion.div>
            <Container
                maxWidth={false}
                sx={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    height: "100vh",
                    justifyContent: "center",
                    backgroundColor: "#000",
                    width: "100vw",
                    marginRight: 0,
                    marginLeft: 0
                }}
            > 
                <Container>
                    <Typography
                        variant="h1"
                        sx={{
                            color: "#FFF",
                            fontSize: "4em",
                            fontWeight: "bold",
                            textAlign: "center",
                        }}  
                    >
                        OOPS!
                    </Typography>
                    <Typography
                        variant="h6"
                        sx={{
                            color: "#FFF",
                            fontSize: "2em",
                            fontWeight: "bold",
                            textAlign: "center",
                            marginBottom: "1em",
                        }}  
                    >
                        :( Falha ao gerar a rota
                    </Typography>
                </Container>
                <Container
                    maxWidth="xs"
                    sx={{
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                    }}
                >
                    <CustomButton
                        height="3.5em"
                        backgroundColor="#E17F49"
                        text="VOLTAR AO INÍCIO"
                        clickHandler={clickHandler}
                    />
                </Container>
            </Container>
        </motion.div>
    )
}

export default Error;