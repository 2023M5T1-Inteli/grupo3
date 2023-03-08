import driver from "../neo4j/neo4j.js";
import client from '../mongodb/mongodb.js';

class GraphService {
    async connect() {
        try {
            await client.connect();
            console.log('Conectado ao servidor do MongoDB');
        } catch (error) {
            console.log('Erro ao conectar ao servidor do MongoDB', error);
        }
    }

    async getFinalPath(){
        const session = driver.session();

        try {
            const result = await session.run(
                'MATCH (n:NewCoodinate) RETURN n'
            );
            
            const nodeFields = result.records.map(queries => queries._fields[0]);
            
            const nodeProperties = nodeFields.map(fields => fields.properties);

            return nodeProperties;
        } 
        finally {
            await session.close();
        }
    }


    async createRoute(){
        await this.connect();
        
        const minhaColecao = client.db('Flightwise').collection('routes');

        const documento = { routeID: '', status: 'Creating' };
        const resultado = await minhaColecao.insertOne(documento);
        
        await client.close();
    }
}

export default new GraphService();