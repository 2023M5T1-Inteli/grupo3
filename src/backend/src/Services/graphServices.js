import driver from "../neo4j/neo4j.js"

class GraphService {

    async createNode(){
        const session = driver.session()

        try {
            const result = await session.run(
                'CREATE (a:Person {name: $name}) RETURN a',
                { name: "Teste" }
            )

            // const singleRecord = result.records[0]
            // const node = singleRecord.get(0)

            // console.log(node.properties.name)
        } 
        finally {
            return await session.close()
        }

        // on application exit:
        // await driver.close()
    }

    async getNode(){
        const session = driver.session()

        try {
            const result = await session.run(
                'MATCH (n) RETURN n'
            )
            
            const nodeFields = result.records.map(queries => queries._fields[0]);
            console.log(nodeFields);
            const nodeProperties = result.records.map(fields => fields);

            return nodeProperties;
        } 
        finally {
            await session.close()
        }
    }

}

export default new GraphService();