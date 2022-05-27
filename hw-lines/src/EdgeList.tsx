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
interface EdgeListProps {
    onChange(edges: string): void;
    draw(): void;
    clear(): void;
    value: string;
}


/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps> {
    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={(e) => {
                        this.props.onChange(e.target.value)
                    }}
                    value={this.props.value}
                /> <br/>
                <button onClick={() => {
                    this.props.draw()
                }}
                >Draw</button>
                <button onClick={() => {
                    this.props.clear()
                }}
                >Clear</button>
            </div>
        );
    }
}

export default EdgeList;
