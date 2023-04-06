// Import React and react-leaflet components, as well as other dependencies
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
  Circle,
} from "react-leaflet";
import { Box } from "@mui/system";
import "leaflet/dist/leaflet.css";
import "./Map.css";
import { LatLngBoundsExpression, LatLngExpression } from "leaflet";
import L from "leaflet";

// Import marker icons and define a default icon for all markers
import icon from "leaflet/dist/images/marker-icon.png";
import iconShadow from "leaflet/dist/images/marker-shadow.png";

let DefaultIcon = L.icon({
  iconUrl: icon,
  shadowUrl: iconShadow,
});

L.Marker.prototype.options.icon = DefaultIcon;

// Define the props interface
interface IMapProps {
  points: LatLngExpression[]; // an array of LatLngExpressions that define the map points
  circleCenter: LatLngExpression[]; // an array of LatLngExpressions that define the center of the circles
  circleRadius: number; // a number that defines the radius of the circles
  bounds: LatLngBoundsExpression; // a LatLngBoundsExpression that defines the bounds of the map
  allBounds: LatLngBoundsExpression[]; // an array of LatLngBoundsExpressions that define the bounds of different areas
}

// Define the props interface for the child map component
interface IChildMapProps {
  points: LatLngExpression[]; // an array of LatLngExpressions that define the map points
  bounds: LatLngBoundsExpression; // a LatLngBoundsExpression that defines the bounds of the map
}

// Define the child map component
function ChildMapPreview(props: IChildMapProps) {
  const map = useMap(); // Get access to the map instance using the useMap hook
  const points = props.points;

  React.useEffect(() => {
    // When the component mounts or the "points" prop changes, fit the map to the bounds defined by the "bounds" prop
    map.fitBounds(props.bounds);
    // Set the minimum zoom level to the one that fits the "bounds" prop
    map.setMinZoom(map.getBoundsZoom(props.bounds));
  }, [points]);

  return null;
}

// Define the main map component
export default function MapPreview(props: IMapProps) {
  console.log(props.bounds);

  let edges = [];
  let rects = [];
  let allBounds = [];

  const points = props.points;
  const circleCenter = props.circleCenter;
  const circleRadius = props.circleRadius;

  // Create Polylines for each set of points
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

  // Create Circles for each set of circle centers
  for (let i = 0; i < circleCenter.length; i++) {
    rects.push(
      <LayerGroup>
        <Circle center={circleCenter[i]} radius={circleRadius} color={"red"} />
      </LayerGroup>
    );
  }

  // Create Rectangles for each set of bounds
  for (let i = 0; i < props.allBounds.length; i++) {
    allBounds.push(
      <LayerGroup>
        <Rectangle bounds={props.allBounds[i]} color={"green"} />
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
        zoomControl={true}
        style={{
          position: "relative",
          width: "100%",
          height: "100%",
        }}
        maxBounds={props.bounds}
      >
        <ChildMapPreview points={points} bounds={props.bounds} />
        {/* Define the TileLayer using the World Imagery Service */}
        <TileLayer url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}" />

        <LayersControl>
          <LayersControl.Overlay checked name={"Rota Principal"}>
            {edges.map((element) => element)}
          </LayersControl.Overlay>
          <LayersControl.Overlay checked name={"Área de Exclusão"}>
            {rects.map((element) => element)}
          </LayersControl.Overlay>
          <LayersControl.Overlay checked name={"Área Permitida"}>
            {allBounds.map((element) => element)}
          </LayersControl.Overlay>
        </LayersControl>
        <Marker position={points[0]} />
        <Marker position={points[points.length - 1]} />
      </MapContainer>
    </Box>
  );
}
