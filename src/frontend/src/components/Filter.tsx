// Import of files and libraries used
import './Filter.css'
import { IoIosArrowForward } from "react-icons/io";
import { useState } from 'react';
import * as d3 from 'd3';

// Interface used to define the props of the component. The show prop is used to show or hide the filter menu. 
// The toggleFilter prop is used to change the state of the show prop.
interface FilterProps {
  show: boolean;
  toggleFilter: () => void;
}

// The function getHeights() is used to get the heights of the nodes from the backend api. 
// It returns an array of numbers representing the heights of each node.
async function getHeights() {
  let heigths: number[] = [];
  await fetch("http://10.128.64.241:4000/graph").then(response => response.json()).then(data => {
    // for each element in the data array, push the averageHeight property to the heigths array.
    data.forEach(function(element: any) {
      heigths.push(element.averageHeight)
    })
  }).catch(error => alert(error.message));
  return heigths;
}

// The function nodeHeight() is used to add the height of each node as a title to the node using the d3 library.
async function nodeHeight() {
  // get the heights of the nodes from the backend api using the getHeights() function.
  const heights = await getHeights()
  const nodes = d3.selectAll("circle")
  // for each node, add a title with the height of the node.
  nodes.each(function(d: unknown, i: number) {
    d3.select(this).append("title").text(heights[i] + "m");
  }) 
}


// The function colorLinks() is used to color the links of the graph based on the height variance of the nodes using the d3 library.
function colorLinks() {
  // At the moment, the height variance is hardcoded. In the future, it will be calculated based on the json from the backend api.
  const heightVar : number[] = [-1, 1, -1, 1]
  const links = d3.selectAll("line")
  links.each(function(d: unknown, i: number) {
    // blue color for negative height variance (going down) and orange color for positive height variance (going up).
    if (heightVar[i] < 0) {
      d3.select(this).attr("fill", "blue").attr("stroke", "blue")
    }
    else {
      d3.select(this).attr("fill", "orange").attr("stroke", "orange")
    }
  }) 
}

// The component Filter is used to show the filter menu. 
// It has a button to close the menu and four buttons to activate the filters.
function Filter(props: FilterProps) {
  // The show prop is used to show or hide the filter menu.
  const { show, toggleFilter } = props;
  
  // The clickedButtons state is used to keep track of the buttons that are clicked.
  const [clickedButtons, setClickedButtons] = useState<number[]>([])

  // The activateFilters() function is used to activate the filters based on the index of the button that was clicked.
  function activateFilters(index: number) {
    // The switch statement is used to activate the filters based on the index of the button that was clicked.
    switch (index) {
      case 1:
        break;
      // call the nodeHeight() function to add the height of each node as a title to the node when the second button is clicked.
      case 2:
        nodeHeight()
        break;
      case 3:
        break;
      // call the colorLinks() function to color the links of the graph based on the height variance of the nodes when the fourth button is clicked.
      case 4:
        colorLinks()
        break;
      default:
        break;
    }
  }

  // The deactivateFilters() function is used to deactivate the filters based on the index of the button that was clicked.
  function deactivateFilters(index: number) {
    switch (index) {
      case 1:
        break;
      // remove the titles of the nodes when the second button is clicked again.
      case 2:
        const heights = document.querySelectorAll("title")!
        heights.forEach((currentValue) => {currentValue.remove()})
        break;
      case 3:
        break;
      // remove the colors of the links when the fourth button is clicked again.
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

  // The handleClick() function is used to handle the click of the buttons.
  const handleClick = (index: number) => {
    // if the button is already clicked, remove it from the clickedButtons state and deactivate the filter.
    if (clickedButtons.includes(index)) {
      setClickedButtons(clickedButtons.filter(i => i !== index))
      deactivateFilters(index);
    }
    // if the button is not clicked, add it to the clickedButtons state and activate the filter.
    else {
      setClickedButtons([...clickedButtons, index])
      activateFilters(index);
    }
  }

  // The component returns the filter menu.
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