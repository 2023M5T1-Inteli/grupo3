import axios from "axios";
import { IGraphLocation } from "../types";

const BASE_URL = "http://localhost:4000";

export const getPath = async (pathID: string) => {
  const response = await axios.get(`${BASE_URL}/graph/${pathID}`);
  const data = response.data;
  const path : IGraphLocation[] = data;
  return path
}