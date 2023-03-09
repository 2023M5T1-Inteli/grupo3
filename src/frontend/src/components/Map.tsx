import * as React from 'react';
import { LayersControl, MapContainer, TileLayer, WMSTileLayer, GeoJSON } from 'react-leaflet';
import { Box } from '@mui/system';
import "leaflet/dist/leaflet.css";
import './Map.css';

export default function Map() {
  const center: [number, number] = [-22.1780, -43.4082];

  return (
    <Box sx={ {
        width: "100%",
        height: "100%",
        position: "relative",
        overflow: "hidden"
      }}>
        <MapContainer center={center} zoom={10} style={{
          position: "relative",
          width: "100%",
          height: "100%"
        }}>
        <TileLayer url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}" />
        </MapContainer>
    </Box>
  );
}
