import React from 'react'
import "./ChatHeader.css"
import { Button } from 'react-bootstrap';

class ChatHeader extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div className="chat-header">
                <div className="chat-header-left">
                    <h3>
                        <span className="chat-header-room-name">
                            {/* Chat Room 0 */}
                            {this.props.room.name}
                        </span>
                        <span className="chat-header-room-id">
                            {/* Room ID:0001 */}
                            Room ID: {this.props.room.roomId}
                        </span>
                    </h3>
                </div>
                <div className="chat-header-right">
                    <Button variant="outline-light" className="chat-header-btn-leave">Leave</Button>
                </div>
            </div>
        )
    }
}

export default ChatHeader
