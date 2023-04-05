export interface IGraphLocation {
  averageHeight: number;
  latitude: number;
  lastNode: {
    low: number;
    high: number;
  };
  index: {
    low: number;
    high: number;
  };
  longitude: number;
}

export interface IRemoteBounds {
  minLon: number;
  maxLat: number;
  minLat: number;
  maxLon: number;
}

export interface IAllRemoteBouds {
  bounds: Array<Array<Array<number>>>;
}
