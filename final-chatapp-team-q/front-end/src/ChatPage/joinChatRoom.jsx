import React, { useState } from "react"
import 'antd/dist/antd.css';
import "./style.css"
import { Modal, Button } from 'antd';
import {get, post} from "../util/index";

export function JoinChatRoom(props) {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [inputText, setInputText] = useState();
    const [resultText, setResultText] = useState("");

    const showModal = () => {
        setIsModalVisible(true);
    };

    const handleCancel = () => {
        setIsModalVisible(false);
        setInputText("");
        setResultText("");
    };

    const handleJoin = () => {
        if (inputText === undefined || inputText === "") {
            setResultText("Room Id is invalid.");
        } else {
            console.log("Room ID: ", inputText);
            // filter search result, should fetch all rooms from /chatroom then filter
            get("/chatroom", (res) => {
                // filter search result, should fetch all rooms from /chatroom then filter
                if (res.success) {
                    const chatRooms = res.payload;
                    chatRooms.map(chatRoom => {
                        if (chatRoom.chatroom_id === parseInt(inputText)) {
                            console.log(chatRoom);
                            // if there is a matching result
                            if (chatRoom.chatroom_id === parseInt(inputText)) {
                                if (chatRoom.chatroom_size === chatRoom.users.length) {
                                    // Case 1: room has already full
                                    setResultText("Chat room is full.");
                                } else if (chatRoom.is_private) {
                                    // Case 2: room is private
                                    setResultText("Chat room is private.");
                                } else {
                                    // Case 4: user is able to join
                                    const joinInfo = {
                                        // FIXME: should be current user's id
                                        Invitee: "Current User",
                                        chatroom_id: chatRoom.chatroom_id,
                                    }
                                    post("/chatroom/join", joinInfo, (res) => {
                                        if (res.success) {
                                            let newRoomList = props.chatRoomList;
                                            let newRoom = res.payload;
                                            newRoomList.push(newRoom);
                                            // should add the new room to chat room list
                                            props.setChatRoomList([...newRoomList]);
                                            console.log(props.chatRoomList);
                                            setResultText("You joined a new chat room.");
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            });
        }
    }

    return(
        <div>
            <Button size={"middle"} block={true} onClick={showModal}>
                Join a Group Chat
            </Button>

            <Modal title="Join a group chat"
                   visible={ isModalVisible }
                   onCancel={ handleCancel }
                   footer={[]}>
                <input type="number"
                       required={true}
                       value={inputText}
                       placeholder={"Room ID (numbers only)"}
                       onChange={(event => {
                            setInputText(event.target.value);
                       })}/>
                <Button onClick={handleJoin}>Join</Button><br/>
                <span>{resultText}</span>
            </Modal>
        </div>
    );
}