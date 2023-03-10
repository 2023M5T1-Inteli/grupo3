// Import necessary modules
import driver from "../neo4j/neo4j.js";
import client from "../mongodb/mongodb.js";
import request from "request";

class GraphService {
  // Function to connect to mongoDB database
  async connect() {
    try {
      await client.connect();
      console.log("Conectado ao servidor do MongoDB");
    } catch (error) {
      console.log("Erro ao conectar ao servidor do MongoDB", error);
    }
  }

  // This is an asynchronous function that takes in a path ID as an argument
  async getFinalPath(pathID) {
    // A new session is opened using the driver object 
    const session = driver.session();

    try {
        // Execute a query to finds all nodes that have a "pathID" property that matches the pathID argument.
        const result = await session.run(
            "MATCH (n:NewCoordinate {pathID: $path}) RETURN n",
            {
                path: pathID,
            }
        );

        // The result object is mapped to an array of node objects
        const nodeFields = result.records.map((queries) => queries._fields[0]);

        // The node objects are further mapped to an array of their properties
        const nodeProperties = nodeFields.map((fields) => fields.properties);

        return nodeProperties;
    } finally {
        // Close session
        await session.close();
    }
  }

  // This function creates a new route in a MongoDB database and returns the generated route ID
  async createRoute() {

    // Connect to the MongoDB database
    await this.connect();

    // Get a reference to the "routes" collection in the "Flightwise" database
    const minhaColecao = client.db("Flightwise").collection("routes");

    // Generate a random route ID
    const code = await this.generateCode();

    // Create a new document for the route with the generated ID and a status of "Creating"
    const documento = { routeID: code, status: "Creating" };

    // Insert the new document into the "routes" collection
    const resultado = await minhaColecao.insertOne(await documento);

    // Close the connection to the MongoDB database
    await client.close();

    // Make a POST request to an external API to execute an algorithm for the new route
    request.post("http://localhost:8080/executeAlg", {
      json: {
        lonInitial: -43.4082,
        latInitial: -22.178,
        lonFinal: -43.4056,
        latFinal: -22.181300000000004,
        dt2file: null,
        pathID: "ABC123A",
      },
    });

    // Return the generated route ID
    return code;
  }


  // This function generates a random code consisting of 7 characters
  async generateCode() {

    // Define two strings of characters to choose from
    const letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    const numbers = "0123456789";

    // Start with an empty code string
    let code = "";

    // Loop 7 times to create a 7-character code
    for (let i = 0; i < 7; i++) {

      // If we're on one of the first 3 characters, choose a random letter
      if (i < 3) {
        code += letters.charAt(Math.floor(Math.random() * letters.length));
      } 
      
      // If we're on one of the next 3 characters, choose a random number
      else if (i < 6) {
        code += numbers.charAt(Math.floor(Math.random() * numbers.length));
      } 
      
      // If we're on the last character, choose a random letter again
      else {
        code += letters.charAt(Math.floor(Math.random() * letters.length));
      }
    }

    // Return the completed code
    return code;
  }

  // This is an asynchronous function that takes in a route ID as an argument
  async checkRouteStatus(id) {
    // This calls the `connect()` function, which presumably sets up a database connection.
    await this.connect();

    // A reference to a MongoDB collection.
    const collection = await client.db("Flightwise").collection("routes");

    // A query is executed on the MongoDB collection using the `findOne()` method.
    const result = await collection.findOne({ routeID: id });

    // The MongoDB client connection is closed
    await client.close();

    // The result object is returned
    return result;
  }
}

export default new GraphService();
