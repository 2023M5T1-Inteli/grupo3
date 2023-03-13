import * as React from 'react';
import { MapContainer, TileLayer } from 'react-leaflet'; // Remove unused imports
import { Box } from '@mui/system';
import "leaflet/dist/leaflet.css";
import './Map.css';

export default function Map() {
  const center: [number, number] = [-22.1780, -43.4082]; // Define the center of the map using an array

  return (
    <Box sx={ {
        width: "100%",
        height: "100%",
        position: "relative",
        overflow: "hidden"
      }}>
        {/* Define the MapContainer with the center and zoom level */}
        <MapContainer center={center} zoom={15} zoomControl={false} style={{
          position: "relative",
          width: "100%",
          height: "100%"
        }}>
        {/* Define the TileLayer using the World Imagery Service */}
        <TileLayer url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}" />
        </MapContainer>
    </Box>
  );
}
