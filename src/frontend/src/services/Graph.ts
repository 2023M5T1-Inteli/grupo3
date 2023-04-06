import axios from "axios";
import { BASE_URL } from "../Constants";
import { IGraphLocation } from "../types";

// function that returns a path from the backend
export const getPath = async (pathID: string) => {
  const response = await axios.get(`${BASE_URL}/graph/${pathID}`);
  const data = response.data;
  const path: IGraphLocation[] = data;
  return path;
};

// function that creates a path from the backend
export const createPath = async (
  originPoints: [number, number],
  destinationPoints: [number, number],
  exclusionPoints: Array<Array<number>>
) => {
  const response = await axios.post(`${BASE_URL}/graph`, {
    entryPoints: originPoints,
    exitPoints: destinationPoints,
    exclusionPoints: exclusionPoints,
    intermediatePoints: [],
  });
  const data = response.data;
  return data.routeID;
};

// function that checks the status of a path from the backend
export const checkRouteStatus = async (routeID: string) => {
  const response = await axios.get(
    `${BASE_URL}/graph/checkRouteStatus/${routeID}`
  );
  const data = response.data;
  return data.status;
};
