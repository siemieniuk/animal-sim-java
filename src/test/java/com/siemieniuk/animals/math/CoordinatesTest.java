package com.siemieniuk.animals.math;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinatesTest {

    @DisplayName("Equal coordinates")
    @Nested
    class EqualCoordinatesTest {
        @DisplayName("Manhattan distance")
        @ParameterizedTest
        @MethodSource("provideEqualParameters")
        void testManhattanDistance(Coordinates first, Coordinates second) {
            assertEquals(0, first.getManhattanDistanceTo(second));
            assertEquals(0, second.getManhattanDistanceTo(first));
        }

        @DisplayName("Equality")
        @ParameterizedTest
        @MethodSource("provideEqualParameters")
        void testEquals(Coordinates first, Coordinates second) {
            assertEquals(first, second);
            assertEquals(second, first);
        }

        private static Stream<Arguments> provideEqualParameters() {
            return Stream.of(Arguments.of(new Coordinates(1, 1), new Coordinates(1, 1)),
                             Arguments.of(new Coordinates(0,0), new Coordinates(0, 0)),
                             Arguments.of(new Coordinates(-1, -1), new Coordinates(-1, -1)));
        }
    }

    @DisplayName("Non-equal parameters")
    @Nested
    class NonEqualParametersTest {
        @DisplayName("Manhattan distance")
        @ParameterizedTest
        @MethodSource("provideDifferentParameters")
        void different_testManhatanDistance(Coordinates first, Coordinates second, int expectedDistance) {
            assertEquals(expectedDistance, first.getManhattanDistanceTo(second));
            assertEquals(expectedDistance, second.getManhattanDistanceTo(first));
        }


        @DisplayName("Equality")
        @ParameterizedTest
        @MethodSource("provideDifferentParameters")
        void different_testEquals(Coordinates first, Coordinates second) {
            assertNotEquals(first, second);
            assertNotEquals(second, first);
        }

        private static Stream<Arguments> provideDifferentParameters() {
            return Stream.of(Arguments.of(new Coordinates(2, 7), new Coordinates(7, 2), 10),
                             Arguments.of(new Coordinates(2, 3), new Coordinates(-3, -2), 10),
                             Arguments.of(new Coordinates(-2, -7), new Coordinates(-7, -2), 10));
        }
    }

    @DisplayName("Test if setting dimensions works exactly once")
    @Test
    void setMaxDimensions() {
        assertDoesNotThrow(() -> Coordinates.setMaxDimensions(2, 5));
        assertThrows(IllegalArgumentException.class, () -> Coordinates.setMaxDimensions(10, 24));

    }
}
