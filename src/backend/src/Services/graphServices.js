import driver from "../neo4j/neo4j.js";
import client from "../mongodb/mongodb.js";
import request from "request";

class GraphService {
  async connect() {
    try {
      await client.connect();
      console.log("Conectado ao servidor do MongoDB");
    } catch (error) {
      console.log("Erro ao conectar ao servidor do MongoDB", error);
    }
  }

  async getFinalPath(pathID) {
    const session = driver.session();

    try {
      const result = await session.run(
        "MATCH (n:NewCoordinate {pathID: $path}) RETURN n",
        {
          path: pathID,
        }
      );

      const nodeFields = result.records.map((queries) => queries._fields[0]);

      const nodeProperties = nodeFields.map((fields) => fields.properties);

      return nodeProperties;
    } finally {
      await session.close();
    }
  }

  async createRoute() {
    await this.connect();

    const minhaColecao = client.db("Flightwise").collection("routes");

    const code = await this.generateCode();

    const documento = { routeID: code, status: "Creating" };
    const resultado = await minhaColecao.insertOne(await documento);

    await client.close();

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

    return code;
  }

  async generateCode() {
    const letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    const numeros = "0123456789";

    let code = "";

    for (let i = 0; i < 7; i++) {
      if (i < 3) {
        code += letras.charAt(Math.floor(Math.random() * letras.length));
      } else if (i < 6) {
        code += numeros.charAt(Math.floor(Math.random() * numeros.length));
      } else {
        code += letras.charAt(Math.floor(Math.random() * letras.length));
      }
    }

    return code;
  }

  async checkRouteStatus(id) {
    await this.connect();

    const collection = await client.db("Flightwise").collection("routes");

    const result = await collection.findOne({ routeID: id });

    await client.close();

    return result;
  }
}

export default new GraphService();
