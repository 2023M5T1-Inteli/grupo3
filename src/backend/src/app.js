// Import necessary modules
import express from "express";
import graphRoutes from "./Routes/graphRoutes.js";
import cors from "cors"; 

// Create an instance of the Express application
const app = express();

// Use the CORS middleware to enable cross-origin requests
app.use(cors());

// Use the built-in JSON middleware to parse incoming JSON data
app.use(express.json());

// Map the /graph URL path to the routes defined in the graphRoutes module
app.use("/graph", graphRoutes);

// Start the server and listen for incoming requests on port 4000
app.listen(4000, () => {
    console.log('listening on port 4000');
});