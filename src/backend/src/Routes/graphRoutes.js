// Import necessary modules
const express = require("express");
const graphController = require("../Controller/graphController.js");

const { createRoute, getFinalPath, checkRouteStatus } = graphController;

// Create a new router object using the express.Router() method
const router = express.Router();

// GET route for "/"
router.get("/", getFinalPath);
// GET route for "/checkRouteStatus"
router.get("/checkRouteStatus", checkRouteStatus);
// POST route for "/"
router.post("/", createRoute);

exports.router = router;
