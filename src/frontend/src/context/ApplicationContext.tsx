import { LatLng } from "leaflet";
import { LatLngBoundsExpression } from "leaflet";
import React, { createContext, useEffect, useMemo, useState } from "react";
import { postGetMapBounds, postListAllBounds } from "../services/Microsservice";
import { LatLngBounds } from "leaflet";
import { IRemoteBounds } from "../types";

export interface IChildren {
  children?: React.ReactNode;
}

export interface IProviderValue {
  hasBounds: boolean;
  allBounds: LatLngBoundsExpression[];
  mapBounds: LatLngBoundsExpression;
  bounds: IRemoteBounds;
  updateMapBounds: () => void;
}

export const ApplicationContext = createContext({} as IProviderValue);

export function ApplicationProvider({ children }: IChildren) {
  const [hasBounds, setHasBounds] = useState(false);
  const [mapBounds, setMapBounds] = useState<LatLngBoundsExpression>([]);
  const [bounds, setBounds] = useState<IRemoteBounds>({} as IRemoteBounds);
  const [allBounds, setAllBounds] = useState<LatLngBoundsExpression[]>(
    [] as LatLngBoundsExpression[]
  );

  async function updateMapBounds() {
    const filename = "dted/rio";
    const response = await postGetMapBounds(filename);
    const pointA = new LatLng(response.minLat, response.minLon);
    const pointB = new LatLng(response.maxLat, response.maxLon);
    const latBounds = new LatLngBounds(pointA, pointB);

    setBounds(response);

    const allBounds = await postListAllBounds(filename);

    const allBoundsLatLng = allBounds.bounds.map((bounds) => {
      const pointA = new LatLng(bounds[0][0], bounds[1][0]);
      const pointB = new LatLng(bounds[0][1], bounds[1][1]);
      return new LatLngBounds(pointA, pointB);
    });

    setMapBounds(latBounds);
    setAllBounds(allBoundsLatLng);
    setHasBounds(true);
  }

  function getBounds() {
    if (!hasBounds) {
      setHasBounds(false);
      updateMapBounds();
    }
  }

  useEffect(() => {
    updateMapBounds();
  }, []);

  const memoedValue = useMemo(
    () => ({
      mapBounds,
      updateMapBounds,
      hasBounds,
      getBounds,
      allBounds,
      bounds,
    }),
    [mapBounds, allBounds, bounds]
  );

  return (
    <ApplicationContext.Provider value={memoedValue}>
      {children}
    </ApplicationContext.Provider>
  );
}
