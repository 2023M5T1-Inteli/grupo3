import { LatLng } from "leaflet";
import { LatLngBoundsExpression } from "leaflet";
import React, { createContext, useEffect, useMemo, useState } from "react";
import { postGetMapBounds, postListAllBounds } from "../services/Microsservice";
import { LatLngBounds } from "leaflet";

export interface IChildren {
  children?: React.ReactNode;
}

export interface IProviderValue {
  hasBounds: boolean;
  allBounds: LatLngBoundsExpression[];
  mapBounds: LatLngBoundsExpression;
  updateMapBounds: () => void;
}

export const ApplicationContext = createContext({} as IProviderValue);

export function ApplicationProvider({ children }: IChildren) {
  const [hasBounds, setHasBounds] = useState(false);
  const [mapBounds, setMapBounds] = useState<LatLngBoundsExpression>([]);
  const [allBounds, setAllBounds] = useState<LatLngBoundsExpression[]>(
    [] as LatLngBoundsExpression[]
  );

  async function updateMapBounds() {
    const filename = "dted/rio";
    const response = await postGetMapBounds(filename);
    const pointA = new LatLng(response.minLat, response.minLon);
    const pointB = new LatLng(response.maxLat, response.maxLon);
    const latBounds = new LatLngBounds(pointA, pointB);

    const allBounds = await postListAllBounds(filename);

    const allBoundsLatLng = allBounds.bounds.map((bounds) => {
      const pointA = new LatLng(bounds[0][0], bounds[1][0]);
      const pointB = new LatLng(bounds[0][1], bounds[1][1]);
      return new LatLngBounds(pointA, pointB);
    });

    setMapBounds(latBounds);
    setAllBounds(allBoundsLatLng);
    setHasBounds(true);
    saveMapBounds(latBounds);
  }

  function saveMapBounds(mapBounds: LatLngBoundsExpression) {
    localStorage.setItem("mapBounds", JSON.stringify(mapBounds));
  }

  function loadMapBounds() {
    const bounds = localStorage.getItem("mapBounds");
    if (bounds) {
      console.log(bounds);
      setMapBounds(JSON.parse(bounds));
      setHasBounds(true);
    }
  }

  function getBounds(): LatLngBoundsExpression {
    if (!hasBounds) {
      loadMapBounds();
    }
    return mapBounds;
  }

  useEffect(() => {
    loadMapBounds();
  }, []);

  const memoedValue = useMemo(
    () => ({
      mapBounds,
      updateMapBounds,
      hasBounds,
      getBounds,
      allBounds,
    }),
    [mapBounds, allBounds]
  );

  return (
    <ApplicationContext.Provider value={memoedValue}>
      {children}
    </ApplicationContext.Provider>
  );
}
