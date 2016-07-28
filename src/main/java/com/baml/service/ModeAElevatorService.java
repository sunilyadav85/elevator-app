package com.baml.service;

import com.baml.bean.Elevator;
import com.baml.bean.ElevatorResult;
import com.baml.bean.MoveRequest;
import com.baml.enums.ElevatorDirection;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Queue;

@Service("modeAElevatorService")
public class ModeAElevatorService implements ElevatorService {

    @Autowired
    private ElevatorServiceHelper elevatorServiceHelper;

    @Override
    public ElevatorResult operate(Elevator elevator) {

        Queue<MoveRequest> allMoveRequests = elevator.getMoveRequests();
        ElevatorResult elevatorResult = new ElevatorResult();
        elevatorResult.addToFloorsTravelled(elevator.getInitialFloor());

        while (allMoveRequests.iterator().hasNext()) {
            MoveRequest moveRequestToProcess = allMoveRequests.poll();
            ElevatorDirection elevatorDirection = elevatorServiceHelper.getElevatorDirection(elevator, moveRequestToProcess);

            int lastFloorTravelled = Iterables.getLast(elevatorResult.getFloorsTravelled());
            Collection<Integer> floorsInSameDirection = elevatorServiceHelper.getFloorsInSameDirection(lastFloorTravelled, moveRequestToProcess, elevatorDirection);
            elevatorResult.addToFloorsTravelled(floorsInSameDirection);
            elevatorServiceHelper.updateElevatorDirection(elevator);
        }

        int distanceTravelled = elevatorServiceHelper.getDistanceTravelled(elevatorResult.getFloorsTravelled());
        elevatorResult.setDistanceTravelled(distanceTravelled);

        return elevatorResult;
    }
}