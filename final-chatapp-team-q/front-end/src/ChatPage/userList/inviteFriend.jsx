import React, {useState} from "react"
import 'antd/dist/antd.css';
import {Modal, Button, message} from 'antd';
import '../style.css';
import {get, del, post} from "../../util";

export function InviteFriend(props) {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [inputText, setInputText] = useState("");

    const showModal = () => {
        setIsModalVisible(true);
    };

    const handleCancel = () => {
        setIsModalVisible(false);
        setInputText("");
    };

    const error = (msg) => {
        message.error(msg);
    };

    const success = (msg) => {
        message.success(msg);
    };

    const handleSearch = () => {
        let userInfo = {
            chatroom_id: props.chatRoomId,
            Invitee: inputText,
        }
        post("/chatroom/join", userInfo, (res) => {
            if (res.success) {
                success(res.message)
                get("/chatroom/" + props.chatRoomId + "/members", (res) => {
                    let users = [];
                    res.payload.map(user => {
                        users.push(user.username);
                    });
                    props.setUserList(users);
                }, props.currentUser.token);
            } else {
                error(res.message);
            }
        });
    }

    return(
        <div>
            <Button size={"middle"} block={true} onClick={showModal}>
                Invite a friend
            </Button>

            <Modal title="Invite a friend"
                   visible={ isModalVisible }
                   onCancel={handleCancel}
                   footer={[
                       <Button key="search" onClick={ handleSearch }>
                           Invite
                       </Button>,
                   ]}>
                <input type="text"
                       value={inputText}
                       placeholder={"User Name"}
                       onChange={(event => {
                       setInputText(event.target.value);
                })}/>
            </Modal>
        </div>
    );
}