package com.baml.service;

import com.baml.bean.Elevator;
import com.baml.bean.ElevatorResult;
import com.baml.bean.MoveRequest;
import com.baml.enums.Direction;
import com.google.common.collect.Iterables;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TreeSet;

@Component
public class ElevatorServiceHelper {

    protected int evaluateDistanceTravelled(ElevatorResult elevatorResult) {
        int distanceTravelled = 0;
        List<Integer> floorsTravelled = elevatorResult.getFloorsTravelled();
        for (int i = 0, j = 1; j < floorsTravelled.size(); i++, j++) {
            distanceTravelled += Math.abs(floorsTravelled.get(i) - floorsTravelled.get(j));
        }
        return distanceTravelled;
    }

    protected void updateElevatorDirection(Elevator elevator) {
        if (elevator.getMoveRequests().isEmpty()) {
            elevator.setDirection(Direction.NA);
        } else if (Direction.DOWN == elevator.getDirection()) {
            elevator.setDirection(Direction.UP);
        } else if (Direction.UP == elevator.getDirection()) {
            elevator.setDirection(Direction.DOWN);
        }
    }

    protected void addFloors(MoveRequest moveRequestToProcess, TreeSet<Integer> floorsToTravel) {
        floorsToTravel.add(moveRequestToProcess.getFromFloor());
        floorsToTravel.add(moveRequestToProcess.getToFloor());
    }

    protected void move(ElevatorResult elevatorResult, TreeSet<Integer> floorsToTravel, Direction elevatorDirection) {

        if (Direction.DOWN == elevatorDirection) {
            floorsToTravel = (TreeSet<Integer>) floorsToTravel.descendingSet();
        }

        int lastFloorTravelled = Iterables.getLast(elevatorResult.getFloorsTravelled());
        if (lastFloorTravelled == floorsToTravel.first()) {
            floorsToTravel.pollFirst();
        }
        elevatorResult.addToFloorsTravelled(floorsToTravel);
        floorsToTravel.clear();
    }
}
