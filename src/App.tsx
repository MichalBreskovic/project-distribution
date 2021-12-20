import React from 'react';
import './App.css';
import MainComponent from "./components/MainComponent";
import {Route, BrowserRouter as Router} from "react-router-dom";
import AddTrain from "./components/AddTrain";

function App() {
  return (
    <div className="App">
        <AddTrain />
        <MainComponent />
    </div>
  );
}

export default App;
