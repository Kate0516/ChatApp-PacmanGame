import React from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap';
import styles from "./ChatBoard.css"
import ChatHeader from './ChatHeader'
import Message from './Message'
import {Button} from 'antd'
// import { Button } from 'react-bootstrap'
// import { PictureOutlined } from '@ant-design/icons'
import {IconButto, Snackbar} from '@material-ui/core';
import MuiAlert from '@material-ui/lab/Alert';
import AlternateEmailIcon from '@material-ui/icons/AlternateEmail';
import ImageIcon from '@material-ui/icons/Image';
import EmojiEmotionsIcon from '@material-ui/icons/EmojiEmotions'
import {del} from '../../util'
import InputEmoji from "react-input-emoji";

function Alert(props) {
    return <MuiAlert elevation={6} variant="filled" {...props} />;
}


class ChatBoard extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            msg: '',
            msgDest: "all"
        };
        this.props = props
        this.messageInput = React.createRef();
    }

    sendMessage() {
        this.setState({msg: ""});
        this.props.sendMessage(JSON.stringify({
            type: 'send_message',
            cookie: this.props.user.token,
            payload: {chatroom_id: this.props.room.roomId, target: this.state.msgDest, content: this.state.msg}
        }))
    }

    deleteMessage(index) {
        this.props.sendMessage(JSON.stringify({
            type: 'delete_message',
            cookie: this.props.user.token,
            payload: {message_id: this.props.messageList[index].id}
        }));
    }

    editMessage(index, body) {
        console.log('edited: new message', index, body);
        this.props.sendMessage(JSON.stringify({
            type: 'edit_message',
            cookie: this.props.user.token,
            payload: {content: body, message_id: this.props.messageList[index].id}
        }));
    }

    leaveRoom() {
        del("/chatroom/" + this.props.room.id + "/member/" + this.props.user.username, {cookie: localStorage.getItem("auth")}, (res) => {
            if (res.success) {
                this.props.setRoomList(this.props.roomList.filter(room => {
                    return room.chatroomId !== this.props.room.roomId;
                }))
            }
        })
    }


    scrollToBottom = () => {
        this.messagesEnd.scrollIntoView({behavior: "smooth"});
    }

    componentDidMount() {
        this.scrollToBottom();
    }

    componentDidUpdate() {
        this.scrollToBottom();
    }


    render() {
        return (
            <div className="chat-board">
                <ChatHeader room={this.props.room}></ChatHeader>

                <div className="chat-messages">
                    <div className="chat-messages-list">
                        {this.props.messageList.map((message, index) => {
                            return message.dest === 'all' || message.sender === this.props.user.username || message.dest === this.props.user.username ? (
                                <Message
                                    authority={this.props.user.isAdmin || this.props.user.username === message.sender}
                                    message={message} handleDelete={this.deleteMessage.bind(this, index)}
                                    sendEditReq={(index, body) => this.editMessage(index, body)} key={index}
                                    index={index}/>
                            ) : null
                        })}
                        <div style={{float: "left", clear: "both"}}
                             ref={(ele) => {
                                 this.messagesEnd = ele;
                             }}>
                        </div>
                    </div>
                </div>

                <Snackbar open={this.props.notification.on} autoHideDuration={6000}
                          onClose={(e) => this.props.setNotification({notification: {on: false, body: ''}})}>
                    <Alert onClose={(e) => this.props.setNotification({notification: {on: false, body: ''}})}
                           severity={this.props.notification.type}>
                        {this.props.notification.body}
                    </Alert>
                </Snackbar>

                <div className="chat-input">
                    <AlternateEmailIcon className="chat-input-item" fontSize="large"/>
                    <select className="chat-input-item" defaultValue="all"
                            onChange={(e) => this.setState({msgDest: e.target.value})}>
                        <option value="all">All</option>
                        {this.props.userList.map((user, index) => {
                            return this.props.user.username !== user ? (
                                <option value={user} key={index}>{user}</option>
                            ) : null
                        })}
                    </select>
                    {/* <input className="chat-input-item" type="text" ref={this.messageInput} placeholder={`message`} /> */}
                    <InputEmoji
                        value={this.state.msg}
                        onChange={(v) => this.setState({msg: v})}
                        cleanOnEnter
                        onEnter={this.sendMessage.bind(this)}
                        placeholder="Enter a message"
                    />
                    <Button className="chat-input-item" variant="outline-light" onClick={this.sendMessage.bind(this)}
                            disabled={this.props.user.banned}>Send</Button>

                </div>

            </div>
        )
    }

}

export default ChatBoard
