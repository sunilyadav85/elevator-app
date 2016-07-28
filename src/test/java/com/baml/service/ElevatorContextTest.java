package com.baml.service;

import com.baml.bean.Elevator;
import com.baml.bean.ElevatorResult;
import com.baml.config.ElevatorTestConfig;
import com.baml.helper.ElevatorTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ElevatorTestConfig.class)
public class ElevatorContextTest {

    @InjectMocks
    private ElevatorContext classToTest;

    @InjectMocks
    private ElevatorTestHelper elevatorTestHelper;

    @Mock
    private Elevator elevator;

    @Mock
    private ElevatorService elevatorService;

    @Test
    public void shouldExecuteElevatorStrategy() throws Exception {
        // Given
        ElevatorResult expectedElevatorResult = elevatorTestHelper.getElevatorResultsForModeA().get(0);
        given(elevatorService.operate(elevator)).willReturn(expectedElevatorResult);

        // When
        ElevatorResult result = classToTest.executeElevatorStrategy(elevator);

        // Then
        verify(elevatorService).operate(elevator);
        assertThat(result, equalTo(expectedElevatorResult));
    }
}