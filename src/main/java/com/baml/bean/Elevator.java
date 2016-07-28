package com.baml.bean;

import com.baml.enums.ElevatorDirection;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import static com.baml.enums.ElevatorDirection.NA;

@Component
public class Elevator {

    private ElevatorDirection elevatorDirection = NA;
    private int initialFloor;
    private final Queue<MoveRequest> moveRequests = new LinkedList<>();

    public ElevatorDirection getElevatorDirection() {
        return elevatorDirection;
    }

    public void setElevatorDirection(ElevatorDirection elevatorDirection) {
        this.elevatorDirection = elevatorDirection;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Elevator elevator = (Elevator) o;

        if (initialFloor != elevator.initialFloor) return false;
        if (elevatorDirection != elevator.elevatorDirection) return false;
        if (moveRequests != null ? !moveRequests.equals(elevator.moveRequests) : elevator.moveRequests != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = elevatorDirection.hashCode();
        result = 31 * result + initialFloor;
        result = 31 * result + (moveRequests != null ? moveRequests.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
