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