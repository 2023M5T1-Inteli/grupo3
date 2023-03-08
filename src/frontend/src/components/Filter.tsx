import './Filter.css'
import { IoIosArrowBack } from "react-icons/io";
import { useState } from 'react';
import * as d3 from 'd3';

interface FilterProps {
  show: boolean;
  toggleFilter: () => void;
}

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
  const svg = d3.select<SVGElement, unknown>("svg");
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

async function getHeights() {
  let heigths: number[] = [];
  await fetch("http://localhost:4000/graph").then(response => response.json()).then(data => {
    data.forEach(function(element: any) {
      heigths.push(element.averageHeight)
    })
  })
  return heigths;
}

async function nodeHeight() {
  const heights = await getHeights()
  const nodes = d3.selectAll("circle")
  nodes.each(function(d: unknown, i: number) {
    d3.select(this).append("title").text(heights[i] + "m");
  }) 
}

function colorLinks() {
  const heightVar : number[] = [-1, 1, -1, 1]
  const links = d3.selectAll("line")
  links.each(function(d: unknown, i: number) {
    if (heightVar[i] < 0) {
      d3.select(this).attr("fill", "blue").attr("stroke", "blue")
    }
    else {
      d3.select(this).attr("fill", "orange").attr("stroke", "orange")
    }
  }) 
}

function Filter(props: FilterProps) {
  const { show, toggleFilter } = props;
  
  const [clickedButtons, setClickedButtons] = useState<number[]>([])

  function activateFilters(index: number) {
    switch (index) {
      case 1:
        createGraph()
        break;
      case 2:
        nodeHeight()
        break;
      case 3:
        console.log("3 apertado")
        break;
      case 4:
        colorLinks()
        break;
      default:
        break;
    }
  }

  function desactivateFilters(index: number) {
    switch (index) {
      case 1:
        const svg = document.querySelector("svg")!;
        while (svg.firstChild) {
          svg.removeChild(svg.firstChild);
        }
        break;
      case 2:
        const heights = document.querySelectorAll("title")!
        heights.forEach((currentValue) => {currentValue.remove()})
        break;
      case 3:
        console.log("3 apertado")
        break;
      case 4:
        const links = d3.selectAll("line")
        links.each(function(d: unknown, i: number) {
          d3.select(this).attr("fill", "#999").attr("stroke", "#999")
        })
        break;
      default:
        break;
    }
  }

  const handleClick = (index: number) => {
    if (clickedButtons.includes(index)) {
      setClickedButtons(clickedButtons.filter(i => i !== index))
      desactivateFilters(index);
    }
    else {
      setClickedButtons([...clickedButtons, index])
      activateFilters(index);
    }
  }


  return (
    <div className={`container ${show ? 'show' : ''}`}>
      <div className={`menu ${show ? 'show' : ''}`}>
        <div className="filter-header">
          <p>Filtro</p>
          <button id='close-button' onClick={toggleFilter} style={{"cursor" : "pointer"}} ><IoIosArrowBack /></button>
        </div>
        <hr/>
        <div className="filter-buttons">
          <button id='1' className={`filter-button ${clickedButtons.includes(1)  ? "clicked" : ""}`} onClick={() => handleClick(1)}>Mostrar zonas de exclusão</button>
          <button id='2' className={`filter-button ${clickedButtons.includes(2)  ? "clicked" : ""}`} onClick={() => handleClick(2)}>Altitude nos nós (passe o mouse em cima do nó)</button>
          <button id='3' className={`filter-button ${clickedButtons.includes(3)  ? "clicked" : ""}`} onClick={() => handleClick(3)}>Mudar tipo de visualização do mapa</button>
          <button id='4' className={`filter-button ${clickedButtons.includes(4)  ? "clicked" : ""}`} onClick={() => handleClick(4)}>Ativar variação de altitude nas arestas</button>
        </div>
      </div>
      <div className="background"></div>
    </div>
  )
}

export default Filter