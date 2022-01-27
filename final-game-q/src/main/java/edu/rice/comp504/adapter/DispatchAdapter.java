package edu.rice.comp504.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.rice.comp504.model.board.BoardStore;
import edu.rice.comp504.model.board.IPacmanBoard;
import edu.rice.comp504.model.board.PacmanBoard;
import edu.rice.comp504.util.GsonExcludeStrategy;
import edu.rice.comp504.util.JsonStatusResponse;

public class DispatchAdapter {
    private final BoardStore bs;
    private static final Gson gson = new GsonBuilder().setExclusionStrategies(new GsonExcludeStrategy()).create();

    private DispatchAdapter() {
        this.bs = BoardStore.getInstance();
    }

    private static final DispatchAdapter instance = new DispatchAdapter();

    public static DispatchAdapter getInstance() {
        return instance;
    }

    /**
     * Initialize the game.
     *
     * @param ghostNum number of ghosts
     * @param lifeNum  number of lives
     * @return json response
     */
    public JsonStatusResponse init(int ghostNum, int lifeNum) {
        if (ghostNum < 3 || ghostNum > 8) {
            return new JsonStatusResponse(false, null, "invalid ghost num, should be between 3 and 8.", 400);
        }
        if (lifeNum < 3 || lifeNum > 6) {
            return new JsonStatusResponse(false, null, "invalid life num, should be between 3 and 6.", 400);
        }
        return new JsonStatusResponse(true, bs.createNewBoard(ghostNum, lifeNum), "", 200);
    }

    /**
     * Get the board instance with this board ID.
     *
     * @param boardId the board ID.
     * @return the JsonStatusResponse of this board.
     */
    public JsonStatusResponse getBoard(int boardId) {
        IPacmanBoard board = bs.getBoard(boardId);
        if (board != null) {
            JsonObject jo = new JsonObject();
            jo.add("board", gson.toJsonTree(board.getBoard()));
            jo.addProperty("score", board.getScore());
            jo.addProperty("level", board.getLevel());
            jo.addProperty("life", board.getLife());
            jo.addProperty("direction", board.getPacmanDirection());
            return new JsonStatusResponse(true, jo, "", 200);
        }
        return new JsonStatusResponse(false, null, "game board id not found", 404);
    }

    /**
     * Move PacMan with this given direction on the board with this board ID.
     *
     * @param boardId   the board ID
     * @param direction the new direction
     * @return the JsonStatusResponse of this moving result.
     */
    public JsonStatusResponse movePacman(int boardId, String direction) {
        return bs.changeDirection(boardId, direction);
    }

    /**
     * reset the board with this board ID.
     *
     * @param boardId the board ID
     * @return the JsonStatusResponse of the reset result.
     */
    public JsonStatusResponse reset(int boardId) {
        IPacmanBoard board = bs.getBoard(boardId);
        if (board != null) {
            return new JsonStatusResponse(true, board, "", 200);
        }
        return new JsonStatusResponse(false, null, "game board id not found", 404);
    }

    public JsonStatusResponse start(int boardId) {
        return bs.start(boardId);
    }

}
