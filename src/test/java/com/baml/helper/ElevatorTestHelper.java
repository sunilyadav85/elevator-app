package com.baml.helper;

import com.baml.bean.ElevatorResult;
import com.baml.bean.MoveRequest;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Component
public class ElevatorTestHelper {

    public LinkedList<MoveRequest> getAllMoveRequest() {
        LinkedList<MoveRequest> allMoveRequests = new LinkedList<>();
        allMoveRequests.add(new MoveRequest(7, 9));
        allMoveRequests.add(new MoveRequest(3, 7));
        allMoveRequests.add(new MoveRequest(5, 8));
        allMoveRequests.add(new MoveRequest(7, 11));
        allMoveRequests.add(new MoveRequest(11, 1));
        return allMoveRequests;
    }

    public List<ElevatorResult> getElevatorResultsForModeA() {
        List<ElevatorResult> elevatorResults = new ArrayList<>();
        elevatorResults.add(getElevatorResult(9, Lists.newArrayList(10, 8, 1)));
        elevatorResults.add(getElevatorResult(30, Lists.newArrayList(9, 1, 5, 1, 6, 1, 5)));
        elevatorResults.add(getElevatorResult(16, Lists.newArrayList(2, 4, 1, 4, 2, 6, 8)));
        elevatorResults.add(getElevatorResult(36, Lists.newArrayList(3, 7, 9, 3, 7, 5, 8, 7, 11, 1)));
        elevatorResults.add(getElevatorResult(40, Lists.newArrayList(7, 11, 6, 10, 5, 6, 8, 7, 4, 12, 7, 8, 9)));
        elevatorResults.add(getElevatorResult(16, Lists.newArrayList(6, 1, 8, 6, 8)));

        return elevatorResults;
    }

    public List<ElevatorResult> getElevatorResultsForModeB() {
        List<ElevatorResult> elevatorResults = new ArrayList<>();
        elevatorResults.add(getElevatorResult(9, Lists.newArrayList(10, 8, 1)));
        elevatorResults.add(getElevatorResult(13, Lists.newArrayList(9, 1, 5, 6)));
        elevatorResults.add(getElevatorResult(12, Lists.newArrayList(2, 4, 2, 1, 6, 8)));
        elevatorResults.add(getElevatorResult(18, Lists.newArrayList(3, 5, 7, 8, 9, 11, 1)));
        elevatorResults.add(getElevatorResult(30, Lists.newArrayList(7, 11, 10, 6, 5, 6, 8, 12, 7, 4, 8, 9)));
        elevatorResults.add(getElevatorResult(12, Lists.newArrayList(6, 1, 6, 8)));

        return elevatorResults;
    }

    private ElevatorResult getElevatorResult(int distanceTravelled, Collection<Integer> floorsTravelled) {
        ElevatorResult er = new ElevatorResult();
        er.setDistanceTravelled(distanceTravelled);
        er.addToFloorsTravelled(floorsTravelled);
        return er;
    }
}
