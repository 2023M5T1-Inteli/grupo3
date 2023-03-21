// Import necessary modules
import graphService from "../Services/graphServices.js";

class GraphController {
	async createRoute(req, res) {
		if (req.body.entryPoints == undefined ||
			req.body.exitPoints == undefined ||
			req.body.exclusionPoints == undefined || 
			req.body.intermediatePoints == undefined){
			res.send("Invalid inputs: entryPoints, exitPoints, exclusionPoints and intermediatePoints can't be null.");
			return;
		}
		
		// Send a response object with a routeID attribute
		res.send({
			routeID: await graphService.createRoute(req.body.entryPoints, req.body.exitPoints, req.body.exclusionPoints, req.body.intermediatePoints)
		});
	}

	async checkRouteStatus(req, res){
		if (req.body.routeID == undefined){
			res.send("Invalid inputs: routeID can't be null.");
			return;
		}

		// Send a response object with the status of the route
		res.send(await graphService.checkRouteStatus(req.body.routeID));
	}

	async getFinalPath(req, res){
		if (req.body.pathID == undefined){
			res.send("Invalid inputs: pathID can't be null.");
			return;
		}
		
		// Send a response object with the final path vertexes
		res.send(await graphService.getFinalPath(req.body.pathID));
	}
}

export default new GraphController();