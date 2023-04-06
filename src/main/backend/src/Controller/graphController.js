// Import necessary modules
import graphService from "../Services/graphServices.js";

class GraphController {
	async createRoute(req, res) {
		if (req.body.entryPoints == undefined ||
			req.body.exitPoints == undefined ||
			req.body.exclusionPoints == undefined){
			res.send("Invalid inputs: entryPoints, exitPoints and exclusionPoints can't be null.");
			return;
		}
		
		// Send a response object with a routeID attribute
		res.send({
			routeID: await graphService.createRoute(req.body.entryPoints, req.body.exitPoints, req.body.exclusionPoints)
		});
	}

	async checkRouteStatus(req, res){
		if (req.params.pathID == undefined){
			res.send("Invalid inputs: routeID can't be null.");
			return;
		}

		// Send a response object with the status of the route
		res.send(await graphService.checkRouteStatus(req.params.pathID));
	}

	async getFinalPath(req, res){
		if (req.params.pathID == undefined){
			res.send("Invalid inputs: pathID can't be null.");
			return;
		}
		
		// Send a response object with the final path vertexes
		res.send(await graphService.getFinalPath(req.params.pathID));
	}
}

export default new GraphController();