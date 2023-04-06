import * as React from "react";
import {
  MapContainer,
  TileLayer,
  Circle,
  LayerGroup,
  LayersControl,
  Polyline,
} from "react-leaflet"; // Remove unused imports
import { Box } from "@mui/system";
import "leaflet/dist/leaflet.css";
import "./Map.css";
import { LatLngExpression } from "leaflet";

interface IMapProps {
  points: LatLngExpression[];
}

export default function Map(props: IMapProps) {
  const center: [number, number] = [49.99, 29.99]; // Define the center of the map using an array

  let elements = [];
  let edges = [];
  const points = props.points;

  // Create the circles and liness
  for (let i = 0; i < points.length; i++) {
    elements.push(
      <Circle
        center={points[i]}
        radius={50}
        pathOptions={{ fillColor: "blue" }}
      />
    );
    if (i + 1 < points.length) {
      edges.push(
        <Polyline
          positions={[points[i], points[i + 1]]}
          pathOptions={{ color: "red", }}
        />
      );
    }
  }

  return (
    <Box
      sx={{
        width: "100%",
        height: "100%",
        position: "relative",
        overflow: "hidden",
      }}
    >
      {/* Define the MapContainer with the center and zoom level */}
      <MapContainer
        center={points[0]}
        zoom={13}
        zoomControl={true}
        style={{
          position: "relative",
          width: "100%",
          height: "100%",
        }}
      >
        {/* Define the TileLayer using the World Imagery Service */}
        <TileLayer url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}" />
        <LayersControl>
          <LayersControl.Overlay checked name={"Visualizar"}>
            <LayerGroup>{elements.map((element) => element)}</LayerGroup>
            <LayerGroup>{edges.map((element) => element)}</LayerGroup>
          </LayersControl.Overlay>
        </LayersControl>
      </MapContainer>
    </Box>
  );
}
