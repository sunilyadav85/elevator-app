package com.baml.service;

import com.baml.bean.Elevator;
import com.baml.bean.MoveRequest;
import com.baml.enums.ElevatorDirection;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import static com.baml.enums.ElevatorDirection.*;

@Component
public class ElevatorServiceHelper {

    protected int getDistanceTravelled(List<Integer> floorsTravelled) {
        int distanceTravelled = 0;
        for (int i = 0, j = 1; j < floorsTravelled.size(); i++, j++) {
            distanceTravelled += Math.abs(floorsTravelled.get(i) - floorsTravelled.get(j));
        }
        return distanceTravelled;
    }

    protected void updateElevatorDirection(Elevator elevator) {
        if (elevator.getMoveRequests().isEmpty()) {
            elevator.setElevatorDirection(NA);
        } else if (DOWN == elevator.getElevatorDirection()) {
            elevator.setElevatorDirection(UP);
        } else if (UP == elevator.getElevatorDirection()) {
            elevator.setElevatorDirection(DOWN);
        }
    }

    protected Collection<Integer> getFloorsInSameDirection(int lastFloorTravelled, MoveRequest moveRequest, ElevatorDirection elevatorDirection) {
        return getFloorsInSameDirection(lastFloorTravelled, Lists.newArrayList(moveRequest), elevatorDirection);
    }

    protected Collection<Integer> getFloorsInSameDirection(int lastFloorTravelled, Collection<MoveRequest> moveRequests, ElevatorDirection elevatorDirection) {
        TreeSet<Integer> floorsToTravel = getAllFloorsFromMoveRequests(moveRequests);

        if (DOWN == elevatorDirection) {
            floorsToTravel = (TreeSet<Integer>) floorsToTravel.descendingSet();
        }

        if (lastFloorTravelled == floorsToTravel.first()) {
            floorsToTravel.pollFirst();
        }
        return floorsToTravel;
    }

    protected ElevatorDirection getElevatorDirection(Elevator elevator, MoveRequest moveRequestToProcess) {
        return (NA == elevator.getElevatorDirection()) ? moveRequestToProcess.getElevatorDirection() : elevator.getElevatorDirection();
    }

    private TreeSet<Integer> getAllFloorsFromMoveRequests(Collection<MoveRequest> moveRequests) {
        final TreeSet<Integer> floorsToTravel = new TreeSet<>();
        moveRequests.forEach(moveRequest -> floorsToTravel.addAll(Lists.newArrayList(moveRequest.getFromFloor(), moveRequest.getToFloor())));
        return floorsToTravel;
    }
}
