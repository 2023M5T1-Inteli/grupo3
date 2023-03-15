import neo4j from "neo4j-driver";
import dotenv from "dotenv";

dotenv.config({ path: '../.env' });

// const driver = neo4j.driver("neo4j+s://41f6b34f.databases.neo4j.io", neo4j.auth.basic("neo4j", "9Mk9OO68J2Xw1z-GaVY2XcPdIa-y4gwwcIqdKXdGYWE"))
const driver = neo4j.driver(process.env.NEO4J_URL, neo4j.auth.basic(process.env.NEO4J_USERNAME, process.env.NEO4J_PASSWORD))

export default driver;
