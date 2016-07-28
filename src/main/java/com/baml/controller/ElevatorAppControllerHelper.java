package com.baml.controller;

import com.baml.bean.Elevator;
import com.baml.bean.ElevatorResult;
import com.baml.bean.MoveRequest;
import com.baml.bean.MoveRequestList;
import com.baml.enums.ElevatorMode;
import com.baml.service.ElevatorContext;
import com.baml.service.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.baml.enums.ElevatorMode.A;
import static com.baml.enums.ElevatorMode.B;

@Component
public class ElevatorAppControllerHelper {

    @Autowired
    private ElevatorContext elevatorContext;

    @Autowired
    private Elevator elevator;

    @Autowired
    @Qualifier("modeAElevatorService")
    private ElevatorService modeAElevatorService;

    @Autowired
    @Qualifier("modeBElevatorService")
    private ElevatorService modeBElevatorService;

    protected Collection<ElevatorResult> processRequest(String fileName, ElevatorMode elevatorMode) {
        initializeElevatorContext(elevatorMode);
        List<ElevatorResult> elevatorResults = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(moveRequestRow -> {
                MoveRequestList moveRequestList = getMoveRequestList(moveRequestRow);
                elevator.addMoveRequests(moveRequestList.getMoveRequests());
                elevator.setInitialFloor(moveRequestList.getInitialFloor());
                elevatorResults.add(elevatorContext.executeElevatorStrategy(elevator));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elevatorResults;
    }

    private MoveRequestList getMoveRequestList(String moveRequestRow) {

        MoveRequestList moveRequestList = new MoveRequestList();

        String[] initialFloorMoveRequestArray = moveRequestRow.split(":");
        moveRequestList.setInitialFloor(Integer.parseInt(initialFloorMoveRequestArray[0]));

        String[] moveRequestArray = initialFloorMoveRequestArray[1].split(",");

        for (String moveRequest : moveRequestArray) {
            String[] moveRequestFloors = moveRequest.split("-");
            moveRequestList.addToMoveRequests(new MoveRequest(Integer.parseInt(moveRequestFloors[0]), Integer.parseInt(moveRequestFloors[1])));
        }
        return moveRequestList;
    }

    private void initializeElevatorContext(ElevatorMode elevatorMode) {
        if (A == elevatorMode) {
            elevatorContext.setElevatorService(modeAElevatorService);
        } else if (B == elevatorMode) {
            elevatorContext.setElevatorService(modeBElevatorService);
        } else {
            String errorMsg = String.format("Invalid Mode [%s] Supplied", elevatorMode);
            throw new IllegalArgumentException(errorMsg);
        }
    }
}
