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

import React, {Component} from 'react';

// onChange, draw, and clear are all callback methods when called on in their
// respective actions on the line mapper.
// value is the given text inside the textbox.
interface PlaceProps {
    onChange(building: string): void;
    label: string;
    buildings: [string, string][];
    selectedBuilding: string;
}

/**
 * A dropdown menu that will allow the user to select from a given list of buildings
 */
class Place extends Component<PlaceProps> {

    // Arrow function to add the building names to the dropdown menu.
    buildList = () => {
        if (this.props.buildings !== null) {
            return this.props.buildings.map((building: [string, string]) => {
                let key: string = building[0];
                let value: string = building[1];
                return <option key={key} value={key}>{value}</option>
            })
        }
    };

    render() {
        return (
            <div id="route">
                <br/>
                {this.props.label}
                <label>
                    <select
                        value={this.props.selectedBuilding}
                        onChange={(e) => {
                            this.props.onChange(e.target.value)
                    }}>
                        <option value={'NaN'}>Select a Building</option>
                        {
                            this.buildList()
                        }
                    </select>
                </label>
                <br/>
            </div>
        );
    }
}

export default Place;
