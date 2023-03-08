import graphService from "../Services/graphServices.js";

class GraphController {

    async getGraph(req, res) {

		res.send("");
	
	}
    
	async createRoute(req, res) {
		res.send(await graphService.createRoute());
	}

	async checkRouteStatus(req, res){
		res.send(await graphService.checkRouteStatus(req.body.routeID));
	}

	async getFinalPath(req, res){
		res.send(await graphService.getFinalPath(req.body.pathID));
	}

}

export default new GraphController();