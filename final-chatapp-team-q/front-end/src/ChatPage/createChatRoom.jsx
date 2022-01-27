import React, { useState } from "react"
import 'antd/dist/antd.css';
import "./style.css"
import { post } from "../util/index";
import {Modal, Button, Checkbox} from 'antd';

export function CreateChatRoom(props) {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [groupName, setGroupName] = useState("");
    const [roomSize, setRoomSize] = useState(0);
    const [isPublic, setIsPublic] = useState(true);
    const [errorMessage, setErrormessage] = useState("");

    const showModal = () => {
        setIsModalVisible(true);
    };

    const clearInputFields = () => {
        setIsModalVisible(false);
        setGroupName("");
        setRoomSize(0);
        setIsPublic(true);
    }

    const handleCancel = () => {
        clearInputFields();
    };

    const handleCreate = () => {
        if (roomSize === 0 || groupName === "" || roomSize === undefined || groupName === undefined) {
            setErrormessage("Input errors.");
        } else {
            setErrormessage("");
            let roomInfo = {
                size: roomSize,
                chatroom_name: groupName,
                is_private: !isPublic,
            }
            post("/chatroom/create", roomInfo, (res) => {
                if (res.success) {
                    let newRoomList = props.chatRoomList;
                    let newRoom = res.payload;
                    newRoomList.push(newRoom);
                    // should add the new room to chat room list
                    props.setChatRoomList([...newRoomList]);
                    console.log(props.chatRoomList);
                }
            }, props.currentUser.token);
            clearInputFields();
        }
    };

    const handlePublic = (e) => {
        setIsPublic(e.target.checked);
        console.log(isPublic);
    }

    return (
        <div>
            <Button size={"middle"} block={true} onClick={showModal}>
                Create a Group Chat
            </Button>

            <Modal title="Create a group chat"
                   visible={isModalVisible}
                   onCancel={handleCancel}
                   footer={[
                       <Button key="create" onClick={handleCreate}>
                           Create
                       </Button>,
                   ]}>
                <input type="text"
                       value={groupName}
                       placeholder={"Group Name"}
                       required={true}
                       onChange={(event => {
                           setGroupName(event.target.value);
                       })}/>
                <input type="number"
                       min={2}
                       max={50}
                       value={roomSize}
                       placeholder={"Room Size"}
                       required={true}
                       onChange={(event => {
                           setRoomSize(event.target.value);
                       })}/><br/>
                <Checkbox onChange={handlePublic}>Public chat room</Checkbox><br/>
                <span style={{color: "red"}}>{errorMessage}</span>
            </Modal>
        </div>
    );
}