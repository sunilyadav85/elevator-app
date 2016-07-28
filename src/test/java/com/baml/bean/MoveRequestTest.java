package com.baml.bean;

import com.baml.config.ElevatorTestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ElevatorTestConfig.class)
public class MoveRequestTest {

    @Test
    public void shouldThrowExceptionWhenFromFloorIsGreaterThanBuildingHeight() throws Exception {
        try {
            // Given

            // When
            new MoveRequest(14, 2);

            //Then
            Assert.fail("Exception When From Floor Is Greater Than Building Height");
        } catch (IllegalArgumentException e) {
            assertThat(e.getClass(), equalTo(IllegalArgumentException.class));
            assertThat(e.getMessage(), equalTo("FromFloor [14] or ToFloor [2] is greater than the number of floors in the building [12]."));
        }
    }

    @Test
    public void shouldThrowExceptionWhenToFloorIsGreaterThanBuildingHeight() throws Exception {
        try {
            // Given

            // When
            new MoveRequest(4, 32);

            //Then
            Assert.fail("Exception When To Floor Is Greater Than Building Height");
        } catch (IllegalArgumentException e) {
            assertThat(e.getClass(), equalTo(IllegalArgumentException.class));
            assertThat(e.getMessage(), equalTo("FromFloor [4] or ToFloor [32] is greater than the number of floors in the building [12]."));
        }
    }

    @Test
    public void shouldThrowExceptionWhenFromFloorIsLessThanGroundFloor() throws Exception {
        try {
            // Given

            // When
            new MoveRequest(-4, 2);

            //Then
            Assert.fail("Exception When From Floor Is Less Than Ground Floor");
        } catch (IllegalArgumentException e) {
            assertThat(e.getClass(), equalTo(IllegalArgumentException.class));
            assertThat(e.getMessage(), equalTo("FromFloor [-4] or ToFloor [2] cannot be less than zero."));
        }
    }

    @Test
    public void shouldThrowExceptionWhenToFloorIsLessThanGroundFloor() throws Exception {
        try {
            // Given

            // When
            new MoveRequest(3, -2);

            //Then
            Assert.fail("Exception When To Floor Is Less Than Ground Floor");
        } catch (IllegalArgumentException e) {
            assertThat(e.getClass(), equalTo(IllegalArgumentException.class));
            assertThat(e.getMessage(), equalTo("FromFloor [3] or ToFloor [-2] cannot be less than zero."));
        }
    }

    @Test
    public void shouldThrowExceptionWhenFromAndToFloorAreSame() throws Exception {
        try {
            // Given

            // When
            new MoveRequest(5, 5);

            //Then
            Assert.fail("Exception When From And To Floor Are Same");
        } catch (IllegalArgumentException e) {
            assertThat(e.getClass(), equalTo(IllegalArgumentException.class));
            assertThat(e.getMessage(), equalTo("From Floor [5] cannot be the same as To Floor [5]"));
        }
    }
}