package edu.rice.comp504.controller;

import com.google.gson.*;
import edu.rice.comp504.adapter.HttpRequestAdapter;
import edu.rice.comp504.adapter.WebSocketAdapter;
import edu.rice.comp504.model.authenticate.Authenticator;
import edu.rice.comp504.model.chatroom.Chatroom;
import edu.rice.comp504.model.chatroom.ChatroomStore;
import edu.rice.comp504.model.chatroom.IChatRoom;
import edu.rice.comp504.model.store.ChatAppStore;
import edu.rice.comp504.model.user.IUser;
import edu.rice.comp504.model.user.User;
import edu.rice.comp504.model.userstorage.UserDB;
import edu.rice.comp504.util.GsonExcludeStrategy;
import edu.rice.comp504.util.JsonResponse;
import spark.Request;

import static spark.Spark.*;

/**
 * The chat app controller communicates with all the clients on the web socket.
 */
public class ChatApiController {
    private static final JsonParser jsonParser = new JsonParser();
    /**
     * Chat App entry point.
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        //TODO: remove this
//        loadTestingData();

        HttpRequestAdapter httpRequestAdapter = HttpRequestAdapter.getInstance();
        port(getHerokuAssignedPort());

        staticFiles.location("/public");


        webSocket("/chatapp", WebSocketAdapter.class);
        Gson gson = new GsonBuilder().setExclusionStrategies(new GsonExcludeStrategy()).create();


        // Use case 1: register
        post("/register", (request, response) -> gson.toJson(null));

        // Use case 1 : Create account
        post("/user/create", (request, response) -> {
            JsonResponse jsonResponse = httpRequestAdapter.registerUser(jsonParser.parse(request.body()).getAsJsonObject());
            response.status(jsonResponse.statusCode);
            return gson.toJson(jsonResponse);
        });

        // Use case 12 : Login registered user
        post("/user/login", (request, response) -> {
            return gson.toJson(httpRequestAdapter.logInRegisteredUser(jsonParser.parse(request.body()).getAsJsonObject()));
        });


        // Use case 2 : Present user’s info
        get("/user/:username", (request, response) -> {
            JsonResponse jsonResponse = httpRequestAdapter.getUserInfo(getAuthorization(request), request.params(":username"));
            response.status(jsonResponse.statusCode);
            return gson.toJson(jsonResponse);
        });


        // Use case 3: Create Chatroom
        post("/chatroom/create", ((request, response) -> {
            JsonResponse jsonResponse = httpRequestAdapter.createChatRoom(getAuthorization(request), jsonParser.parse(request.body()).getAsJsonObject());
            response.status(jsonResponse.statusCode);
            return gson.toJson(jsonResponse);
        }));

        // Use case 4&5: Join Chatroom
        post("/chatroom/join", ((request, response) -> {
            JsonResponse jsonResponse = httpRequestAdapter.addUserToChatRoom(getAuthorization(request), jsonParser.parse(request.body()).getAsJsonObject());
            response.status(jsonResponse.statusCode);
            return gson.toJson(jsonResponse);
        }));

        delete("/chatroom/:chatroom_id/member/:username", ((request, response) -> {
            JsonResponse jsonResponse = httpRequestAdapter.removeUserFromChatroom(getAuthorization(request), Integer.parseInt(request.params(":chatroom_id")), request.params(":username"));
            response.status(jsonResponse.statusCode);
            return gson.toJson(jsonResponse);
        }));

        get("/chatroom", ((request, response) -> {
            JsonResponse jsonResponse = httpRequestAdapter.getAllChatRooms(getAuthorization(request));
            response.status(jsonResponse.statusCode);
            return gson.toJson(jsonResponse);
        }));
        get("/chatroom/:chatroom_id/members", ((request, response) -> {
            JsonResponse jsonResponse = httpRequestAdapter.getUserInChatroom(getAuthorization(request), request.params(":chatroom_id"));
            response.status(jsonResponse.statusCode);
            return gson.toJson(jsonResponse);
        }));

        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request
                    .headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers",
                        accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request
                    .headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods",
                        accessControlRequestMethod);
            }
            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "http://localhost:3000");
            response.header("Access-Control-Allow-Methods", "*");
            response.header("Access-Control-Allow-Credentials", "*");
        });

        init();
    }

    /**
     * Get the heroku assigned port number.
     *
     * @return The heroku assigned port number
     */
    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; // return default port if heroku-port isn't set.
    }

    // Load testing data, should be deleted
    private static void loadTestingData() {
        IUser user = (IUser) ChatAppStore.getInstance().registerNewUser("test2", "test", "rice", 19, "play", "fun").payload;
        IUser admin = (IUser) ChatAppStore.getInstance().registerNewUser("admin", "admin", "rice", 20, "sleep").payload;
        IUser user2 = (IUser) ChatAppStore.getInstance().registerNewUser("test", "test", "test school", 18).payload;
        IChatRoom chatRoom = ChatroomStore.getInstance().createChatroom("room1", 10, false);

        chatRoom.addAdmin(admin.getUsername());
        chatRoom.addUserToChatroom(user.getUsername());
        chatRoom.addUserToChatroom(user2.getUsername());
        chatRoom.addUserToChatroom(admin.getUsername());
        UserDB.getInstance().addNewUser(user);
        UserDB.getInstance().addNewUser(user2);
        UserDB.getInstance().addNewUser(admin);

        System.out.println(Authenticator.getInstance().getJwtForUser(user));
    }

    private static String getAuthorization(Request request) {
        if (request.headers("Authorization") != null && request.headers("Authorization").split(" ").length == 2) {
            return request.headers("Authorization").split(" ")[1];
        } else {
            return request.cookie("auth");
        }
    }
}
