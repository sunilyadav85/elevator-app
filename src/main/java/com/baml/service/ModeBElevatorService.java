package com.baml.service;

import com.baml.bean.Elevator;
import com.baml.bean.ElevatorResult;
import com.baml.bean.MoveRequest;
import com.baml.enums.ElevatorDirection;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

@Service("modeBElevatorService")
public class ModeBElevatorService implements ElevatorService {

    @Autowired
    private ElevatorServiceHelper elevatorServiceHelper;

    @Override
    public ElevatorResult operate(Elevator elevator) {

        Queue<MoveRequest> allMoveRequests = elevator.getMoveRequests();
        ElevatorResult elevatorResult = new ElevatorResult();
        elevatorResult.addToFloorsTravelled(elevator.getInitialFloor());
        List<MoveRequest> moveRequestsInSameDirection = new ArrayList<>();

        while (allMoveRequests.iterator().hasNext()) {

            MoveRequest moveRequestToProcess = allMoveRequests.poll();
            MoveRequest nextMoveRequest = allMoveRequests.peek();

            ElevatorDirection elevatorDirection = elevatorServiceHelper.getElevatorDirection(elevator, moveRequestToProcess);
            moveRequestsInSameDirection.add(moveRequestToProcess);
            if ((nextMoveRequest != null && nextMoveRequest.getElevatorDirection() != elevatorDirection) || allMoveRequests.isEmpty()) {
                int lastFloorTravelled = Iterables.getLast(elevatorResult.getFloorsTravelled());
                Collection<Integer> floorsInSameDirection = elevatorServiceHelper.getFloorsInSameDirection(lastFloorTravelled, moveRequestsInSameDirection, elevatorDirection);
                elevatorResult.addToFloorsTravelled(floorsInSameDirection);
                moveRequestsInSameDirection.clear();
                elevatorServiceHelper.updateElevatorDirection(elevator);
            }
        }

        int distanceTravelled = elevatorServiceHelper.getDistanceTravelled(elevatorResult.getFloorsTravelled());
        elevatorResult.setDistanceTravelled(distanceTravelled);

        return elevatorResult;
    }
}