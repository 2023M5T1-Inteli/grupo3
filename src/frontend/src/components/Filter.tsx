import './Filter.css'

interface FilterProps {
  show: boolean;
}

function Filter(props: FilterProps) {
  return (
    <div className={`container ${props.show ? 'show' : ''}`}>
      <div className={`menu ${props.show ? 'show' : ''}`}>
      </div>
      <div className="background"></div>
    </div>
  )
}

export default Filter