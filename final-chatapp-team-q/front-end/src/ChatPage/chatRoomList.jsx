import React, {useEffect, useState} from "react"
import {get} from "../util";
import 'antd/dist/antd.css';
import "./style.css"
import {JoinChatRoom} from "./joinChatRoom";
import {CreateChatRoom} from "./createChatRoom";
import {ChatRooms} from "./chatRooms";

export function ChatRoomList(props) {
    // fetch data from backend
    useEffect(() => {
        // FIXME: should only fetch chat rooms that contains the current user
        get("/chatroom", (res) => {
            if (res.success) {
                // TODO: should filter the chat rooms that contains the current loggedIn user
                const filteredChatRooms = [];
                res.payload.map(chatRoom => {
                    if (chatRoom.users.includes(props.currentUser.username)) {
                        filteredChatRooms.push(chatRoom);
                    }
                    console.log(chatRoom.users);
                    console.log(filteredChatRooms);
                });

                props.setChatRoomList(filteredChatRooms);
                //console.log(res.payload);
            }
        });
    }, []);

    // TODO: should fetch user info from backend API
    return (
        <div className="outer-box">
            <JoinChatRoom chatRoomList={props.chatroomList}
                          setChatRoomList={props.setChatRoomList}
                          currentUser={props.currentUser}
            /><br/>
            <CreateChatRoom chatRoomList={props.chatroomList}
                            setChatRoomList={props.setChatRoomList}
                            currentUser={props.currentUser}
            /><br/>
            <h6>Chats</h6>
            <ChatRooms
                chatRoomList={props.chatroomList}
                currentRoom={props.currentRoom}
                setCurrentRoom={props.setCurrentRoom}
                userList={props.userList}
                setUserList={props.setUserList}
                currentUser={props.currentUser}
                setCurrentUser={props.setCurrentUser}
                messages={props.messages}
                setCurMsgList={props.setCurMsgList}
            />
        </div>
    );
}