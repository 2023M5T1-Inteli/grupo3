import axios from "axios";
import { IAllRemoteBouds, IRemoteBounds } from "../types";

const BASE_URL = "http://localhost:8080";

export const postGetMapBounds = async (filename: string) => {
  const response = await axios.post(`${BASE_URL}/getMapBounds`, {
    filePath: filename,
  });
  const data: IRemoteBounds = response.data;
  return data;
};

export const postListAllBounds = async (filename: string) => {
  const response = await axios.post(`${BASE_URL}/listAllBounds`, {
    filePath: filename,
  });
  const data: IAllRemoteBouds = response.data;
  return data;
};
