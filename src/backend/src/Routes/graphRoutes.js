// Import necessary modules
import express from "express";
import graphController from "../Controller/graphController.js";

const { createRoute, getFinalPath, checkRouteStatus } = graphController;

// Create a new router object using the express.Router() method
const router = express.Router();

// GET route for "/"
router.get("/:pathID", getFinalPath);
// GET route for "/checkRouteStatus"
router.get("/checkRouteStatus", checkRouteStatus);
// POST route for "/"
router.post("/", createRoute);

export default router;
