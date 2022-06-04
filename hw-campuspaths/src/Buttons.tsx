import React, {Component} from "react";

interface ButtonProps {
    draw(): void;
    clear(): void;
}

/**
 * Buttons that the user will interact with in order to use the app.
 */
class Buttons extends Component<ButtonProps> {
    render() {
        return (
            <div id="buttons">
                <button onClick={() => {
                    this.props.draw()
                    console.log("Button has been pressed");
                }}
                >Find Route</button>
                <button onClick={() => {
                    this.props.clear()
                }}
                >Reset</button>
            </div>
        );
    }
}

export default Buttons;