// Import necessary modules
import graphService from "../Services/graphServices.js";

class GraphController {
	async createRoute(req, res) {
		// Send a response object with a routeID attribute
		res.send({
			routeID: await graphService.createRoute()
		});
	}

	async checkRouteStatus(req, res){
		// Send a response object with the status of the route
		res.send(await graphService.checkRouteStatus(req.body.routeID));
	}

	async getFinalPath(req, res){
		// Send a response object with the final path vertexes
		res.send(await graphService.getFinalPath(req.body.pathID));
	}
}

export default new GraphController();