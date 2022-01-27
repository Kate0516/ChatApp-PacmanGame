package edu.rice.comp504.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import edu.rice.comp504.adapter.DispatchAdapter;
import edu.rice.comp504.util.GsonExcludeStrategy;
import edu.rice.comp504.util.JsonStatusResponse;


import static spark.Spark.*;

public class PacmanController {
    /**
     * The main entry point into the program.
     *
     * @param args The program arguments normally specified on the cmd line
     */
    public static void main(String[] args) {
        staticFiles.location("/public");
        port(getHerokuAssignedPort());
        Gson gson = new GsonBuilder().setExclusionStrategies(new GsonExcludeStrategy()).create();
        DispatchAdapter da = DispatchAdapter.getInstance();

        get("/init/:ghostNum/:lifeNum", (request, response) -> {
            int ghostNum;
            int lifeNum;

            try {
                ghostNum = Integer.parseInt(request.params(":ghostNum"));
                lifeNum = Integer.parseInt(request.params(":lifeNum"));
            } catch (NumberFormatException e) {
                response.status(400);
                return gson.toJson(new JsonStatusResponse(false, null, "Invalid ghost num or life num", 400));
            }
            JsonStatusResponse resp = da.init(ghostNum, lifeNum);
            response.status(resp.getStatusCode());
            return gson.toJson(resp);
        });

        get("/:boardId/board", (request, response) -> {
            int boardId = Integer.parseInt(request.params(":boardId"));
            JsonStatusResponse resp = da.getBoard(boardId);
            response.status(resp.getStatusCode());
            return gson.toJson(resp);
        });

        get("/:boardId/change/:direction", (request, response) -> {
            int boardId = Integer.parseInt(request.params(":boardId"));
            String direction = request.params(":direction");
            return gson.toJson(da.movePacman(boardId, direction));
        });

        get("/:boardId/reset", (request, response) -> {
            int boardId = Integer.parseInt(request.params(":boardId"));
            return gson.toJson(da.reset(boardId));
        });

        get("/:boardId/start", ((request, response) -> {
            int boardId = Integer.parseInt(request.params(":boardId"));
            return gson.toJson(da.start(boardId));
        }));

        notFound((req, res) -> {
            res.redirect("/");
            return gson.toJson(new JsonStatusResponse(false, null, "redirecting to main page", 404));
        });


    }

    /**
     * Get the heroku assigned port number.
     *
     * @return The port number
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
