import driver from "../neo4j/neo4j.js"

class GraphService {

    async getFinalPath(){
        const session = driver.session()

        try {
            const result = await session.run(
                'MATCH (n:NewCoodinate) RETURN n'
            )
            
            const nodeFields = result.records.map(queries => queries._fields[0]);
            
            const nodeProperties = nodeFields.map(fields => fields.properties);

            return nodeProperties;
        } 
        finally {
            await session.close()
        }
    }

}

export default new GraphService();