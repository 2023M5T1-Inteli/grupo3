import './Filter.css'
import { IoIosArrowForward } from "react-icons/io";
import { useState } from 'react';
import * as d3 from 'd3';

interface FilterProps {
  show: boolean;
  toggleFilter: () => void;
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
        break;
      case 2:
        nodeHeight()
        break;
      case 3:
        break;
      case 4:
        colorLinks()
        break;
      default:
        break;
    }
  }

  function deactivateFilters(index: number) {
    switch (index) {
      case 1:
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
      deactivateFilters(index);
    }
    else {
      setClickedButtons([...clickedButtons, index])
      activateFilters(index);
    }
  }


  return (
    <div className={`container ${show ? 'show' : ''}`}>
       <div className="background"></div>
      <div className={`menu ${show ? 'show' : ''}`}>
        <div className="filter-header">
          <button id='close-button' onClick={toggleFilter} style={{"cursor" : "pointer"}} ><IoIosArrowForward /></button>
          <p>Filtro</p>
        </div>
        <hr/>
        <div className="filter-buttons">
          <button id='1' className={`filter-button ${clickedButtons.includes(1)  ? "clicked" : ""}`} onClick={() => handleClick(1)}>Mostrar zonas de exclusão</button>
          <button id='2' className={`filter-button ${clickedButtons.includes(2)  ? "clicked" : ""}`} onClick={() => handleClick(2)}>Altitude nos nós (passe o mouse em cima do nó)</button>
          <button id='3' className={`filter-button ${clickedButtons.includes(3)  ? "clicked" : ""}`} onClick={() => handleClick(3)}>Mudar tipo de visualização do mapa</button>
          <button id='4' className={`filter-button ${clickedButtons.includes(4)  ? "clicked" : ""}`} onClick={() => handleClick(4)}>Ativar variação de altitude nas arestas</button>
        </div>
      </div>
     
    </div>
  )
}

export default Filter