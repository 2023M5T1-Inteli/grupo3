import neo4j from "neo4j-driver";

const driver = neo4j.driver(
  "neo4j+s://41f6b34f.databases.neo4j.io",
  neo4j.auth.basic("neo4j", "9Mk9OO68J2Xw1z-GaVY2XcPdIa-y4gwwcIqdKXdGYWE")
);

export default driver;
