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
import Place from "./Place";
import Map from "./Map";
import Buttons from "./Buttons";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    buildings: [string, string][]; // key and value pairs from the given map of buildings
    edges: [number, number, number, number, string][];
    start: string;
    end: string;
    totalDistance: number;
}

interface Path {
    cost: number;
    start: Point
    path: Segment[];
}

interface Segment {
    cost: number;
    start: Point;
    end: Point;
}

interface Point {
    x: number;
    y: number;
}

// async function to request data from a given address
async function sendRequest(address: string): Promise<any> {
    try {
        let response = await fetch(address);
        if (!response.ok) {
            alert("Error fetching request.");
            return;
        }
        return await response.json();
    } catch (e) {
        alert("Error fetching request.")
    }
}

class App extends Component<{}, AppState> { // <- {} means no props.

    constructor(props: any) {
        super(props);
        this.state = {
            buildings: [],
            edges: [],
            start: 'NaN',
            end: 'NaN',
            totalDistance: 0,
        }
        this.getBuildings();
        console.log(this.state.buildings);
    }

    getBuildings = () => {
        try {
            fetch("http://localhost:4567/buildings")
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    this.setState({
                        buildings: Object.entries(data),
                    })
                });
        } catch (e) {
            alert("Error! Cannot find list of buildings.");
        }
    }

    render() {
        return (
            <div>
                <h1 id="app-title">Campus Path Finder</h1>
                <h2 id="distance">Total Distance: {this.state.totalDistance} ft</h2>
                <div>
                <Place
                    // Callback collects the given starting point building
                    onChange={(value) => {
                        this.setState({
                            start: value,
                        });
                    }}
                    label={"Starting Point:  "}
                    selectedBuilding={this.state.start}
                    buildings={this.state.buildings}
                />
                </div>
                <div>
                <Place
                    // Callback collects the given end point building
                    onChange={(value) => {
                        this.setState({
                            end: value,
                        });
                    }}
                    label={"End Point:  "}
                    selectedBuilding={this.state.end}
                    buildings={this.state.buildings}
                />
                </div>
                <Buttons
                    draw={async () => {
                        // Check if the user has left a select option blank.
                        if (this.state.start === 'NaN' || this.state.end === 'NaN') {
                            alert("Please choose 2 valid buildings.")
                            // Check if the end and start of the route are the same.
                        } else if (this.state.start === this.state.end) {
                            alert("Please choose 2 different buildings.")
                        } else {
                            let newEdges: [number, number, number, number, string][] = [];
                            let address: string =
                                "http://localhost:4567/findPath?start=" + this.state.start + "&end=" + this.state.end;
                            const parsed: Path = await sendRequest(address) as Path;
                            let segments: Segment[] = parsed.path;
                            for (let segment of segments) {
                                let start: Point = segment.start;
                                let end: Point = segment.end;
                                let x1: number = start.x;
                                let y1: number = start.y;
                                let x2: number = end.x;
                                let y2: number = end.y;
                                let mapLine: [number, number, number, number, string] = [x1, y1, x2, y2, "purple"];
                                newEdges.push(mapLine);
                            }
                            this.setState({
                                edges: newEdges,
                                totalDistance: parsed.cost,
                            });
                        }
                    }}

                    clear={() => {
                        this.setState({
                            start: '',
                            end: '',
                            edges: [],
                            totalDistance: 0,
                        })
                    }}
                />
                <div>
                    <Map edges={this.state.edges}/>
                </div>

            </div>
        );
    }
}

export default App;
