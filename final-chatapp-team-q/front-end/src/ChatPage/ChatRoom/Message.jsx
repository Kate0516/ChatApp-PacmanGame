import React, {useState} from 'react'
import style from './Message.css'
import { TextField } from '@material-ui/core';
import IconButton from '@material-ui/core/IconButton';
import EditIcon from '@material-ui/icons/Edit';
import SaveIcon from '@material-ui/icons/Save';
import DeleteIcon from '@material-ui/icons/Delete';

class Message extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            // body: this.props.message.body,
            editing: false,
            editedMsg: ""
        };
    }

    saveEditedMsg(e) {
        this.setState({ editedMsg: e.target.value });
    }

    render() {
        return (
            <div className="message">
                <div className="message-info">
                    <h4>{this.props.message.sender}
                        {/* {this.props.message.username} */}
                        {/* {this.props.message.time} */}
                        <span className="message-timestamp">{this.props.message.time}</span>
                    </h4>
                    {/* <p className="message-body">{this.props.message.body}</p> */}
                    {this.state.editing ? (
                        <TextField
                            // type="text"
                            variant="outlined"
                            size="small" 
                            defaultValue={this.props.message.body}
                            // ref={this.messageEditor}
                            onChange={this.saveEditedMsg.bind(this)}
                        />
                    ) : (
                        <p className="message-body">{this.props.message.body}</p>
                    )}
                </div>

                {this.props.authority ? (
                    <div className="message-icons">
                        {this.state.editing ? (
                            <IconButton aria-label="save" color="inherit" onClick={() => {this.setState({ body: this.state.editedMsg, editing: !this.state.editing }); this.props.sendEditReq(this.props.index, this.state.editedMsg );}}>
                                <SaveIcon/>
                            </IconButton>
                        ) : (
                            <IconButton aria-label="edit" color="inherit" onClick={() => {this.setState({ editing: !this.state.editing })}}>
                                <EditIcon/>
                            </IconButton>
                        )}
                        <IconButton aria-label="delete" color="inherit" onClick={this.props.handleDelete.bind(this)}>
                            <DeleteIcon/>
                        </IconButton>
                    </div>
                ) : null}
            </div>
        )
    }
}

export default Message
