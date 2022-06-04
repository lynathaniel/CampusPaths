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

import { LatLngExpression } from "leaflet";
import React, { Component } from "react";
import { MapContainer, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import MapLine from "./MapLine";
import { UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER } from "./Constants";

// This defines the location of the map. These are the coordinates of the UW Seattle campus
const position: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

// Takes a single prop that defines an array of line fields.
interface MapProps {
    edges: [number, number, number, number, string][];
}

class Map extends Component<MapProps> {

    // arrow functions that converts edges to MapLines to be rendered.
    drawLines = () => {
        if (this.props.edges !== null) {
            return this.props.edges.map((edge, index) => {
                return <MapLine x1={edge[0]} y1={edge[1]} x2={edge[2]} y2={edge[3]} color={edge[4]} key={index}/>
            });
        }
    };
    render() {
        return (
            <div id="map">
                <MapContainer
                    center={position}
                    zoom={15}
                    scrollWheelZoom={false}
                >
                    <TileLayer
                        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />
                    {
                        this.drawLines()
                    }
                </MapContainer>
            </div>
        );
    }
}

export default Map;
