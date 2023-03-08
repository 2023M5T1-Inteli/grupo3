import graphService from "../Services/graphServices.js";

class GraphController {

    async getGraph(req, res) {

		res.send("");
	
	}
    
	async createRoute(req, res) {
		await graphService.createRoute();
	}

	async getFinalPath(req, res){
		res.send(await graphService.getFinalPath());
	}

}

export default new GraphController();