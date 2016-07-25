package com.baml.bean;

import com.baml.enums.Direction;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class Elevator {

    private Direction direction = Direction.NA;
    private int initialFloor;
    private Queue<MoveRequest> moveRequests = new LinkedList<>();

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getInitialFloor() {
        return initialFloor;
    }

    public void setInitialFloor(int initialFloor) {
        this.initialFloor = initialFloor;
    }

    public Queue<MoveRequest> getMoveRequests() {
        return moveRequests;
    }

    public void addMoveRequests(Collection<MoveRequest> moveRequests) {
        moveRequests.forEach(this.moveRequests::add);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
