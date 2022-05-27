/*
 * Copyright (C) 2022 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, { Component } from "react";
import EdgeList from "./EdgeList";
import Map from "./Map";
import MapLine from "./MapLine";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    edges: [number, number, number, number, string][];
    textInput: string
}

class App extends Component<{}, AppState> { // <- {} means no props.

  constructor(props: any) {
    super(props);
    this.state = {
        edges: [],
        textInput: '',
    };
  }

  render() {
    return (
      <div>
        <h1 id="app-title">Line Mapper!</h1>
        <div>
          <Map edges={this.state.edges}/>
        </div>
        <EdgeList
          // callback collects the given text in the textbox for later processing.
          onChange={(value) => {
            this.setState({
                textInput: value,
            });
          }}
          // callback parses through the given input and sets it as line fields
          // within edges.
          draw={() => {
            let newEdges: [number, number, number, number, string][] = [];
            let lines: string[] = this.state.textInput.split("\n");
            // break down by line
            for (let line of lines) {
                let tokens: string[] = line.split(/\s+/);
                // collect line fields
                if (tokens.length === 5) {
                    let x1: number = parseInt(tokens[0]);
                    let y1: number = parseInt(tokens[1]);
                    let x2: number = parseInt(tokens[2]);
                    let y2: number = parseInt(tokens[3]);
                    let color: string = tokens[4].trim();
                    let mapLine: [number, number, number, number, string] = [x1, y1, x2, y2, color];
                    newEdges.push(mapLine);
                }
            }
            this.setState({
                edges: newEdges,
            });
          }}

          clear={() => {
            this.setState({
                edges: [],
                textInput: '',
            });
          }}

          value={this.state.textInput}
        />
      </div>
    );
  }
}

export default App;
