(this["webpackJsonpfront-end"]=this["webpackJsonpfront-end"]||[]).push([[0],{207:function(e,t,s){},208:function(e,t,s){},212:function(e,t,s){},235:function(e,t,s){},236:function(e,t,s){},237:function(e,t,s){},240:function(e,t,s){},243:function(e,t,s){"use strict";s.r(t);var n=s(0),o=s.n(n),r=s(12),a=s.n(r),i=(s(207),s(13)),c=(s(208),s(292)),l=s(23),u=s(36),d=(s(69),s(70),s(142)),h=s.n(d),j="",m=function(e,t,s){var n=arguments.length>3&&void 0!==arguments[3]?arguments[3]:"";""===n&&(console.warn("authToken is '', should pass token"),n=localStorage.getItem("auth")),h.a.ajax({url:j+e,type:"POST",dataType:"json",headers:{Authorization:"Bearer "+n},data:JSON.stringify(t),contentType:"application/json; charset=utf-8",success:function(e){s(e)},error:function(e){s(e)}})},b=function(e,t){var s=arguments.length>2&&void 0!==arguments[2]?arguments[2]:"";""===s&&(console.warn("authToken is '', should pass token"),s=localStorage.getItem("auth")),h.a.ajax({url:j+e,type:"GET",dataType:"json",headers:{Authorization:"Bearer "+s},contentType:"application/json; charset=utf-8",success:function(e){t(e)},error:function(e){t(e)}})},p=function(e,t){var s=arguments.length>2&&void 0!==arguments[2]?arguments[2]:"";""===s&&(console.warn("authToken is '', should pass token"),s=localStorage.getItem("auth")),h.a.ajax({url:j+e,type:"DELETE",dataType:"json",headers:{Authorization:"Bearer "+s},contentType:"application/json; charset=utf-8",success:function(e){t(e)},error:function(e){t(e)}})},g=s(96),f=s(288),O=s(2);function v(e){var t=Object(n.useState)(!1),s=Object(i.a)(t,2),o=s[0],r=s[1],a=Object(n.useState)(),c=Object(i.a)(a,2),l=c[0],d=c[1],h=Object(n.useState)(""),j=Object(i.a)(h,2),p=j[0],v=j[1];return Object(O.jsxs)("div",{children:[Object(O.jsx)(g.a,{size:"middle",block:!0,onClick:function(){r(!0)},children:"Join a Group Chat"}),Object(O.jsxs)(f.a,{title:"Join a group chat",visible:o,onCancel:function(){r(!1),d(""),v("")},footer:[],children:[Object(O.jsx)("input",{type:"number",required:!0,value:l,placeholder:"Room ID (numbers only)",onChange:function(e){d(e.target.value)}}),Object(O.jsx)(g.a,{onClick:function(){void 0===l||""===l?v("Room Id is invalid."):(console.log("Room ID: ",l),b("/chatroom",(function(t){t.success&&t.payload.map((function(t){if(t.chatroom_id===parseInt(l)&&(console.log(t),t.chatroom_id===parseInt(l)))if(t.chatroom_size===t.users.length)v("Chat room is full.");else if(t.is_private)v("Chat room is private.");else{var s={Invitee:"Current User",chatroom_id:t.chatroom_id};m("/chatroom/join",s,(function(t){if(t.success){var s=e.chatRoomList,n=t.payload;s.push(n),e.setChatRoomList(Object(u.a)(s)),console.log(e.chatRoomList),v("You joined a new chat room.")}}))}}))})))},children:"Join"}),Object(O.jsx)("br",{}),Object(O.jsx)("span",{children:p})]})]})}var x=s(293);function y(e){var t=Object(n.useState)(!1),s=Object(i.a)(t,2),o=s[0],r=s[1],a=Object(n.useState)(""),c=Object(i.a)(a,2),l=c[0],d=c[1],h=Object(n.useState)(0),j=Object(i.a)(h,2),b=j[0],p=j[1],v=Object(n.useState)(!0),y=Object(i.a)(v,2),C=y[0],k=y[1],L=Object(n.useState)(""),N=Object(i.a)(L,2),I=N[0],S=N[1],U=function(){r(!1),d(""),p(0),k(!0)};return Object(O.jsxs)("div",{children:[Object(O.jsx)(g.a,{size:"middle",block:!0,onClick:function(){r(!0)},children:"Create a Group Chat"}),Object(O.jsxs)(f.a,{title:"Create a group chat",visible:o,onCancel:function(){U()},footer:[Object(O.jsx)(g.a,{onClick:function(){0===b||""===l||void 0===b||void 0===l?S("Input errors."):(S(""),m("/chatroom/create",{size:b,chatroom_name:l,is_private:!C},(function(t){if(t.success){var s=e.chatRoomList,n=t.payload;s.push(n),e.setChatRoomList(Object(u.a)(s)),console.log(e.chatRoomList)}}),e.currentUser.token),U())},children:"Create"},"create")],children:[Object(O.jsx)("input",{type:"text",value:l,placeholder:"Group Name",required:!0,onChange:function(e){d(e.target.value)}}),Object(O.jsx)("input",{type:"number",min:2,max:50,value:b,placeholder:"Room Size",required:!0,onChange:function(e){p(e.target.value)}}),Object(O.jsx)("br",{}),Object(O.jsx)(x.a,{onChange:function(e){k(e.target.checked),console.log(C)},children:"Public chat room"}),Object(O.jsx)("br",{}),Object(O.jsx)("span",{style:{color:"red"},children:I})]})]})}var C=s.p+"static/media/default-group-chat.28ff0cf6.jpg",k=s(179),L=function(e){k.b.error(e)};function N(e){return Object(O.jsx)("div",{children:e.chatRoomList.map((function(t){return Object(O.jsxs)("div",{className:"outer-box",onClick:function(){return function(t,s){console.log("setting",t,s),console.log(e);var n=e.currentRoom;n.roomId=t,n.roomName=s,e.setCurrentRoom(Object(l.a)({},n)),b("/chatroom",(function(t){if(t.success){var s=t.payload.filter((function(e){return e.chatroom_id===n.roomId}))[0];if(s){e.setUserList(s.users),e.setCurrentUser(Object(l.a)(Object(l.a)({},e.currentUser),{},{isAdmin:s.admins.includes(e.currentUser.username)}));var o=e.messages.filter((function(e){return e.roomId===n.roomId}));e.setCurMsgList(Object(u.a)(o))}}else L(t.message)}),e.currentUser.token)}(t.chatroom_id,t.chatroom_name)},children:[Object(O.jsx)("img",{className:"groupImg",src:C,alt:"",style:{width:"50px"}}),Object(O.jsx)("b",{children:t.chatroom_name}),Object(O.jsxs)("p",{children:["Room ID: ",t.chatroom_id]})]},t.chatroom_id)}))})}function I(e){return Object(n.useEffect)((function(){b("/chatroom",(function(t){if(t.success){var s=[];t.payload.map((function(t){t.users.includes(e.currentUser.username)&&s.push(t),console.log(t.users),console.log(s)})),e.setChatRoomList(s)}}))}),[]),Object(O.jsxs)("div",{className:"outer-box",children:[Object(O.jsx)(v,{chatRoomList:e.chatroomList,setChatRoomList:e.setChatRoomList,currentUser:e.currentUser}),Object(O.jsx)("br",{}),Object(O.jsx)(y,{chatRoomList:e.chatroomList,setChatRoomList:e.setChatRoomList,currentUser:e.currentUser}),Object(O.jsx)("br",{}),Object(O.jsx)("h6",{children:"Chats"}),Object(O.jsx)(N,{chatRoomList:e.chatroomList,currentRoom:e.currentRoom,setCurrentRoom:e.setCurrentRoom,userList:e.userList,setUserList:e.setUserList,currentUser:e.currentUser,setCurrentUser:e.setCurrentUser,messages:e.messages,setCurMsgList:e.setCurMsgList})]})}s(212);function S(e){var t=Object(n.useState)(!1),s=Object(i.a)(t,2),o=s[0],r=s[1],a=Object(n.useState)(""),c=Object(i.a)(a,2),l=c[0],u=c[1];return Object(O.jsxs)("div",{children:[Object(O.jsx)(g.a,{size:"middle",block:!0,onClick:function(){r(!0)},children:"Invite a friend"}),Object(O.jsx)(f.a,{title:"Invite a friend",visible:o,onCancel:function(){r(!1),u("")},footer:[Object(O.jsx)(g.a,{onClick:function(){var t={chatroom_id:e.chatRoomId,Invitee:l};m("/chatroom/join",t,(function(t){var s;t.success?(s=t.message,k.b.success(s),b("/chatroom/"+e.chatRoomId+"/members",(function(t){var s=[];t.payload.map((function(e){s.push(e.username)})),e.setUserList(s)}),e.currentUser.token)):function(e){k.b.error(e)}(t.message)}))},children:"Invite"},"search")],children:Object(O.jsx)("input",{type:"text",value:l,placeholder:"User Name",onChange:function(e){u(e.target.value)}})})]})}function U(e){return Object(O.jsxs)("div",{className:"outer-box",children:[Object(O.jsx)("link",{href:"https://fonts.googleapis.com/icon?family=Material+Icons",rel:"stylesheet"}),Object(O.jsx)(S,{chatRoomId:e.chatRoomId,userList:e.userList,setUserList:e.setUserList,currentUser:e.currentUser}),Object(O.jsx)(R,{chatRoomId:e.chatRoomId,userList:e.userList,setUserList:e.setUserList,currentUser:e.currentUser})]})}function R(e){var t=function(t){console.info(t),p("/chatroom/"+e.chatRoomId+"/member/"+t,(function(t){var s;t.success?b("/chatroom/"+e.chatRoomId+"/members",(function(t){var s=[];t.payload.map((function(e){s.push(e.username)})),e.setUserList(s)}),e.currentUser.token):(s=t.message,k.b.error(s))}),e.currentUser.token)};return Object(O.jsx)("div",{children:e.userList.map((function(s,n){return Object(O.jsx)("div",{className:"outer-box",style:{padding:30,position:"relative"},children:Object(O.jsxs)("div",{children:[Object(O.jsx)("i",{className:"material-icons prefix",style:{position:"absolute",left:10},children:"account_circle"}),Object(O.jsx)("b",{style:{position:"absolute",left:40},children:s}),e.currentUser.isAdmin&&s!==e.currentUser.username?Object(O.jsx)(g.a,{danger:!0,style:{position:"absolute",right:10,bottom:10},onClick:function(){return t(s)},children:"block"}):null]})},n)}))})}var w=s(48),_=s(49),M=s(51),J=s(50),T=(s(213),s(214),s(235),s(236),s(295)),A=function(e){Object(M.a)(s,e);var t=Object(J.a)(s);function s(e){return Object(w.a)(this,s),t.call(this,e)}return Object(_.a)(s,[{key:"render",value:function(){return Object(O.jsxs)("div",{className:"chat-header",children:[Object(O.jsx)("div",{className:"chat-header-left",children:Object(O.jsxs)("h3",{children:[Object(O.jsx)("span",{className:"chat-header-room-name",children:this.props.room.name}),Object(O.jsxs)("span",{className:"chat-header-room-id",children:["Room ID: ",this.props.room.roomId]})]})}),Object(O.jsx)("div",{className:"chat-header-right",children:Object(O.jsx)(T.a,{variant:"outline-light",className:"chat-header-btn-leave",children:"Leave"})})]})}}]),s}(o.a.Component),E=(s(237),s(286)),D=s(291),F=s(174),P=s.n(F),z=s(173),B=s.n(z),G=s(175),Q=s.n(G),V=function(e){Object(M.a)(s,e);var t=Object(J.a)(s);function s(e){var n;return Object(w.a)(this,s),(n=t.call(this,e)).state={editing:!1,editedMsg:""},n}return Object(_.a)(s,[{key:"saveEditedMsg",value:function(e){this.setState({editedMsg:e.target.value})}},{key:"render",value:function(){var e=this;return Object(O.jsxs)("div",{className:"message",children:[Object(O.jsxs)("div",{className:"message-info",children:[Object(O.jsxs)("h4",{children:[this.props.message.sender,Object(O.jsx)("span",{className:"message-timestamp",children:this.props.message.time})]}),this.state.editing?Object(O.jsx)(E.a,{variant:"outlined",size:"small",defaultValue:this.props.message.body,onChange:this.saveEditedMsg.bind(this)}):Object(O.jsx)("p",{className:"message-body",children:this.props.message.body})]}),this.props.authority?Object(O.jsxs)("div",{className:"message-icons",children:[this.state.editing?Object(O.jsx)(D.a,{"aria-label":"save",color:"inherit",onClick:function(){e.setState({body:e.state.editedMsg,editing:!e.state.editing}),e.props.sendEditReq(e.props.index,e.state.editedMsg)},children:Object(O.jsx)(B.a,{})}):Object(O.jsx)(D.a,{"aria-label":"edit",color:"inherit",onClick:function(){e.setState({editing:!e.state.editing})},children:Object(O.jsx)(P.a,{})}),Object(O.jsx)(D.a,{"aria-label":"delete",color:"inherit",onClick:this.props.handleDelete.bind(this),children:Object(O.jsx)(Q.a,{})})]}):null]})}}]),s}(o.a.Component),q=s(296),H=s(290),W=s(176),X=s.n(W),Y=s(182);function Z(e){return Object(O.jsx)(H.a,Object(l.a)({elevation:6,variant:"filled"},e))}var K=function(e){Object(M.a)(s,e);var t=Object(J.a)(s);function s(e){var n;return Object(w.a)(this,s),(n=t.call(this,e)).scrollToBottom=function(){n.messagesEnd.scrollIntoView({behavior:"smooth"})},n.state={msg:"",msgDest:"all"},n.props=e,n.messageInput=o.a.createRef(),n}return Object(_.a)(s,[{key:"sendMessage",value:function(){this.setState({msg:""}),this.props.sendMessage(JSON.stringify({type:"send_message",cookie:this.props.user.token,payload:{chatroom_id:this.props.room.roomId,target:this.state.msgDest,content:this.state.msg}}))}},{key:"deleteMessage",value:function(e){this.props.sendMessage(JSON.stringify({type:"delete_message",cookie:this.props.user.token,payload:{message_id:this.props.messageList[e].id}}))}},{key:"editMessage",value:function(e,t){console.log("edited: new message",e,t),this.props.sendMessage(JSON.stringify({type:"edit_message",cookie:this.props.user.token,payload:{content:t,message_id:this.props.messageList[e].id}}))}},{key:"leaveRoom",value:function(){var e=this;p("/chatroom/"+this.props.room.id+"/member/"+this.props.user.username,{cookie:localStorage.getItem("auth")},(function(t){t.success&&e.props.setRoomList(e.props.roomList.filter((function(t){return t.chatroomId!==e.props.room.roomId})))}))}},{key:"componentDidMount",value:function(){this.scrollToBottom()}},{key:"componentDidUpdate",value:function(){this.scrollToBottom()}},{key:"render",value:function(){var e=this;return Object(O.jsxs)("div",{className:"chat-board",children:[Object(O.jsx)(A,{room:this.props.room}),Object(O.jsx)("div",{className:"chat-messages",children:Object(O.jsxs)("div",{className:"chat-messages-list",children:[this.props.messageList.map((function(t,s){return"all"===t.dest||t.sender===e.props.user.username||t.dest===e.props.user.username?Object(O.jsx)(V,{authority:e.props.user.isAdmin||e.props.user.username===t.sender,message:t,handleDelete:e.deleteMessage.bind(e,s),sendEditReq:function(t,s){return e.editMessage(t,s)},index:s},s):null})),Object(O.jsx)("div",{style:{float:"left",clear:"both"},ref:function(t){e.messagesEnd=t}})]})}),Object(O.jsx)(q.a,{open:this.props.notification.on,autoHideDuration:6e3,onClose:function(t){return e.props.setNotification({notification:{on:!1,body:""}})},children:Object(O.jsx)(Z,{onClose:function(t){return e.props.setNotification({notification:{on:!1,body:""}})},severity:this.props.notification.type,children:this.props.notification.body})}),Object(O.jsxs)("div",{className:"chat-input",children:[Object(O.jsx)(X.a,{className:"chat-input-item",fontSize:"large"}),Object(O.jsxs)("select",{className:"chat-input-item",defaultValue:"all",onChange:function(t){return e.setState({msgDest:t.target.value})},children:[Object(O.jsx)("option",{value:"all",children:"All"}),this.props.userList.map((function(t,s){return e.props.user.username!==t?Object(O.jsx)("option",{value:t,children:t},s):null}))]}),Object(O.jsx)(Y.a,{value:this.state.msg,onChange:function(t){return e.setState({msg:t})},cleanOnEnter:!0,onEnter:this.sendMessage.bind(this),placeholder:"Enter a message"}),Object(O.jsx)(g.a,{className:"chat-input-item",variant:"outline-light",onClick:this.sendMessage.bind(this),disabled:this.props.user.banned,children:"Send"})]})]})}}]),s}(o.a.Component);function $(e){var t=Object(n.useState)(void 0),s=Object(i.a)(t,2),o=s[0],r=s[1],a=Object(n.useState)(!1),c=Object(i.a)(a,2),d=c[0],h=c[1],j=Object(n.useState)([]),m=Object(i.a)(j,2),p=m[0],g=m[1],v=Object(n.useState)([]),x=Object(i.a)(v,2),y=x[0],C=x[1],k=Object(n.useState)({roomId:void 0,roomName:void 0}),L=Object(i.a)(k,2),N=L[0],S=L[1],R=Object(n.useState)([]),w=Object(i.a)(R,2),_=w[0],M=w[1],J=Object(n.useState)({username:e.loggedInUser.username,token:e.loggedInUser.token,isAdmin:!1,banned:!1}),T=Object(i.a)(J,2),A=T[0],E=T[1],D=Object(n.useState)([]),F=Object(i.a)(D,2),P=F[0],z=F[1],B=Object(n.useState)({on:!1,type:"warning",body:""}),G=Object(i.a)(B,2),Q=G[0],V=G[1];Object(n.useEffect)((function(){q()}),[]);var q=function t(){console.log("connecting");var s=new WebSocket("wss://"+window.location.hostname+":"+window.location.port+"/chatapp");return r(s),setInterval((function(){s.send(JSON.stringify({type:"heart_beat",cookie:e.loggedInUser.token}))}),3e4),s.onopen=function(){console.log("connected"),s.send(JSON.stringify({type:"register_session",cookie:e.loggedInUser.token}))},s.onerror=function(){t()},s.onclose=function(e){console.log("websocket closing: "+e.code+" "+e.reason+" "+e.wasClean)},s.onmessage=function(e){var t=JSON.parse(e.data);switch(console.log(t),t.type){case"notification":V({on:!0,type:t.payload.level,body:t.payload.message});break;case"new_message":_.push({roomId:t.payload.chatroom_id,id:t.payload.message_id,dest:t.payload.target,sender:t.payload.sender,time:(new Date).toTimeString().substring(0,9)+(new Date).toDateString(),body:t.payload.content});var s=_.filter((function(e){return e.roomId===N.roomId}));z(Object(u.a)(s)),M(Object(u.a)(_));break;case"edit_message":_.forEach((function(e){e.id===t.payload.message_id&&(e.body=t.payload.content)})),console.log("edited:",_),M(Object(u.a)(_));var n=_.filter((function(e){return e.roomId===N.roomId}));console.log(n),z(Object(u.a)(n));break;case"delete_message":var o=_;o=o.filter((function(e){return e.id!==t.payload.message_id})),M(o),o=o.filter((function(e){return e.roomId===N.roomId})),z(o);break;case"update_info":b("/chatroom",(function(e){if(e.success&&(g(e.payload),void 0!==N.roomId)){var t=e.payload.filter((function(e){return e.chatroom_id===N.roomId}))[0];t&&(C(t.users),E(Object(l.a)(Object(l.a)({},A),{},{isAdmin:t.admins.includes(A.username)})))}}),A.token)}},s};return Object(O.jsxs)("div",{className:"chat-page",children:[Object(O.jsxs)("div",{className:"nav-bar",children:[Object(O.jsx)("div",{className:"logo-tab",children:"QuickChat"}),Object(O.jsx)("div",{className:"logout-tab",onClick:function(){e.setLoggedInUser({username:"",token:"",age:0,university:"",interest:[],chatRooms:[]})},children:"Logout"}),Object(O.jsx)("div",{className:"profile-tab",onClick:function(){h(!0)},children:"Profile"})]}),Object(O.jsxs)("div",{className:"chat-wrapper",children:[Object(O.jsx)("div",{className:"chat-room-list",children:Object(O.jsx)(I,{chatroomList:p,currentRoom:N,setChatRoomList:g,setCurrentRoom:S,userList:y,setUserList:C,currentUser:A,setCurrentUser:E,messages:_,setCurMsgList:z})}),Object(O.jsx)("div",{className:"chat-main outer-box-chatroom",children:void 0!==N.roomId?Object(O.jsx)(K,{user:A,room:N,userList:y,sendMessage:function(e){console.log(JSON.parse(e)),o.send(e)},messageList:P,notification:Q,setNotification:V,roomList:p,setRoomList:g}):null}),Object(O.jsx)("div",{className:"chat-user-list",children:Object(O.jsx)(U,{chatRoomId:N.roomId,currentUser:A,userList:y,setUserList:C})})]}),Object(O.jsxs)(f.a,{title:"My Profile",visible:d,onCancel:function(){h(!1)},footer:[],children:[Object(O.jsxs)("p",{children:["Name: ",e.loggedInUser.username]}),Object(O.jsxs)("p",{children:["Age: ",e.loggedInUser.age]}),Object(O.jsxs)("p",{children:["University: ",e.loggedInUser.university]}),Object(O.jsxs)("p",{children:["Interests: ",e.loggedInUser.interests]})]})]})}var ee=s(101),te=(s(240),s(289)),se=s(287),ne=function(e){Object(M.a)(s,e);var t=Object(J.a)(s);function s(e){var n;return Object(w.a)(this,s),n=t.call(this,e),localStorage.setItem("auth","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJxdWlja2NoYXQiLCJ1c2VybmFtZSI6InRlc3QifQ.ouJvL4eRZXfG9V_6PvGWby7ho4sN97hRrQ4hTNEJTig"),n.state={isLogginActive:!0},n.container=o.a.createRef(),n}return Object(_.a)(s,[{key:"componentDidMount",value:function(){this.rightSide.classList.add("right")}},{key:"changeState",value:function(){this.state.isLogginActive?(this.rightSide.classList.remove("right"),this.rightSide.classList.add("left")):(this.rightSide.classList.remove("left"),this.rightSide.classList.add("right")),this.setState((function(e){return{isLogginActive:!e.isLogginActive}}))}},{key:"render",value:function(){var e=this,t=this.state.isLogginActive,s=t?"Register":"Login",n=t?"login":"register";return Object(O.jsx)("div",{children:Object(O.jsx)("div",{className:"App",children:Object(O.jsxs)("div",{className:"login",children:[Object(O.jsxs)("div",{className:"container",ref:function(t){return e.container=t},children:[t&&Object(O.jsx)(re,{history:this.props.history,state:this.state,containerRef:function(t){return e.current=t},setLoggedInUser:this.props.setLoggedInUser,loggedInUser:this.props.loggedInUser}),!t&&Object(O.jsx)(ae,{history:this.props.history,containerRef:function(t){return e.current=t},addUser:this.addUser})]}),Object(O.jsx)(oe,{current:s,currentActive:n,containerRef:function(t){return e.rightSide=t},onClick:this.changeState.bind(this)})]})})})}}]),s}(o.a.Component),oe=function(e){return Object(O.jsx)("div",{className:"right-side",ref:e.containerRef,onClick:e.onClick,children:Object(O.jsx)("div",{className:"inner-container",children:Object(O.jsx)("div",{className:"text",children:e.current})})})},re=function(e){Object(M.a)(s,e);var t=Object(J.a)(s);function s(e){var n;return Object(w.a)(this,s),(n=t.call(this,e)).state={user:{},password:"",username:"",isLoggedIn:!1},n.loginFunc=n.loginFunc.bind(Object(ee.a)(n)),n}return Object(_.a)(s,[{key:"loginFunc",value:function(){var e=this,t={username:this.state.username,password:this.state.password};m("/user/login",t,(function(s){if(s.success){console.log(e.props),e.props.setLoggedInUser({username:t.username,token:s.payload.cookie});var n=s.payload.cookie;b("/user/"+t.username,(function(s){s.success?e.props.setLoggedInUser({username:t.username,token:n,age:s.payload.age,school:s.payload.school,interests:s.payload.interests.join(", ")}):(console.log(s),L(s.responseJSON.message))}),e.props.loggedInUser.token)}else L(s.responseJSON.message)}))}},{key:"render",value:function(){var e,t=this;return!0===this.state.isLoggedIn&&(e=Object(O.jsxs)(te.a,{icon:!0,error:!0,children:[Object(O.jsx)(se.a,{name:"warning"}),Object(O.jsxs)(te.a.Content,{children:[Object(O.jsx)(te.a.Header,{children:"Username Not Found"}),"The username you supplied was not found"]})]})),Object(O.jsxs)("div",{className:"base-container",ref:this.props.containerRef,children:[Object(O.jsx)("div",{className:"header",children:"Login"}),Object(O.jsx)("div",{className:"content",children:Object(O.jsxs)("div",{className:"form",children:[Object(O.jsxs)("div",{className:"form-group",children:[Object(O.jsx)("label",{htmlFor:"username",children:"Username"}),Object(O.jsx)("input",{type:"text",name:"username",id:"username",placeholder:"username",onChange:function(e){t.setState({username:e.target.value})}})]}),Object(O.jsxs)("div",{className:"form-group",children:[Object(O.jsx)("label",{htmlFor:"password",children:"Password"}),Object(O.jsx)("input",{type:"password",name:"password",id:"password",placeholder:"password",onChange:function(e){t.setState({password:e.target.value})}})]})]})}),Object(O.jsx)("div",{className:"footer",children:Object(O.jsx)("button",{type:"button",className:"btn",onClick:this.loginFunc,children:"Login"})}),e]})}}]),s}(o.a.Component),ae=function(e){Object(M.a)(s,e);var t=Object(J.a)(s);function s(e){var n;return Object(w.a)(this,s),(n=t.call(this,e)).state={username:"",interest:"",school:"",age:"",password:"",confirmPassword:"",errorMsg:"",error:!1,success:!1},n.register=n.register.bind(Object(ee.a)(n)),n}return Object(_.a)(s,[{key:"register",value:function(){var e="",t=this.state.password,s=this.state.confirmPassword;if(""===this.state.username)return e+="user name cant be null \n",void this.setState({errorMsg:e,error:!0,success:!1});if(t!==s)return e+="password and password confirmation must be same\n",void this.setState({errorMsg:e,error:!0,success:!1});var n={id:11,username:this.state.username,password:this.state.password,age:this.state.age,school:this.state.school,interest:[this.state.interest]};m("/user/create",n,(function(e){e.success?function(e){k.b.success(e)}("User has ben register successfully!"):(console.log(e),L(e.responseJSON.message))}))}},{key:"render",value:function(){var e,t,s=this;return!0===this.state.error&&(e=ce(this.state.errorMsg)),!0===this.state.success&&(t=ie()),Object(O.jsxs)("div",{className:"base-container",ref:this.props.containerRef,children:[Object(O.jsx)("div",{className:"header",children:"Register"}),Object(O.jsx)("div",{className:"content",children:Object(O.jsx)("form",{children:Object(O.jsxs)("div",{className:"form",children:[Object(O.jsxs)("div",{className:"form-group",children:[Object(O.jsx)("label",{htmlFor:"username",children:"Username"}),Object(O.jsx)("input",{type:"text",name:"username",placeholder:"username",onChange:function(e){s.setState({username:e.target.value})}})]}),Object(O.jsxs)("div",{className:"form-group",children:[Object(O.jsx)("label",{htmlFor:"email",children:"Interest"}),Object(O.jsx)("input",{type:"email",name:"email",placeholder:"interest",onChange:function(e){s.setState({interest:e.target.value})}})]}),Object(O.jsxs)("div",{className:"form-group",children:[Object(O.jsx)("label",{htmlFor:"school",children:"School"}),Object(O.jsx)("input",{type:"text",name:"school",placeholder:"school",onChange:function(e){s.setState({school:e.target.value})}})]}),Object(O.jsxs)("div",{className:"form-group",children:[Object(O.jsx)("label",{htmlFor:"age",children:"Age"}),Object(O.jsx)("input",{type:"text",name:"school",placeholder:"age",onChange:function(e){s.setState({age:e.target.value})}})]}),Object(O.jsxs)("div",{className:"form-group",children:[Object(O.jsx)("label",{htmlFor:"password",children:"Password"}),Object(O.jsx)("input",{type:"password",name:"password",placeholder:"password",onChange:function(e){s.setState({password:e.target.value})}})]}),Object(O.jsxs)("div",{className:"form-group",children:[Object(O.jsx)("label",{htmlFor:"password",children:"Confirmed Password"}),Object(O.jsx)("input",{type:"password",name:"confirmPassword",placeholder:"confirmedPassword",onChange:function(e){s.setState({confirmPassword:e.target.value})}})]})]})})}),Object(O.jsxs)("div",{className:"footer",children:[Object(O.jsx)("button",{type:"button",className:"btn",onClick:this.register,children:"Register"}),e,t]})]})}}]),s}(o.a.Component),ie=function(){return Object(O.jsx)(te.a,{icon:!0,success:!0,children:Object(O.jsxs)(te.a.Content,{children:[Object(O.jsx)(te.a.Header,{children:"Register Success"}),"Login with username and password!"]})})},ce=function(e){return Object(O.jsxs)(te.a,{icon:!0,error:!0,children:[Object(O.jsx)(se.a,{name:"warning"}),Object(O.jsxs)(te.a.Content,{children:[Object(O.jsx)(te.a.Header,{children:"Register Error"}),e]})]})};var le=function(){var e=Object(n.useState)({username:"",token:"",age:0,university:"",interest:[],chatRooms:[]}),t=Object(i.a)(e,2),s=t[0],o=t[1],r=Object(n.useState)(Object(c.a)()),a=Object(i.a)(r,2),l=a[0],u=a[1];return Object(O.jsx)("div",{className:"App",children:void 0===s.token||""===s.token?Object(O.jsx)(ne,{loggedInUser:s,setLoggedInUser:o,history:l,setHistory:u}):Object(O.jsx)($,{loggedInUser:s,setLoggedInUser:o})})},ue=function(e){e&&e instanceof Function&&s.e(3).then(s.bind(null,297)).then((function(t){var s=t.getCLS,n=t.getFID,o=t.getFCP,r=t.getLCP,a=t.getTTFB;s(e),n(e),o(e),r(e),a(e)}))};a.a.render(Object(O.jsx)(o.a.StrictMode,{children:Object(O.jsx)(le,{})}),document.getElementById("root")),ue()},70:function(e,t,s){}},[[243,1,2]]]);
//# sourceMappingURL=main.05eece96.chunk.js.map