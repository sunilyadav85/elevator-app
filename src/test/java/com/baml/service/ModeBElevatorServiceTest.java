package com.baml.service;

import com.baml.bean.Elevator;
import com.baml.bean.ElevatorResult;
import com.baml.bean.MoveRequest;
import com.baml.config.ElevatorTestConfig;
import com.baml.helper.ElevatorTestHelper;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.util.LinkedList;
import java.util.List;

import static com.baml.enums.ElevatorDirection.DOWN;
import static com.baml.enums.ElevatorDirection.UP;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ElevatorTestConfig.class)
public class ModeBElevatorServiceTest {

    @InjectMocks
    private ModeBElevatorService classToTest;

    @InjectMocks
    private ElevatorTestHelper elevatorTestHelper;

    @Spy
    private Elevator elevator;

    @Mock
    private ElevatorServiceHelper elevatorServiceHelper;

    @Test
    public void shouldOperate() throws Exception {
        // Given
        LinkedList<MoveRequest> allMoveRequests = elevatorTestHelper.getAllMoveRequest();

        given(elevator.getMoveRequests()).willReturn(allMoveRequests);
        given(elevator.getInitialFloor()).willReturn(3);

        given(elevatorServiceHelper.getElevatorDirection(elevator, allMoveRequests.get(0))).willReturn(UP);
        given(elevatorServiceHelper.getElevatorDirection(elevator, allMoveRequests.get(1))).willReturn(UP);
        given(elevatorServiceHelper.getElevatorDirection(elevator, allMoveRequests.get(2))).willReturn(UP);
        given(elevatorServiceHelper.getElevatorDirection(elevator, allMoveRequests.get(3))).willReturn(UP);
        given(elevatorServiceHelper.getElevatorDirection(elevator, allMoveRequests.get(4))).willReturn(DOWN);

        given(elevatorServiceHelper.getFloorsInSameDirection(3,
                Lists.newArrayList(allMoveRequests.get(0), allMoveRequests.get(1), allMoveRequests.get(2), allMoveRequests.get(3)),
                UP))
                .willReturn(Lists.newArrayList(5, 7, 8, 9, 11));
        given(elevatorServiceHelper.getFloorsInSameDirection(11, Lists.newArrayList(allMoveRequests.get(4)), DOWN)).willReturn(Lists.newArrayList(1));

        List<Integer> expectedFloorsTravelled = Lists.newArrayList(3, 5, 7, 8, 9, 11, 1);
        given(elevatorServiceHelper.getDistanceTravelled(expectedFloorsTravelled)).willReturn(18);

        // When
        ElevatorResult result = classToTest.operate(elevator);

        // Then
        ElevatorResult expectedResult = elevatorTestHelper.getElevatorResultsForModeB().get(3);
        assertThat(result.getDistanceTravelled(), equalTo(expectedResult.getDistanceTravelled()));
        assertThat(result.getFloorsTravelled(), equalTo(expectedResult.getFloorsTravelled()));

        verify(elevatorServiceHelper, times(2)).updateElevatorDirection(elevator);
    }
}