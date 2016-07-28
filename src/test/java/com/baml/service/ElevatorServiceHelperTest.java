package com.baml.service;

import com.baml.bean.Elevator;
import com.baml.bean.ElevatorResult;
import com.baml.bean.MoveRequest;
import com.baml.config.ElevatorTestConfig;
import com.baml.enums.ElevatorDirection;
import com.baml.helper.ElevatorTestHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

import static com.baml.enums.ElevatorDirection.NA;
import static com.baml.enums.ElevatorDirection.UP;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ElevatorTestConfig.class)
public class ElevatorServiceHelperTest {

    @InjectMocks
    private ElevatorServiceHelper classToTest;

    @InjectMocks
    private ElevatorTestHelper elevatorTestHelper;

    @Mock
    private Elevator elevator;

    @Test
    public void shouldGetDistanceTravelled() throws Exception {
        // Given
        ElevatorResult elevatorResult = elevatorTestHelper.getElevatorResultsForModeA().get(4);

        // When
        int result = classToTest.getDistanceTravelled(elevatorResult.getFloorsTravelled());

        // Then
        assertThat(result, equalTo(elevatorResult.getDistanceTravelled()));
    }

    @Test
    public void shouldUpdateElevatorDirectionToNAWhenNoMoveRequestLeft() throws Exception {
        // Given
        given(elevator.getMoveRequests()).willReturn(new LinkedList<>());

        // When
        classToTest.updateElevatorDirection(elevator);

        // Then
        verify(elevator).setElevatorDirection(NA);
    }

    @Test
    public void shouldUpdateElevatorDirectionToUPWhenCurrentDirectionIsDOWN() throws Exception {
        // Given
        Queue<MoveRequest> moveRequests = new LinkedList<>();
        moveRequests.add(new MoveRequest(2, 1));
        given(elevator.getMoveRequests()).willReturn(moveRequests);
        given(elevator.getElevatorDirection()).willReturn(ElevatorDirection.DOWN);

        // When
        classToTest.updateElevatorDirection(elevator);

        // Then
        verify(elevator).setElevatorDirection(UP);
    }

    @Test
    public void shouldUpdateElevatorDirectionToDOWNWhenCurrentDirectionIsUP() throws Exception {
        // Given
        Queue<MoveRequest> moveRequests = new LinkedList<>();
        moveRequests.add(new MoveRequest(2, 1));
        given(elevator.getMoveRequests()).willReturn(moveRequests);
        given(elevator.getElevatorDirection()).willReturn(UP);

        // When
        classToTest.updateElevatorDirection(elevator);

        // Then
        verify(elevator).setElevatorDirection(ElevatorDirection.DOWN);
    }


    @Test
    public void shouldGetFloorsInSameDirectionWhenOneMoveRequest() throws Exception {
        // Given
        MoveRequest moveRequest = new MoveRequest(3, 5);

        // When
        Collection<Integer> result = classToTest.getFloorsInSameDirection(4, moveRequest, ElevatorDirection.UP);

        // Then
        assertThat(result, equalTo(Sets.newTreeSet(Lists.newArrayList(3, 5))));
    }

    @Test
    public void shouldRemoveDuplicatesFromFloorsInSameDirection() throws Exception {
        // Given
        List<MoveRequest> moveRequests = new ArrayList<>();
        moveRequests.add(new MoveRequest(1, 4));
        moveRequests.add(new MoveRequest(4, 6));

        // When
        Collection<Integer> result = classToTest.getFloorsInSameDirection(3, moveRequests, ElevatorDirection.UP);

        // Then
        assertThat(result, equalTo(Sets.newTreeSet(Lists.newArrayList(1, 4, 6))));
    }

    @Test
    public void shouldReverseFloorOrderWhenElevatorDirectionDown() throws Exception {
        // Given
        List<MoveRequest> moveRequests = new ArrayList<>();
        moveRequests.add(new MoveRequest(5, 1));
        moveRequests.add(new MoveRequest(8, 2));

        // When
        Collection<Integer> result = classToTest.getFloorsInSameDirection(3, moveRequests, ElevatorDirection.DOWN);

        // Then
        assertThat(result, equalTo(Sets.newTreeSet(Lists.newArrayList(8, 5, 2, 1))));
    }

    @Test
    public void shouldSkipTheFloorWhenAlreadyOnThatFloor() throws Exception {
        // Given
        List<MoveRequest> moveRequests = new ArrayList<>();
        moveRequests.add(new MoveRequest(3, 5));
        moveRequests.add(new MoveRequest(6, 8));

        // When
        Collection<Integer> result = classToTest.getFloorsInSameDirection(3, moveRequests, ElevatorDirection.UP);

        // Then
        assertThat(result, equalTo(Sets.newTreeSet(Lists.newArrayList(5, 6, 8))));
    }

    @Test
    public void shouldReturnMoveRequestDirectionWhenElevatorDirectionNA() throws Exception {
        // Given
        given(elevator.getElevatorDirection()).willReturn(NA);
        MoveRequest moveRequest = new MoveRequest(3, 5);

        //When
        ElevatorDirection elevatorDirection = classToTest.getElevatorDirection(elevator, moveRequest);

        // Then
        assertThat(elevatorDirection, equalTo(moveRequest.getElevatorDirection()));
    }

    @Test
    public void shouldReturnElevatorDirection() throws Exception {
        // Given
        given(elevator.getElevatorDirection()).willReturn(UP);
        MoveRequest moveRequest = new MoveRequest(3, 5);

        //When
        ElevatorDirection elevatorDirection = classToTest.getElevatorDirection(elevator, moveRequest);

        // Then
        assertThat(elevatorDirection, equalTo(UP));
    }
}