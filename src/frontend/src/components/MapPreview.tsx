import * as React from "react";
import {
  MapContainer,
  TileLayer,
  LayerGroup,
  LayersControl,
  Polyline,
  Marker,
  useMap,
  Rectangle,
} from "react-leaflet"; // Remove unused imports
import { Box } from "@mui/system";
import "leaflet/dist/leaflet.css";
import "./Map.css";
import { LatLngBoundsExpression, LatLngExpression } from "leaflet";
import L from "leaflet";

import icon from "leaflet/dist/images/marker-icon.png";
import iconShadow from "leaflet/dist/images/marker-shadow.png";

let DefaultIcon = L.icon({
  iconUrl: icon,
  shadowUrl: iconShadow,
});

L.Marker.prototype.options.icon = DefaultIcon;

interface IMapProps {
  points: LatLngExpression[];
  bounds: LatLngBoundsExpression[];
}

function ChildMapPreview(props: IMapProps) {
  const map = useMap();
  const points = props.points;

  React.useEffect(() => {
    map.setView(points[0], 13);
  }, [points]);

  return null;
}

export default function MapPreview(props: IMapProps) {
  let edges = [];
  let rects = [];

  const points = props.points;
  const bounds = props.bounds;
  console.log(bounds);

  for (let i = 0; i < points.length; i++) {
    if (i + 1 < points.length) {
      edges.push(
        <LayerGroup>
          <Polyline
            positions={[points[i], points[i + 1]]}
            pathOptions={{ color: "red" }}
          />
        </LayerGroup>
      );
    }
  }

  for (let i = 0; i < bounds.length; i++) {
    rects.push(
      <LayerGroup>
        <Rectangle bounds={bounds[i]} color={"red"} />
      </LayerGroup>
    );
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
        <ChildMapPreview points={points} bounds={props.bounds} />
        {/* Define the TileLayer using the World Imagery Service */}
        <TileLayer url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}" />

        <LayersControl>
          <LayersControl.Overlay checked name={"test"}>
            {edges.map((element) => element)}
          </LayersControl.Overlay>
          <LayersControl.Overlay checked name={"test2"}>
            {rects.map((element) => element)}
          </LayersControl.Overlay>
        </LayersControl>
        <Marker position={points[0]} />
        <Marker position={points[points.length - 1]} />
      </MapContainer>
    </Box>
  );
}
