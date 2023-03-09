import { Button, TextField } from "@mui/material";
import { Box } from "@mui/system";
import Grid2 from "@mui/material/Unstable_Grid2/Grid2";
import * as d3 from 'd3';

function PathInputBox() {
  interface Node {
    id: number;
    name: string;
    x?: number;
    y?: number;
  }
  
  interface Link {
    source: Node;
    target: Node;
  }
  
  const nodes: Node[] = [];
  
  const links: Link[] = [];
  
  async function getGraph() {
    await fetch("http://localhost:4000/graph").then(response => response.json()).then(data => {
      data.forEach(function(element: any) {
        nodes.push({id: element.index.low, name: "A", x: element.latitude, y: element.longitude})
      });
      data.forEach(function(element: any) {
        const lastNodeIndex = nodes.findIndex(node => node.id === element.lastNode.low)
        const NodeIndex = nodes.findIndex(node => node.id === element.index.low)
        if (lastNodeIndex !== NodeIndex) links.push({source: nodes[lastNodeIndex], target: nodes[NodeIndex]})
      });
    });
  }
  
  async function createGraph() {
    await getGraph()
    // Visualização do grafo
    const svg = d3.select<SVGElement, unknown>(".graph");
    const width = +svg.attr("width")!;
    const height = +svg.attr("height")!;

    const simulation = d3.forceSimulation<Node>(nodes)
    .force("link", d3.forceLink<Node, Link>(links).id((d) => d.id).distance(80))
    .force("charge", d3.forceManyBody<Node>().strength(-200))
    .force("center", d3.forceCenter<Node>(width / 2, height / 2));

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
