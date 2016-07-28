package com.baml.controller;

import com.baml.bean.Elevator;
import com.baml.bean.ElevatorResult;
import com.baml.bean.MoveRequest;
import com.baml.config.ElevatorTestConfig;
import com.baml.enums.ElevatorMode;
import com.baml.helper.ElevatorTestHelper;
import com.baml.service.ElevatorContext;
import com.baml.service.ElevatorService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ElevatorTestConfig.class)
public class ElevatorAppControllerHelperTest {

    @InjectMocks
    private ElevatorAppControllerHelper classToTest;

    @InjectMocks
    private ElevatorTestHelper elevatorTestHelper;

    @Mock
    private ElevatorContext elevatorContext;

    @Mock
    private ElevatorResult elevatorResult;

    @Mock
    private Elevator elevator;

    @Mock
    @Qualifier("modeAElevatorService")
    private ElevatorService modeAElevatorService;

    @Mock
    @Qualifier("modeBElevatorService")
    private ElevatorService modeBElevatorService;

    @Captor
    private ArgumentCaptor<Collection<MoveRequest>> argumentCaptor;

    @Test
    public void shouldProcessRequestForModeA() throws Exception {
        // Given
        ElevatorMode elevatorMode = ElevatorMode.A;
        String fileName = Paths.get(ClassLoader.getSystemResource("ElevatorRequestFile.txt").toURI()).toString();
        List<ElevatorResult> elevatorResults = elevatorTestHelper.getElevatorResultsForModeA();

        given(elevatorContext.executeElevatorStrategy(elevator)).
                willReturn(elevatorResults.get(0),
                        elevatorResults.get(1),
                        elevatorResults.get(2),
                        elevatorResults.get(3),
                        elevatorResults.get(4),
                        elevatorResults.get(5));

        // When
        Collection<ElevatorResult> result = classToTest.processRequest(fileName, elevatorMode);

        // Then
        verify(elevatorContext).setElevatorService(modeAElevatorService);
        verify(elevator, times(6)).addMoveRequests(argumentCaptor.capture());

        verifyElevatorInitialization();

        verify(elevatorContext, times(6)).executeElevatorStrategy(elevator);
        assertThat(result, equalTo(elevatorTestHelper.getElevatorResultsForModeA()));
    }

    @Test
    public void shouldProcessRequestForModeB() throws Exception {
        // Given
        ElevatorMode elevatorMode = ElevatorMode.B;
        String fileName = Paths.get(ClassLoader.getSystemResource("ElevatorRequestFile.txt").toURI()).toString();
        List<ElevatorResult> elevatorResults = elevatorTestHelper.getElevatorResultsForModeB();


        given(elevatorContext.executeElevatorStrategy(elevator)).
                willReturn(elevatorResults.get(0),
                        elevatorResults.get(1),
                        elevatorResults.get(2),
                        elevatorResults.get(3),
                        elevatorResults.get(4),
                        elevatorResults.get(5));

        // When
        Collection<ElevatorResult> result = classToTest.processRequest(fileName, elevatorMode);

        // Then
        verify(elevatorContext).setElevatorService(modeBElevatorService);
        verify(elevator, times(6)).addMoveRequests(argumentCaptor.capture());

        verifyElevatorInitialization();

        verify(elevatorContext, times(6)).executeElevatorStrategy(elevator);
        assertThat(result, equalTo(elevatorTestHelper.getElevatorResultsForModeB()));
    }

    private void verifyElevatorInitialization() {
        List<Collection<MoveRequest>> allMoveRequests = argumentCaptor.getAllValues();
        InOrder order = inOrder(elevator);

        assertThat(allMoveRequests.get(0), equalTo(Lists.newArrayList(new MoveRequest(8, 1))));
        order.verify(elevator).setInitialFloor(10);

        assertThat(allMoveRequests.get(1), equalTo(Lists.newArrayList(new MoveRequest(1, 5), new MoveRequest(1, 6), new MoveRequest(1, 5))));
        order.verify(elevator).setInitialFloor(9);

        assertThat(allMoveRequests.get(2), equalTo(Lists.newArrayList(new MoveRequest(4, 1), new MoveRequest(4, 2), new MoveRequest(6, 8))));
        order.verify(elevator).setInitialFloor(2);

        assertThat(allMoveRequests.get(3), equalTo(Lists.newArrayList(new MoveRequest(7, 9), new MoveRequest(3, 7), new MoveRequest(5, 8), new MoveRequest(7, 11), new MoveRequest(11, 1))));
        order.verify(elevator).setInitialFloor(3);

        assertThat(allMoveRequests.get(4), equalTo(Lists.newArrayList(new MoveRequest(11, 6), new MoveRequest(10, 5), new MoveRequest(6, 8), new MoveRequest(7, 4), new MoveRequest(12, 7), new MoveRequest(8, 9))));
        order.verify(elevator).setInitialFloor(7);

        assertThat(allMoveRequests.get(5), equalTo(Lists.newArrayList(new MoveRequest(1, 8), new MoveRequest(6, 8))));
        order.verify(elevator).setInitialFloor(6);
        verifyNoMoreInteractions(elevator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenInvalidElevatorMode() throws Exception {
        // Given
        ElevatorMode elevatorMode = null;

        // When
        classToTest.processRequest("file.txt", elevatorMode);

        // Then
    }
}