import graphService from "../Services/graphServices.js";

class GraphController {

    async getGraph(req, res) {

		res.send("");
	
	}
    
	async createNode(req, res) {
		await graphService.createNode();
	}

	async getNode(req, res){
		res.send(await graphService.getNode());
	}

}

export default new GraphController();