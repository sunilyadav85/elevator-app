package com.baml.service;

import com.baml.bean.Elevator;
import com.baml.bean.ElevatorResult;
import org.springframework.stereotype.Service;

@Service
public class ElevatorContext {

    private ElevatorService elevatorService;

    public void setElevatorService(ElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }

    public ElevatorResult executeElevatorStrategy(Elevator elevator) {
        return elevatorService.operate(elevator);
    }
}
