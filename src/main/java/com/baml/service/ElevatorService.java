package com.baml.service;

import com.baml.bean.Elevator;
import com.baml.bean.ElevatorResult;

public interface ElevatorService {

    ElevatorResult operate(Elevator elevator);
}
