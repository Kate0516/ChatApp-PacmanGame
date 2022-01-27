import React, {useState} from "react"
import 'antd/dist/antd.css';
import "./style.css"
import defaultPic from "./default-group-chat.jpg"
import {get} from "../util";
import {error} from "../util/message";

export function ChatRooms(props) {

    const handleOnClick = (chatRoomId, chatRoomName) => {
        // set the current room ID
        console.log('setting', chatRoomId, chatRoomName)
        console.log(props)
        let curRoom = props.currentRoom;
        curRoom.roomId = chatRoomId;
        curRoom.roomName = chatRoomName;
        props.setCurrentRoom({...curRoom});
        // Check if user is the admin of current room
        get("/chatroom", (res) => {
            if (res.success) {
                let room = res.payload.filter((room) =>{
                    return room.chatroom_id === curRoom.roomId;
                })[0];
                if (room) {
                    props.setUserList(room.users);
                    props.setCurrentUser({
                        ...props.currentUser,
                        isAdmin: room.admins.includes(props.currentUser.username)
                    });
                    let currentMessage = props.messages.filter((message)=>message.roomId === curRoom.roomId);
                    props.setCurMsgList([...currentMessage]);
                }

            } else {
                error(res.message);
            }
        }, props.currentUser.token);

        // // Set users of the current chat room
        // get("/chatroom/" + props.currentRoom.roomId + "/members", (res) => {
        //     let users = [];
        //     res.payload.map(user => {
        //         users.push(user.username);
        //     });
        //     props.setUserList(users);
        // }, props.currentUser.token);
    }
    return (
        <div>
            {props.chatRoomList.map(chatroom => {
                return(
                    <div className="outer-box" onClick={() => handleOnClick(chatroom.chatroom_id, chatroom.chatroom_name)} key={chatroom.chatroom_id}>
                        <img className="groupImg" src={defaultPic} alt={""} style={{width: "50px"}}/>
                        <b>{chatroom.chatroom_name}</b>
                        <p>Room ID: {chatroom.chatroom_id}</p>
                    </div>
                );
            })}
        </div>
    );

}