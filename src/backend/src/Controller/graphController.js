import graphService from "../Services/graphServices.js";

class GraphController {

    async getGraph(req, res) {

		res.send("");
	
	}
    
	async createNode(req, res) {
		await graphService.createNode();
	}

	async getFinalPath(req, res){
		res.send(await graphService.getFinalPath());
	}

}

export default new GraphController();