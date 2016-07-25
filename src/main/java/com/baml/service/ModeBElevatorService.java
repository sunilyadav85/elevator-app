package com.baml.service;

import com.baml.bean.Elevator;
import com.baml.bean.ElevatorResult;
import com.baml.bean.MoveRequest;
import com.baml.enums.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.TreeSet;

@Service("modeBElevatorService")
public class ModeBElevatorService implements ElevatorService {

    @Autowired
    private ElevatorServiceHelper elevatorServiceHelper;

    @Override
    public ElevatorResult operate(Elevator elevator) {

        Queue<MoveRequest> allMoveRequests = elevator.getMoveRequests();
        ElevatorResult elevatorResult = new ElevatorResult();
        elevatorResult.addToFloorsTravelled(elevator.getInitialFloor());
        TreeSet<Integer> floorsToTravel = new TreeSet<>();

        while (allMoveRequests.iterator().hasNext()) {
            MoveRequest moveRequestToProcess = allMoveRequests.poll();
            MoveRequest nextMoveRequest = allMoveRequests.peek();

            Direction elevatorDirection = (Direction.NA == elevator.getDirection()) ? moveRequestToProcess.getDirection() : elevator.getDirection();
            elevatorServiceHelper.addFloors(moveRequestToProcess, floorsToTravel);

            if ((nextMoveRequest != null && nextMoveRequest.getDirection() != elevatorDirection) || allMoveRequests.isEmpty()) {
                elevatorServiceHelper.move(elevatorResult, floorsToTravel, elevatorDirection);
                elevatorServiceHelper.updateElevatorDirection(elevator);
            }
        }

        int distanceTravelled = elevatorServiceHelper.evaluateDistanceTravelled(elevatorResult);
        elevatorResult.setDistanceTravelled(distanceTravelled);

        return elevatorResult;
    }
}
