import axios from "axios";
import { BASE_URL } from "../Constants";
import { IGraphLocation } from "../types";


export const getPath = async (pathID: string) => {
  const response = await axios.get(`${BASE_URL}/graph/${pathID}`);
  const data = response.data;
  const path : IGraphLocation[] = data;
  return path
}