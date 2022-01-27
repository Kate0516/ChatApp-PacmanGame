import React, {useEffect, useState} from "react"
import 'antd/dist/antd.css';
import './userList.css';
import '../style.css';
import {InviteFriend} from "./inviteFriend";
import {get, del} from "../../util";
import { message,Button } from 'antd';

export function UserList(props) {
    return (
        <div className="outer-box">
            <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet"/>
            <InviteFriend
                chatRoomId={props.chatRoomId}
                userList={props.userList}
                setUserList={props.setUserList}
                currentUser={props.currentUser}
            />
            <User
                chatRoomId={props.chatRoomId}
                userList={props.userList}
                setUserList={props.setUserList}
                currentUser={props.currentUser}
            />
        </div>
    );
}

export function User(props) {
    const error = (errorMessage) => {
        message.error(errorMessage);
    };
    // fetch data from backend

    const handleBlock = (username) => {
        console.info(username)
        del("/chatroom/" + props.chatRoomId + "/member/" + username, (res) => {
            if (res.success) {
                // modify user list in current chatroom
                get("/chatroom/" + props.chatRoomId + "/members", (res) => {
                    let users = [];
                    res.payload.map(user => {
                        users.push(user.username);
                    });
                    props.setUserList(users);
                }, props.currentUser.token);
            }else{
                error(res.message);
            }
        }, props.currentUser.token);
    }

    return (
        <div>
            {props.userList.map((user, index) => {
                return (
                    <div className="outer-box" style={{padding: 30, position:"relative"}} key={index}>
                        <div >
                            <i className="material-icons prefix" style={{position:"absolute", left:10}}>account_circle</i>
                            <b style={{position:"absolute", left:40}}>{user}</b>
                            {
                                props.currentUser.isAdmin && user !== props.currentUser.username ?
                                    (<Button danger style={{position:"absolute", right:10, bottom : 10}}
                                             onClick={() => handleBlock(user)}>
                                        block
                                    </Button>) : null

                            }

                        </div>
                    </div>
                );
            })}
        </div>
    );

}