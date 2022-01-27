package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.board.IPacmanBoard;
import edu.rice.comp504.model.object.ObjectType;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * AStar search algorithm util.
 */
public class AStarSearch {
    /**
     * Using AStar to find the next location on the path moving from start point to target point.
     *
     * @param start  start location
     * @param target target location
     * @param board  the game board
     * @return next location on the path
     */
    public static Point towards(Point start, Point target, IPacmanBoard board) {
        if (start.equals(target)) {
            return new Point(start);
        }
        List<State> openList = new ArrayList<State>();
        List<State> closeList = new ArrayList<State>();
        boolean findFlag = false;
        State termState = null;
        State startState = new State(start.x, start.y, manhattanDist(start, target));
        openList.add(startState);
        do {
            // find the state with the least evaluation value in open list.
            State currentState = openList.get(0);
            for (int i = 0; i < openList.size(); i++) {
                if (currentState.f > openList.get(i).f) {
                    currentState = openList.get(i);
                }
            }
            // remove the state from open list and add it into close list.
            closeList.add(currentState);
            openList.remove(currentState);

            // loop through possible next positions of current position in four directions.
//            int[][] gameBoard = board.getBoard();
            for (int i = 0; i <= 3; i++) {
                int newX = currentState.loc.x;
                int newY = currentState.loc.y;
                if (i == 0) {
                    newX -= 1;
                } else if (i == 1) {
                    newX += 1;
                } else if (i == 2) {
                    newY -= 1;
                } else {
                    newY += 1;
                }
                int[][] gameBoard = board.getBoard();
                // if the position is a wall or out of bound, skip it.

                if (newX < 0 || newY < 0 || newX >= gameBoard.length || newY >= gameBoard[0].length || gameBoard[newX][newY] == ObjectType.WALL.getCode()) {
                    continue;
                }
                State nextState = new State(newX, newY, manhattanDist(new Point(newX, newY), target), currentState);
                // if the position have been visited and explored, skip it.
                if (closeList.contains(nextState)) {
                    continue;
                }
                // if the position have not been visited, add it into the open list.
                // else update the state of this position of a shorter path is found.
                if (!openList.contains(nextState)) {
                    openList.add(nextState);
                } else {
                    State preState = null;
                    for (State state : openList) {
                        if (state.loc.x == newX && state.loc.y == newY) {
                            preState = state;
                            break;
                        }
                    }
                    if (nextState.g < preState.g) {
                        preState.setParent(currentState);
                    }
                }

            }
            // if the target point has been reached, record and update it.
            for (State state : openList) {
                if (state.loc.x == target.x && state.loc.y == target.y) {
                    termState = state;
                    findFlag = true;
                    break;
                }
            }

        } while (openList.size() != 0);

        if (!findFlag) {
            return null;
        }

        Stack<Point> resStack = new Stack<>();
        if (termState != null) {
            resStack.push(termState.loc);
            while (termState.parent != null) {
                termState = termState.parent;
                resStack.push(termState.loc);
            }
        }
        resStack.pop();
        return resStack.pop();
    }

    /**
     * AStar heuristic by Manhattan Distance.
     *
     * @param a one point
     * @param b the other point
     * @return manhattan distance between two points.
     */
    private static int manhattanDist(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    /**
     * State class to store the state of (location, parent state on path, cost g, heuristic h, evaluation f) during AStar Search.
     */
    static class State {
        public Point loc;
        public int f;
        public int g;
        public int h;
        public State parent;

        /**
         * Constructor without parent state for the start state.
         *
         * @param x x of location
         * @param y y of location
         * @param h heuristic value
         */
        public State(int x, int y, int h) {
            this(x, y, h, null);
        }

        /**
         * Constructor with parent state.
         *
         * @param x x of location
         * @param y y of location
         * @param h heuristic value
         */
        public State(int x, int y, int h, State parent) {
            this.loc = new Point(x, y);
            this.h = h;
            this.parent = parent;
            if (parent == null) {
                this.g = 0;
            } else {
                this.g = parent.g + 1;
            }
            this.f = g + h;
        }

        /**
         * Update the parent state.
         *
         * @param parent parent state
         */
        public void setParent(State parent) {
            this.parent = parent;
            this.g = parent.g + 1;
            this.f = g + h;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof State)) {
                return false;
            }
            State pos = (State) obj;
            return this.loc.x == pos.loc.x && this.loc.y == pos.loc.y;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + loc.x;
            result = prime * result + loc.y;
            result = prime * result + f;
            return result;
        }

    }

//    public static class Tuple<X, Y> {
//        public final X x;
//        public final Y y;
//        public Tuple(X x, Y y) {
//            this.x = x;
//            this.y = y;
//        }
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            Tuple<?, ?> tuple = (Tuple<?, ?>) o;
//            return Objects.equals(x, tuple.x) && Objects.equals(y, tuple.y);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(x, y);
//        }
//    }
//    private static int getMattDistance(Point a, Point b) {
//        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
//    }
//
//    public static Point towards(Point start, Point target, IPacmanBoard board) {
//            ArrayList<Tuple<Point, ArrayList<Point>>> visited = new ArrayList<>();
//            PriorityQueue<Tuple<Tuple<Point, ArrayList<Point>>, Integer>> frontier = new PriorityQueue<>((o1, o2) -> o1.y < o2.y ? 1 : -1);
//            frontier.add(new Tuple(new Tuple(start, new ArrayList<>()), getMattDistance(start, target)));
//            while (!frontier.isEmpty()) {
//                Tuple<Tuple<Point, ArrayList<Point>>, Integer> current = frontier.poll();
//                if (current.x.x.x == target.x && current.x.x.y == target.y) {
//                    //                object.setLocation(current.x.y.get(0));
//                    return current.x.y.get(0);
//                }
//
//                if (!visited.contains(current.x.x)) {
//                    for (int i = -1; i <= 1; i += 2) {
//                        for (int j = -1; i <= 1; i += 2) {
//                            int newX = current.x.x.x + i;
//                            int newY = current.x.x.y + j;
//                            int[][] gameBoard = board.getBoard();
//                            if (newX > 0 && newY > 0 && newX < gameBoard[0].length && newY < gameBoard.length  && gameBoard[newX][newY] != 0) {
//                                Point successor = new Point(newX, newY);
//                                if (!visited.contains(successor)) {
//                                    ArrayList<Point> branch = current.x.y;
//                                    branch.add(successor);
//                                    int cost = branch.size();
//                                    frontier.add(new Tuple(new Tuple(successor, branch), cost + getMattDistance(successor, target)));
//                                }
//                            }
//                        }
//                    }
//                    visited.add(current.x);
//                }
//            }
//        return start;
//    }
}
