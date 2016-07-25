package com.baml.controller;

import com.baml.bean.Elevator;
import com.baml.bean.ElevatorResult;
import com.baml.bean.MoveRequest;
import com.baml.service.ElevatorContext;
import com.baml.service.ElevatorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class ElevatorAppControllerHelper {

    @Autowired
    private ElevatorContext elevatorContext;

    private static final Character MODE_A = 'A';
    private static final Character MODE_B = 'B';

    @Autowired
    @Qualifier("modeAElevatorService")
    private ElevatorService modeAElevatorService;

    @Autowired
    @Qualifier("modeBElevatorService")
    private ElevatorService modeBElevatorService;

    protected void processRequest(String fileName, char mode) {
        initializeElevatorContext(mode);
        Elevator elevator = new Elevator();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(rowOfMoveRequest -> getElevatorRequestMap(rowOfMoveRequest).forEach((k, v) -> {
                elevator.addMoveRequests(k);
                elevator.setInitialFloor(v);
                ElevatorResult elevatorResult = elevatorContext.executeElevatorStrategy(elevator);

                printResult(elevatorResult);
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Map<List<MoveRequest>, Integer> getElevatorRequestMap(String rowOfMoveRequest) {

        Map<List<MoveRequest>, Integer> elevatorRequestMap = new LinkedHashMap<>();
        String[] initialFloorMoveRequestArray = rowOfMoveRequest.split(":");

        int initialFloor = Integer.parseInt(initialFloorMoveRequestArray[0]);
        String[] moveRequestArray = initialFloorMoveRequestArray[1].split(",");
        List<MoveRequest> moveRequests = new LinkedList<>();


        for (String moveRequest : moveRequestArray) {
            String[] moveRequestFloors = moveRequest.split("-");
            moveRequests.add(new MoveRequest(Integer.parseInt(moveRequestFloors[0]), Integer.parseInt(moveRequestFloors[1])));
        }
        elevatorRequestMap.put(moveRequests, initialFloor);
        return elevatorRequestMap;
    }

    protected void printResult(ElevatorResult elevatorResult) {
        System.out.print(StringUtils.join(elevatorResult.getFloorsTravelled(), " "));
        System.out.println(String.format(" (%d)", elevatorResult.getDistanceTravelled()));
    }

    protected void initializeElevatorContext(char mode) {
        if (Character.toUpperCase(mode) == MODE_A) {
            elevatorContext.setElevatorService(modeAElevatorService);
        } else if (Character.toUpperCase(mode) == MODE_B) {
            elevatorContext.setElevatorService(modeBElevatorService);
        } else {
            String errorMsg = String.format("Invalid Mode [%c] Supplied", mode);
            throw new IllegalArgumentException(errorMsg);
        }
    }
}
