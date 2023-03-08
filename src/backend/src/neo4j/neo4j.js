import neo4j from 'neo4j-driver'

const driver = neo4j.driver("<URI>", neo4j.auth.basic("<USERNAME>", "<PASSWORD>"))

export default driver
