package com.baml.bean;

import java.util.LinkedList;
import java.util.List;

public class MoveRequestList {

    private final List<MoveRequest> moveRequests = new LinkedList<>();
    private int initialFloor;

    public void addToMoveRequests(MoveRequest moveRequest) {
        moveRequests.add(moveRequest);
    }

    public List<MoveRequest> getMoveRequests() {
        return moveRequests;
    }

    public void setInitialFloor(int initialFloor) {
        this.initialFloor = initialFloor;
    }

    public int getInitialFloor() {
        return initialFloor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoveRequestList that = (MoveRequestList) o;

        if (initialFloor != that.initialFloor) return false;
        if (moveRequests != null ? !moveRequests.equals(that.moveRequests) : that.moveRequests != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = moveRequests != null ? moveRequests.hashCode() : 0;
        result = 31 * result + initialFloor;
        return result;
    }
}
