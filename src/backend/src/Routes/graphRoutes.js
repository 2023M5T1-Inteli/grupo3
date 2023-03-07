import express from "express";
import graphController from "../Controller/graphController.js";

const { getGraph, createNode, getNode } = graphController;

const router = express.Router();

// GET /api/graph
router.get("/", getNode);
router.post("/", createNode);

export default router;