package edu.rice.comp504.model.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import edu.rice.comp504.model.authenticate.Authenticator;
import edu.rice.comp504.model.message.Message;

import edu.rice.comp504.model.authenticate.Authenticator;
import edu.rice.comp504.model.chatroom.IChatRoom;
import edu.rice.comp504.model.messagestore.MessageStore;
import edu.rice.comp504.model.chatroom.ChatroomStore;
import edu.rice.comp504.model.chatroom.IChatroomStore;
import edu.rice.comp504.model.command.MessageCommandFactory;
import edu.rice.comp504.model.message.IMessage;
import edu.rice.comp504.model.user.IUser;
import edu.rice.comp504.model.user.User;
import edu.rice.comp504.model.userstorage.UserDB;
import edu.rice.comp504.util.GsonExcludeStrategy;
import edu.rice.comp504.util.JsonResponse;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ChatAppStore implements IChatAppStore {
    private final PropertyChangeSupport pcs;
    private final IChatroomStore chatroomStore = ChatroomStore.getInstance();
    private final UserDB userDB = UserDB.getInstance();
    private final MessageStore messageStore = MessageStore.getInstance();
    private final Gson gson = new GsonBuilder().setExclusionStrategies(new GsonExcludeStrategy()).create();

    private ChatAppStore() {
        this.pcs = new PropertyChangeSupport(this);
    }

    private static final ChatAppStore instance = new ChatAppStore();

    public static ChatAppStore getInstance() {
        return instance;
    }


    /**
     * Use case 1: create account
     * Create an account.
     * To implement this use case, we will first check if the username exists in the current database by calling
     * <code>userDB.userExists</code> method. Then, if the username does not exist, we will create a new user by
     * creating a User instance. Then, we will pass the user info to <code>IAuthenticator.getJwtForUser</code>
     * and generate a jwt for the user. Then we will return the jwt for the user as a cookie.
     *
     * @param username  username for the user
     * @param password  password for the user
     * @param school    school info of the user
     * @param interests interests of the user
     * @return json response
     */
    public JsonResponse registerNewUser(String username, String password, String school, int age, String... interests) {
        if (userDB.userExists(username)) {
            return new JsonResponse(false, null, "user already exists", 400);
        }

        IUser user = userDB.addNewUser(new User(username, password, school, age, interests));
        this.pcs.addPropertyChangeListener(user);
        return new JsonResponse(true, user, "");
    }
    /**
     * Use case 2: present user info
     * We will first check if initiator is authenticated/logged in.
     * Then, lookup in userDB to see if a user with checkedUsername exists.
     * If user is found, fetch and return user's info
     *
     * @param initiator     the user who send this request
     * @param checkUsername the user whose info will be returned
     * @return json response
     */
    public JsonResponse getUser(IUser initiator, String checkUsername) {
        IUser user = userDB.getUserInfo(checkUsername);
        if (user == null) {
            return new JsonResponse(false, null, "user not found", 404);
        }
        JsonObject resp = gson.toJsonTree(user).getAsJsonObject();
        ArrayList<IChatRoom> chatRooms = new ArrayList<>();
        IChatRoom[] iChatRooms = ChatroomStore.getInstance().getAllChatRooms();
        for (IChatRoom iChatRoom : iChatRooms) {
            if (iChatRoom.isUserInChatroom(checkUsername)) {
                chatRooms.add(iChatRoom);
            }
        }
        resp.add("chatrooms", gson.toJsonTree(chatRooms));

        return new JsonResponse(true, resp, "");
    }

    /**
     * Use case 3: create chat room.
     * We will first check if initiator is authenticated/logged in.
     * A chatroom with specified chatroom name will be created. An id (synthetic key) will be assigned to the new chatroom.
     * The new chat room will have security level (private or public) specified by privateChatroom
     * Initiator will be granted Admin privilege for the chat room.
     *
     * @param initiator       the user who send this request
     * @param chatroomName    name of chat room that user wants to create
     * @param privateChatroom security level of the chat room
     * @return json response
     */
    public JsonResponse createChatroom(IUser initiator, String chatroomName, boolean privateChatroom, int chatroomSize) {
        if (chatroomSize <= 0) {
            return new JsonResponse(false, null, "Chat room size is not valid", 400);
        }
        IChatRoom chatRoom = chatroomStore.createChatroom(chatroomName, chatroomSize, privateChatroom);
        chatRoom.addAdmin(initiator.getUsername());
        chatRoom.addUserToChatroom(initiator.getUsername());
        this.pcs.firePropertyChange("", "", MessageCommandFactory.getInstance().make(null, "update_info"));
        return new JsonResponse(true, chatRoom, "");
    }

    /**
     * Use case 4: join chatroom.
     * Use case 5: invite user to chatroom.
     * To implement his use case, we will first check if initiator is authenticated/logged in.
     * Then check if addedUsername and chatroomId are valid.
     * Then, we will check if initiator and addedUser are the same user, thus determine which case to use.
     * If initiator has the same username as addedUsername, this is a voluntary action and covers case 4.
     * Otherwise, initiator is adding another user into chat room and covers case 5.
     * Then, we will check whether user has the privilege to join specified chat room (cannot join private voluntarily, but may be invited).
     * If conditions are satisfied, user will be added to the specified chatroom.
     *
     * @param initiator     user who is adding another user (may be self) into chatroom
     * @param addedUsername user (maybe self) who is being added into chatroom
     * @param chatroomId    id of the chat room
     * @return json response
     */
    public JsonResponse joinUserIntoChatroom(IUser initiator, String addedUsername, int chatroomId) {
        IUser addedUser = userDB.getUserInfo(addedUsername);
        if (addedUser == null) {
            return new JsonResponse(false, null, addedUsername + " not found", 404);
        }
        IChatRoom chatRoom = chatroomStore.getChatroom(chatroomId);
        if (chatRoom == null) {
            return new JsonResponse(false, null, "Chat room id not found", 404);
        }
        if (chatRoom.isUserInChatroom(addedUser.getUsername())) {
            return new JsonResponse(false, null, "User already in that chatroom", 400);
        }

        if (initiator.getUsername().equals(addedUsername)) {
            if (chatRoom.isPrivateChatroom()) {
                return new JsonResponse(false, null, "User is not authorized to join private chatroom", 403);
            }
        } else if (!chatRoom.isPrivateChatroom()) {
            if (chatRoom.isUserAdmin(initiator.getUsername())) {
                chatroomStore.getChatroom(chatroomId).addUserToChatroom(addedUsername);
                this.pcs.firePropertyChange("", "", MessageCommandFactory.getInstance().make(null, "update_info"));
                return new JsonResponse(true, null, "successfully joined chatroom");
            } else {
                this.pcs.firePropertyChange("", "", MessageCommandFactory.getInstance().make(null, "update_info"));
                return new JsonResponse(true, null, "User is not authorized to invite other users", 403);
            }
        }
        chatroomStore.getChatroom(chatroomId).addUserToChatroom(addedUsername);
        this.pcs.firePropertyChange("", "", MessageCommandFactory.getInstance().make(null, "update_info"));
        return new JsonResponse(true, null, "successfully joined chatroom");
    }

    /**
     * Use case 6: User voluntarily leave chatroom.
     * Use case 7: Admin kick user out of chatroom.
     * To implement his use case, we will first check if initiator is authenticated/logged in.
     * Then, check if addedUsername and chatroomId are valid.
     * Then, we will check if initiator and removed User are the same user, thus determine which case to use.
     * If initiator has the same username as addedUsername, this is a voluntary action and covers case 6.
     * Otherwise, initiator is removing another user from chat room and covers case 7.
     * Then, we will check whether initiator has the privilege to remove user from specified chat room (all user can remove self, admin can remove non-admin users).
     * If conditions are satisfied, user will be removed from the specified chatroom.
     *
     * @param initiator       user who is removing another user (maybe self) from chatroom
     * @param removedUsername user (maybe self) who is being removed from chatroom
     * @param chatroomId      id of the chat room
     * @return json response
     */
    public JsonResponse removeUserFromChatroom(IUser initiator, String removedUsername, int chatroomId) {
        IUser removedUser = userDB.getUserInfo(removedUsername);
        if (removedUser == null) {
            return new JsonResponse(false, null, removedUsername + " not found", 404);
        }
        IChatRoom chatRoom = chatroomStore.getChatroom(chatroomId);
        if (chatRoom == null) {
            return new JsonResponse(false, null, "chat room not found", 404);
        }

        if (removedUser.getUsername().equals(initiator.getUsername())) {
            chatRoom.removeUserFromChatroom(removedUsername);

            // If admin leaves chatroom, make another user admin. If no user is admin, delete chatroom.
            if (chatRoom.isUserAdmin(removedUsername)) {
                chatRoom.removeAdmin(removedUsername);
                if (chatRoom.getUsersInChatroom().length == 0) {
                    chatroomStore.removeChatroom(chatroomId);
                } else {
                    chatRoom.addAdmin(chatRoom.getUsersInChatroom()[0]);
                }
            }
            this.pcs.firePropertyChange("", "", MessageCommandFactory.getInstance().make(null, "update_info"));
            return new JsonResponse(true, null, "");
        }
        if (chatRoom.isUserAdmin(initiator.getUsername())) {
            chatRoom.removeUserFromChatroom(removedUsername);
            chatRoom.addUserToBanList(removedUsername);
            this.pcs.firePropertyChange("", "", MessageCommandFactory.getInstance().make(null, "update_info"));
            return new JsonResponse(true, null, "");
        } else {
            return new JsonResponse(false, null, "user is not authorized to ban user.", 403);
        }
    }

    /**
     * Use case 8.
     * Get User in the chatroom.
     * We will first authenticate if the user has the privilege for getting other user info in the chatroom by checking
     * if the user is in the chatroom by calling <code>chatroomStore.getChatroom</code> and
     * <code>chatroom.isUserInChatroom</code>. Then, we will get all users in the chatroom by calling
     * <code>chatroom.getUsersInChatroom</code> and finally, we will look the usernames up in the userDB with
     * <code>userDB.getUser</code> and put the <code>IUser</code> objects in an array and return it.
     *
     * @param initiator  the user called this method
     * @param chatroomId chatroom id
     * @return json response
     */
    public JsonResponse getUserInChatroom(IUser initiator, int chatroomId) {
        IChatRoom chatRoom = chatroomStore.getChatroom(chatroomId);
        if (chatRoom == null) {
            return new JsonResponse(false, null, "", 404);
        }
        String[] usernames = chatroomStore.getChatroom(chatroomId).getUsersInChatroom();
        IUser[] result = new IUser[usernames.length];
        for (int i = 0; i < usernames.length; i++) {
            result[i] = userDB.getUserInfo(usernames[i]);
        }
        return new JsonResponse(true, result, "");
    }

    /**
     * Use case 10: send message.
     * Send a message to a user or chatroom.
     * First we will check if the user has the privilege to send message by checking whether the user is not banned
     * and the user is in the chatroom gotten by <code>chatroomStore.getChatroom</code>. Then we will create a new
     * <code>IMessage</code> instance and store it by calling <code>messageStore.addMessage</code>. Then we will
     * call <code>pcs.firePropertyChange</code> and pass in the UpdateMessageCommand with message as message and 'send'
     * as method. The command will then call the SendMessageStrategy which will check if the user is the target receiver
     * of the message and if so, it will send the user the packet indicating the new message received.
     *
     * @param initiator  the user called this method
     * @param content    message content
     * @param chatroomId chat room id
     * @param target     target user's username, 'all' for all user in the chatroom
     */
    public void sendMessageInChatRoom(IUser initiator, String content, int chatroomId, String target) {
        if (ChatroomStore.getInstance().getChatroom(chatroomId).isUserInChatroom(initiator.getUsername())) {
            IMessage message = messageStore.addMessage(content, chatroomId, initiator, target);
            this.pcs.firePropertyChange("", null, MessageCommandFactory.getInstance().make(message, "send"));
        } else {
            //throws exception
        }
    }

    //Use case 10 should be on client side.

    //Use case 11: delete message

    /**
     * Use case 11: delete message.
     * Edit an existing message.
     * First we will get the IMessage object based on the provided messageId with <code>messageStore.getMessage()</code>
     * Then, if the message exists, we will authenticate if the user has the privilege to edit this message by
     * checking if the user sent this message by comparing <code>IMessage.getSender</code> and
     * <code>initiator.getUsername</code> or checking if the user is the admin in the chatroom which the message belongs.
     * Then we will fire the <code>pcs.firePropertyChange</code> and pass the UpdateMessageCommand with
     * <code>IMessage</code> object as message and 'delete' as method. Then the command will call DeleteMessageStrategy
     * which will check if the user can see the message, if they can, then it will send a  message to the user
     * indicating the deleted message.
     *
     * @param initiator the user called this method
     * @param messageId message if of the message
     */
    public void deleteMessage(IUser initiator, int messageId) {
        IMessage message = MessageStore.getInstance().getMessage(messageId);
        if (MessageStore.checkPrivilegeMessage(initiator.getUsername(), messageId) || ChatroomStore.checkPrivilegeChatroom(initiator.getUsername(), message.getChatroomId())) {
            message = messageStore.removeMessage(messageId);
            this.pcs.firePropertyChange("", "", MessageCommandFactory.getInstance().make(message, "delete"));
        } else {
            //throws exception
        }
    }

    /**
     * Use case 12: edit message.
     * Edit an existing message.
     * First we will get the IMessage object based on the provided messageId with <code>messageStore.getMessage()</code>
     * Then, if the message exists, we will authenticate if the user has the privilege to edit this message by
     * checking if the user sent this message by comparing <code>IMessage.getSender</code> and
     * <code>initiator.getUsername</code>. Then the IMessage object's content will be updated with the new
     * <code>newContent</code> by calling <code>messageStore.updateMessageContent</code>.
     * Then we will fire the <code>pcs.firePropertyChange</code> and pass the
     * UpdateMessageCommand with <code>IMessage</code> object as message and 'edit' as method. Then the command will
     * call editMessageStrategy which will check if the user can see the message, if they can, then it will send a
     * message to the user indicating the changed message.
     *
     * @param initiator  the user called this method
     * @param messageId  id of the message
     * @param newContent new content for the message
     */
    @Override
    public void editMessage(IUser initiator, int messageId, String newContent) {
        if (MessageStore.checkPrivilegeMessage(initiator.getUsername(), messageId)) {
            IMessage message = messageStore.updateMessageContent(initiator, messageId, newContent);
            this.pcs.firePropertyChange("", "", MessageCommandFactory.getInstance().make(message, "edit"));
        } else {
            //throws exception
        }

    }


    /**
     * Use case 13 login user.
     * Login user returns the jwt for the user
     * First we will call <code>userDB.authenticateUser</code> method to authenticate a user. If the username and
     * password is valid, we will call <code>IAuthenticator.getJwtForUser</code> to generate jwt for the user and return
     * the jwt.
     *
     * @param username User's username
     * @param password password
     * @return "" if login fails, jwt for the user if login success
     */
    public String loginUser(String username, String password) {
        IUser iu = userDB.authenticateUser(username, password);
        if (iu != null) {
            Authenticator au = Authenticator.getInstance();
            return au.getJwtForUser(iu);
        }
        return null;
    }

}
