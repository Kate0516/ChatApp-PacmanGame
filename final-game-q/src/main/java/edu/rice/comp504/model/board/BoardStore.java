package edu.rice.comp504.model.board;

import edu.rice.comp504.util.JsonStatusResponse;

import java.util.ArrayList;
import java.util.Set;

public class BoardStore implements IBoardStore {
    ArrayList<PacmanBoard> boards;
    Set<String> validDirection = Set.of("up", "down", "right", "left");

    private BoardStore() {
        this.boards = new ArrayList<>();
    }

    private static final BoardStore instance = new BoardStore();

    public static BoardStore getInstance() {
        return instance;
    }


    @Override
    public IPacmanBoard getBoard(int boardId) {
        // check if boardId is valid
        if (boardId >= 0 && boardId < boards.size()) {
            return boards.get(boardId);
        }
        return null;
    }

    @Override
    public int createNewBoard(int ghostNum, int lifeNum) {
        boards.add(new PacmanBoard(ghostNum, lifeNum));
        return boards.size() - 1;
    }

    @Override
    public JsonStatusResponse changeDirection(int boardId, String direction) {
        IPacmanBoard board = getBoard(boardId);
        if (board != null) {
            if (validDirection.contains(direction)) {
                board.changeDirection(direction);
                return new JsonStatusResponse(true, null, "Successfully changed the direction for the user", 200);
            } else {
                return new JsonStatusResponse(false, null, "Input direction is not valid. direction should be one of {up, down, right, left}", 400);
            }
        } else {
            return new JsonStatusResponse(false, null, "game board id not found", 404);
        }
    }

    @Override
    public JsonStatusResponse start(int boardId) {
        IPacmanBoard board = getBoard(boardId);
        if (board != null) {
            board.start();
            return new JsonStatusResponse(true, null, "Start the game", 200);
        } else {
            return new JsonStatusResponse(false, null, "game board id not found", 404);
        }
    }

    @Override
    public IPacmanBoard reset(int boardId) {
        // check if boardId is valid
        if (boardId >= 0 && boardId < boards.size()) {
            return boards.get(boardId);
        }
        return null;
    }

}
