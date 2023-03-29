import { Route, Routes, useLocation } from "react-router-dom";

// Import the pages
import AddCoordinates from "./pages/AddCoordinates";
import AddExclusionZone from "./pages/AddExclusionZone";
import Home from "./pages/Home";
import Loading from "./pages/Loading";
import Result from "./pages/Result";

import { AnimatePresence, motion } from "framer-motion";

function MainRoute() {
  const location = useLocation();
  return (
    <AnimatePresence>
   
        <Routes location={location} key={location.pathname}>
          <Route path="/" element={<Home />} />
          <Route path="/AddCoordinates" element={<AddCoordinates />} />
          <Route path="/AddExclusionZone" element={<AddExclusionZone />} />
          <Route path="/Loading" element={<Loading />} />
          <Route path="/Result" element={<Result />} />
        </Routes>
    </AnimatePresence>
  );
}

export default MainRoute;
