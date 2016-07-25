package com.baml.service;

import com.baml.bean.Elevator;
import com.baml.bean.ElevatorResult;
import com.baml.bean.MoveRequest;
import com.baml.config.ElevatorTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ElevatorTestConfig.class)
public class ModeAElevatorServiceTest {

    @Autowired
    @Qualifier("modeAElevatorService")
    private ElevatorService classToTest;

    @Test
    public void testOperateInput1() throws Exception {
        // Given
        Elevator elevator = new Elevator();
        elevator.setInitialFloor(10);

        List<MoveRequest> moveRequests = new LinkedList<>();
        moveRequests.add(new MoveRequest(8, 1));
        elevator.addMoveRequests(moveRequests);

        List<Integer> expectedFloorTravelled = IntStream.of(10, 8, 1).boxed().collect(Collectors.toList());

        // When
        ElevatorResult elevatorResult = classToTest.operate(elevator);

        // Then
        assertThat(elevatorResult.getDistanceTravelled(), equalTo(9));
        assertThat(expectedFloorTravelled.equals(elevatorResult.getFloorsTravelled()), equalTo(true));
    }

    @Test
    public void testOperateInput2() throws Exception {
        // Given
        Elevator elevator = new Elevator();
        elevator.setInitialFloor(9);

        List<MoveRequest> moveRequests = new LinkedList<>();
        moveRequests.add(new MoveRequest(1, 5));
        moveRequests.add(new MoveRequest(1, 6));
        moveRequests.add(new MoveRequest(1, 5));
        elevator.addMoveRequests(moveRequests);

        List<Integer> expectedFloorTravelled = IntStream.of(9, 1, 5, 1, 6, 1, 5).boxed().collect(Collectors.toList());

        // When
        ElevatorResult elevatorResult = classToTest.operate(elevator);

        // Then
        assertThat(elevatorResult.getDistanceTravelled(), equalTo(30));
        assertThat(expectedFloorTravelled.equals(elevatorResult.getFloorsTravelled()), equalTo(true));
    }

    @Test
    public void testOperateInput3() throws Exception {
        // Given
        Elevator elevator = new Elevator();
        elevator.setInitialFloor(2);

        List<MoveRequest> moveRequests = new LinkedList<>();
        moveRequests.add(new MoveRequest(4, 1));
        moveRequests.add(new MoveRequest(4, 2));
        moveRequests.add(new MoveRequest(6, 8));
        elevator.addMoveRequests(moveRequests);

        List<Integer> expectedFloorTravelled = IntStream.of(2, 4, 1, 4, 2, 6, 8).boxed().collect(Collectors.toList());

        // When
        ElevatorResult elevatorResult = classToTest.operate(elevator);

        // Then
        assertThat(elevatorResult.getDistanceTravelled(), equalTo(16));
        assertThat(expectedFloorTravelled.equals(elevatorResult.getFloorsTravelled()), equalTo(true));
    }

    @Test
    public void testOperateInput4() throws Exception {
        // Given
        Elevator elevator = new Elevator();
        elevator.setInitialFloor(3);

        List<MoveRequest> moveRequests = new LinkedList<>();
        moveRequests.add(new MoveRequest(7, 9));
        moveRequests.add(new MoveRequest(3, 7));
        moveRequests.add(new MoveRequest(5, 8));
        moveRequests.add(new MoveRequest(7, 11));
        moveRequests.add(new MoveRequest(11, 1));
        elevator.addMoveRequests(moveRequests);

        List<Integer> expectedFloorTravelled = IntStream.of(3, 7, 9, 3, 7, 5, 8, 7, 11, 1).boxed().collect(Collectors.toList());

        // When
        ElevatorResult elevatorResult = classToTest.operate(elevator);

        // Then
        assertThat(elevatorResult.getDistanceTravelled(), equalTo(36));
        assertThat(expectedFloorTravelled.equals(elevatorResult.getFloorsTravelled()), equalTo(true));
    }

    @Test
    public void testOperateInput5() throws Exception {
        // Given
        Elevator elevator = new Elevator();
        elevator.setInitialFloor(7);

        List<MoveRequest> moveRequests = new LinkedList<>();
        moveRequests.add(new MoveRequest(11, 6));
        moveRequests.add(new MoveRequest(10, 5));
        moveRequests.add(new MoveRequest(6, 8));
        moveRequests.add(new MoveRequest(7, 4));
        moveRequests.add(new MoveRequest(12, 7));
        moveRequests.add(new MoveRequest(8, 9));
        elevator.addMoveRequests(moveRequests);

        List<Integer> expectedFloorTravelled = IntStream.of(7, 11, 6, 10, 5, 6, 8, 7, 4, 12, 7, 8, 9).boxed().collect(Collectors.toList());

        // When
        ElevatorResult elevatorResult = classToTest.operate(elevator);

        // Then
        assertThat(elevatorResult.getDistanceTravelled(), equalTo(40));
        assertThat(expectedFloorTravelled.equals(elevatorResult.getFloorsTravelled()), equalTo(true));
    }

    @Test
    public void testOperateInput6() throws Exception {
        // Given
        Elevator elevator = new Elevator();
        elevator.setInitialFloor(6);

        List<MoveRequest> moveRequests = new LinkedList<>();
        moveRequests.add(new MoveRequest(1, 8));
        moveRequests.add(new MoveRequest(6, 8));
        elevator.addMoveRequests(moveRequests);

        List<Integer> expectedFloorTravelled = IntStream.of(6, 1, 8, 6, 8).boxed().collect(Collectors.toList());

        // When
        ElevatorResult elevatorResult = classToTest.operate(elevator);

        // Then
        assertThat(elevatorResult.getDistanceTravelled(), equalTo(16));
        assertThat(expectedFloorTravelled.equals(elevatorResult.getFloorsTravelled()), equalTo(true));
    }
}