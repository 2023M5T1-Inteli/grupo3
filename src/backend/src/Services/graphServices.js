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
            await session.close()
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
            
            return result;
        } 
        finally {
            await session.close()
        }
    }

}

export default new GraphService();