import React, {useEffect, useState} from "react"
import 'antd/dist/antd.css';
import "./style.css"
import {useHistory} from "react-router-dom";
import {ChatRoomList} from "./chatRoomList";
import {UserList} from "./userList/userList";
import ChatBoard from "./ChatRoom/ChatBoard";
import {Button, Modal} from "antd";
import {get} from "../util";
let intervalId;
export function MainView(props) {
    const [ws, setWs] = useState(undefined);
    const [isModalVisible, setIsModalVisible] = useState(false);
    // User joined chat rooms
    const [chatRoomList, setChatRoomList] = useState([]);
    const [userList, setUserList] = useState([]);
    // Current Room that needs to be rendered
    const [currentRoom, setCurrentRoom] = useState(
        {
            roomId: undefined,
            roomName: undefined,
        }
    );
    // Messages in current room that needs to be rendered
    const [messages, setMessages] = useState([]);
    //const [userId, setUserId] = useState(0);

    const [curUser, setCurUser] = useState({
        username: props.loggedInUser.username,
        token: props.loggedInUser.token,
        isAdmin: false,
        banned: false,
    })

    const [curMsgList, setCurMsgList] = useState([]);
    const [notification, setNotification] = useState({on: false, type: "warning", body: ''})

    useEffect(() => {
        createWebSocket();
    }, []);

    const showProfileModal = () => {
        setIsModalVisible(true);
    };

    const handleLogout = () => {
        props.setLoggedInUser({
            username: "",
            token: "",
            age: 0,
            university: "",
            interest: [], // list of strings
            chatRooms: [], //  list of json objects
        })
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    const fetchData = () => {
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

                setChatRoomList(filteredChatRooms);
            }
        });
    }

    const createWebSocket = () => {
        //  + window.location.port
        console.log("connecting");
        //TODO: change ws to wss in production
        let ws = new WebSocket("wss://" + window.location.hostname + ":" + window.location.port + "/chatapp");
        setWs(ws)
        setInterval(()=>{
            ws.send(JSON.stringify({type:"heart_beat", cookie:props.loggedInUser.token}))
        }, 30000)
        ws.onopen = function () {
            console.log("connected");
            ws.send(JSON.stringify({type: "register_session", cookie: props.loggedInUser.token}));
        }
        ws.onerror = function () {
            createWebSocket();
        };
        ws.onclose = function (e) {
            console.log('websocket closing: ' + e.code + ' ' + e.reason + ' ' + e.wasClean)
        }
        ws.onmessage = function (event) {
            let msg = JSON.parse(event.data);
            console.log(msg);
            switch (msg.type) {
                case "notification":
                    setNotification({on: true, type: msg.payload.level, body: msg.payload.message});
                    break;
                case "new_message":
                    messages.push({ roomId: msg.payload.chatroom_id, id: msg.payload.message_id, dest: msg.payload.target, sender: msg.payload.sender, time: new Date().toTimeString().substring(0, 9) + new Date().toDateString(), body: msg.payload.content })
                    let currentMessage = messages.filter((message)=>message.roomId === currentRoom.roomId)
                    setCurMsgList([...currentMessage])
                    setMessages([...messages])
                    break;
                case "edit_message":
                    messages.forEach(ele => {
                        if (ele.id === msg.payload.message_id) {
                            ele.body = msg.payload.content;
                        }
                    });
                    console.log("edited:", messages);
                    setMessages([...messages]);
                    let editMsgList = messages.filter(message => { return message.roomId === currentRoom.roomId });
                    console.log(editMsgList);
                    setCurMsgList([...editMsgList]);
                    break;
                case "delete_message":
                    let delMsgList = messages;
                    delMsgList = delMsgList.filter(message => { return message.id !== msg.payload.message_id; });
                    setMessages(delMsgList);
                    delMsgList = delMsgList.filter(message => { return message.roomId === currentRoom.roomId });
                    setCurMsgList(delMsgList);
                    break;
                case "update_info":
                    get("/chatroom", (res) => {
                        if (res.success) {
                            // TODO: should filter the chat rooms that contains the current loggedIn user
                            setChatRoomList(res.payload);
                            if (currentRoom.roomId !== undefined) {
                                let room = res.payload.filter((room) =>{
                                    return room.chatroom_id === currentRoom.roomId;
                                })[0];
                                if (room) {
                                    setUserList(room.users);
                                    setCurUser({
                                        ...curUser,
                                        isAdmin: room.admins.includes(curUser.username)
                                    })
                                }
                            }
                        }
                    }, curUser.token);
                    break;
            }
        }
        return ws;
    }

    const sendMessage = (msg) => {
        console.log(JSON.parse(msg))
        ws.send(msg);
    }

    return (
        <div className="chat-page">
            <div className="nav-bar">
                <div className="logo-tab">QuickChat</div>
                <div className="logout-tab" onClick={handleLogout}>Logout</div>
                <div className="profile-tab" onClick={showProfileModal}>Profile</div>
            </div>
            <div className="chat-wrapper">
                <div className="chat-room-list">
                    <ChatRoomList chatroomList={chatRoomList}
                                  currentRoom={currentRoom}
                                  setChatRoomList={setChatRoomList}
                                  setCurrentRoom={setCurrentRoom}
                                  userList={userList}
                                  setUserList={setUserList}
                                  currentUser={curUser}
                                  setCurrentUser={setCurUser}
                                  messages={messages}
                                  setCurMsgList={setCurMsgList}
                    />
                </div>
                <div className="chat-main outer-box-chatroom">
                    {currentRoom.roomId !== undefined ? (
                        <ChatBoard user={curUser}
                                   room={currentRoom}
                                   userList={userList}
                                   sendMessage={sendMessage}
                                   messageList={curMsgList}
                                   notification={notification}
                                   setNotification={setNotification}
                                   roomList={chatRoomList}
                                   setRoomList={setChatRoomList}/>
                    ) : null}
                </div>
                <div className="chat-user-list">
                    {/*FIXME: check if this change is valid*/}
                    <UserList chatRoomId={currentRoom.roomId}
                              currentUser={curUser}
                              userList={userList}
                              setUserList={setUserList}
                    />
                </div>
            </div>

            <Modal title="My Profile"
                   visible={isModalVisible}
                   onCancel={handleCancel}
                   footer={[]}>
                <p>Name: {props.loggedInUser.username}</p>
                <p>Age: {props.loggedInUser.age}</p>
                <p>University: {props.loggedInUser.university}</p>
                <p>Interests: {props.loggedInUser.interests}</p>
            </Modal>
        </div>

    );
}