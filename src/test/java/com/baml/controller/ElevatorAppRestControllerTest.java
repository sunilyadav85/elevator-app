package com.baml.controller;

import com.baml.bean.ElevatorResult;
import com.baml.config.ElevatorTestConfig;
import com.baml.helper.ElevatorTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ElevatorTestConfig.class)
public class ElevatorAppRestControllerTest {

    @Autowired
    private ElevatorAppController classToTest;

    @Autowired
    private ElevatorTestHelper elevatorTestHelper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(classToTest)
                .build();
    }

    @Test
    public void shouldProcessRequestForModeA() throws Exception {
        // Given
        String elevatorMode = "a";
        String fileName = Paths.get(ClassLoader.getSystemResource("ElevatorRequestFile.txt").toURI()).toString();
        String url = "/elevator/result?fileName=".concat(fileName).concat("&elevatorMode=").concat(elevatorMode);

        List<ElevatorResult> expectedResults = elevatorTestHelper.getElevatorResultsForModeA();

        // When
        classToTest.process(fileName, elevatorMode);

        // Then
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].distanceTravelled").value(expectedResults.get(0).getDistanceTravelled()))
                .andExpect(jsonPath("$[0].floorsTravelled", equalTo(expectedResults.get(0).getFloorsTravelled())))
                .andExpect(jsonPath("$[1].distanceTravelled").value(expectedResults.get(1).getDistanceTravelled()))
                .andExpect(jsonPath("$[1].floorsTravelled", equalTo(expectedResults.get(1).getFloorsTravelled())))
                .andExpect(jsonPath("$[2].distanceTravelled").value(expectedResults.get(2).getDistanceTravelled()))
                .andExpect(jsonPath("$[2].floorsTravelled", equalTo(expectedResults.get(2).getFloorsTravelled())))
                .andExpect(jsonPath("$[3].distanceTravelled").value(expectedResults.get(3).getDistanceTravelled()))
                .andExpect(jsonPath("$[3].floorsTravelled", equalTo(expectedResults.get(3).getFloorsTravelled())))
                .andExpect(jsonPath("$[4].distanceTravelled").value(expectedResults.get(4).getDistanceTravelled()))
                .andExpect(jsonPath("$[4].floorsTravelled", equalTo(expectedResults.get(4).getFloorsTravelled())))
                .andExpect(jsonPath("$[5].distanceTravelled").value(expectedResults.get(5).getDistanceTravelled()))
                .andExpect(jsonPath("$[5].floorsTravelled", equalTo(expectedResults.get(5).getFloorsTravelled())));
    }

    @Test
    public void shouldProcessRequestForModeB() throws Exception {
        // Given
        String elevatorMode = "b";
        String fileName = Paths.get(ClassLoader.getSystemResource("ElevatorRequestFile.txt").toURI()).toString();
        String url = "/elevator/result?fileName=".concat(fileName).concat("&elevatorMode=").concat(elevatorMode);

        List<ElevatorResult> expectedResults = elevatorTestHelper.getElevatorResultsForModeB();

        // When
        classToTest.process(fileName, elevatorMode);

        // Then
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].distanceTravelled").value(expectedResults.get(0).getDistanceTravelled()))
                .andExpect(jsonPath("$[0].floorsTravelled", equalTo(expectedResults.get(0).getFloorsTravelled())))
                .andExpect(jsonPath("$[1].distanceTravelled").value(expectedResults.get(1).getDistanceTravelled()))
                .andExpect(jsonPath("$[1].floorsTravelled", equalTo(expectedResults.get(1).getFloorsTravelled())))
                .andExpect(jsonPath("$[2].distanceTravelled").value(expectedResults.get(2).getDistanceTravelled()))
                .andExpect(jsonPath("$[2].floorsTravelled", equalTo(expectedResults.get(2).getFloorsTravelled())))
                .andExpect(jsonPath("$[3].distanceTravelled").value(expectedResults.get(3).getDistanceTravelled()))
                .andExpect(jsonPath("$[3].floorsTravelled", equalTo(expectedResults.get(3).getFloorsTravelled())))
                .andExpect(jsonPath("$[4].distanceTravelled").value(expectedResults.get(4).getDistanceTravelled()))
                .andExpect(jsonPath("$[4].floorsTravelled", equalTo(expectedResults.get(4).getFloorsTravelled())))
                .andExpect(jsonPath("$[5].distanceTravelled").value(expectedResults.get(5).getDistanceTravelled()))
                .andExpect(jsonPath("$[5].floorsTravelled", equalTo(expectedResults.get(5).getFloorsTravelled())));
    }
}