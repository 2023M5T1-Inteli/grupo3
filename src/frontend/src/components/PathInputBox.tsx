// Import the files and libraries used
import { Button, TextField } from "@mui/material";
import { Box } from "@mui/system";
import Grid2 from "@mui/material/Unstable_Grid2/Grid2";
import * as d3 from 'd3';

function PathInputBox() {
  // The Node interface is used to define the nodes of the graph. It has an id, a name, and x and y coordinates.
  interface Node {
    id: number;
    name: string;
    x?: number;
    y?: number;
  }

  // The Link interface is used to define the links of the graph. It has the source node and the target node.
  interface Link {
    source: Node;
    target: Node;
  }
  
  // The nodes and links arrays are used to store the nodes and links of the graph.
  const nodes: Node[] = [];
  const links: Link[] = [];
  
  // The function getGraph() is used to get the nodes and links of the graph from the backend api. 
  // It adds the nodes and links to the nodes and links arrays respectively.
  async function getGraph() {
    await fetch("http://localhost:4000/grap").then(response => response.json()).then(data => {
      // For each element in the data array, push the node to the nodes array.
      data.forEach(function(element: any) {
        nodes.push({id: element.index.low, name: "A", x: element.latitude, y: element.longitude})
      });
      data.forEach(function(element: any) {
        // The lastNodeIndex and NodeIndex variables are used to find the index of the source and target nodes of the link.
        const lastNodeIndex = nodes.findIndex(node => node.id === element.lastNode.low)
        const NodeIndex = nodes.findIndex(node => node.id === element.index.low)
        // If the lastNodeIndex is not equal to the NodeIndex, push the link to the links array. This avoids adding a link to the same node (self-loop).
        if (lastNodeIndex !== NodeIndex) links.push({source: nodes[lastNodeIndex], target: nodes[NodeIndex]})
      });
    }).catch(error => alert(error.message));
  }
  
  // The function createGraph() is used to create the graph using the d3 library and the nodes and links arrays.
  async function createGraph() {
    await getGraph()
    // The svg variable is used to select the svg element in the html file.
    const svg = d3.select<SVGElement, unknown>(".graph");
    const width = +svg.attr("width")!;
    const height = +svg.attr("height")!;

    // The simulation variable is used to create the simulation of the graph.
    const simulation = d3.forceSimulation<Node>(nodes)
    .force("link", d3.forceLink<Node, Link>(links).id((d) => d.id).distance(80))
    .force("charge", d3.forceManyBody<Node>().strength(-200))
    .force("center", d3.forceCenter<Node>(width / 2, height / 2));

    // The arrowhead variable is used to create the arrowhead of the links.
    svg.append("defs").append("marker")
      .attr("id", "arrowhead")
      .attr("viewBox", "0 -5 10 10")
      .attr("refX", 35)
      .attr("markerWidth", 8)
      .attr("markerHeight", 8)
      .attr("orient", "auto")
      .append("path")
      .attr("d", "M0,-5L10,0L0,5")
      .attr("fill", "#999");

    // The link and node variables are used to create the links and nodes of the graph.
    const link = svg.append("g")
      .attr("class", "links")
      .selectAll("line")
      .data(links)
      .enter().append("line")
      .attr("class", "link")
      .attr("stroke", "#999")
      .attr("fill", "#999")
      .attr("marker-end", "url(#arrowhead)");;

    const node = svg.append("g")
        .attr("class", "nodes")
        .selectAll("circle")
        .data(nodes)
        .enter()
        .append("circle")
        .attr("class", "node")
        .attr("r", 15)
        .attr("fill", "steelblue")

    // The simulation.on() function is used to update the links and nodes of the graph according to the simulation and the nodes x and y coordinates.
    simulation.on("tick", () => {
      link.attr("x1", d => d.source.x!)
      .attr("y1", d => d.source.y!)
      .attr("x2", d => d.target.x!)
      .attr("y2", d => d.target.y!);

      node.attr("cx", d => d.x!)
        .attr("cy", d => d.y!);
    });
  }

  return (
    <Grid2
      container
      direction="column"
      alignItems="center"
      justifyContent="center"
    >
      <Grid2 bgcolor={"#F1F1F1"} padding={3} borderRadius={4} xs={10}>
        <Grid2 container spacing={4}>
          <Grid2 xs={5}>
            <Grid2
              container
              bgcolor={"#D9D9D9"}
              spacing={2}
              padding={2}
              borderRadius={4}
            >
              <Grid2 xs={12}textAlign={"start"}>
                Origem
              </Grid2>
              <Grid2 xs={6}>
                <TextField
                  id="standard-basic"
                  label="Latitude"
                  variant="standard"
                  sx={{ width: "100%" }}
                />
              </Grid2>
              <Grid2 xs={6}>
                <TextField
                  id="standard-basic"
                  label="Longitude"
                  variant="standard"
                  sx={{ width: "100%" }}
                />
              </Grid2>
            </Grid2>
          </Grid2>
          <Grid2 xs={5}>
            <Grid2
              container
              bgcolor={"#D9D9D9"}
              spacing={2}
              padding={2}
              borderRadius={4}
            >
               <Grid2 xs={12}textAlign={"start"}>
                Destino
              </Grid2>
              <Grid2 xs={6}>
                <TextField
                  id="standard-basic"
                  label="Latitude"
                  variant="standard"
                  sx={{ width: "100%" }}
                />
              </Grid2>
              <Grid2 xs={6}>
                <TextField
                  id="standard-basic"
                  label="Longitude"
                  variant="standard"
                  sx={{ width: "100%" }}
                />
              </Grid2>
            </Grid2>
          </Grid2>
          <Grid2
            padding={3}
            container
            direction="column"
            alignItems="center"
            justifyContent="center"
            xs={2}
          >
            <Button onClick={createGraph} variant="contained">
              <Box padding={1} width={100}>
                Gerar
              </Box>
            </Button>
          </Grid2>
        </Grid2>
      </Grid2>
    </Grid2>
  );
}

export default PathInputBox;
