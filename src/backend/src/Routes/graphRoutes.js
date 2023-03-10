import express from "express";
import graphController from "../Controller/graphController.js";

const { getGraph, createRoute, getFinalPath, checkRouteStatus } = graphController;

const router = express.Router();

// GET /api/graph
router.get("/", getFinalPath);
router.get("/checkRouteStatus", checkRouteStatus);
router.post("/", createRoute);

export default router;