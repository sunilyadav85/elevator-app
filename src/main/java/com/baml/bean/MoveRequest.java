package com.baml.bean;

import com.baml.enums.ElevatorDirection;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import static com.baml.enums.ElevatorDirection.DOWN;
import static com.baml.enums.ElevatorDirection.UP;

public class MoveRequest {

    private static final int GROUND_FLOOR = 0;
    private static final int MAX_FLOORS = 12;

    private final int fromFloor;
    private final int toFloor;
    private final ElevatorDirection elevatorDirection;

    public MoveRequest(int fromFloor, int toFloor) {
        validateMoveRequest(fromFloor, toFloor);
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
        elevatorDirection = fromFloor < toFloor ? UP : DOWN;
    }

    public void validateMoveRequest(int fromFloor, int toFloor) {
        if (fromFloor > MAX_FLOORS || toFloor > MAX_FLOORS) {
            String errorMsg = String.format("FromFloor [%d] or ToFloor [%d] is greater than the number of floors in the building [%d].", fromFloor, toFloor, MAX_FLOORS);
            throw new IllegalArgumentException(errorMsg);
        } else if (fromFloor < GROUND_FLOOR || toFloor < GROUND_FLOOR) {
            String errorMsg = String.format("FromFloor [%d] or ToFloor [%d] cannot be less than zero.", fromFloor, toFloor);
            throw new IllegalArgumentException(errorMsg);
        } else if (fromFloor == toFloor) {
            String errorMsg = String.format("From Floor [%d] cannot be the same as To Floor [%d]", fromFloor, toFloor);
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public ElevatorDirection getElevatorDirection() {
        return elevatorDirection;
    }

    public int getFromFloor() {
        return fromFloor;
    }

    public int getToFloor() {
        return toFloor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoveRequest that = (MoveRequest) o;

        if (fromFloor != that.fromFloor) return false;
        if (toFloor != that.toFloor) return false;
        if (elevatorDirection != that.elevatorDirection) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fromFloor;
        result = 31 * result + toFloor;
        result = 31 * result + elevatorDirection.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
