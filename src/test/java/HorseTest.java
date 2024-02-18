import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

public class HorseTest {

    @Test
    void WhenNameOfHorseIsNull_ShouldThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 3.2, 2.8));
    }

    @Test
    void WhenNameOfHorseIsNull_ShouldShowCorrectMessage() {
        try {
            Horse horse = new Horse(null, anyDouble(), anyDouble());
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "", "\t", "\n"})
    public void WhenNameIsBlank_ShouldThrowIllegalArgumentException(String name) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(name, anyDouble(), anyDouble()));
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "", "\t", "\n"})
    public void WhenNameIsBlank_ShouldShowCorrectMessage(String name) {
        try {
            new Horse(name, anyDouble(), anyDouble());
        } catch (Exception e) {
            Assertions.assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @Test
    void WhenSpeedOfHorseIsNegative_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(anyString(), anyDouble() * -1, anyDouble()));
    }

    @Test
    void WhenSpeedOfHorseIsNegative_ShouldShowCorrectMessage() {
        try {
            Horse horse = new Horse("someString", anyDouble() * -1, anyDouble());
        } catch (IllegalArgumentException e) {
            String message = "Speed cannot be negative.";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    void WhenDistanceOfHorseIsNegative_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(anyString(), anyDouble(), anyDouble() * -1));
    }

    @Test
    void WhenDistanceOfHorseIsNegative_ShouldShowCorrectMessage() {
        try {
            Horse horse = new Horse("someName", 3.6, -3.7);
        } catch (IllegalArgumentException e) {
            String message = "Distance cannot be negative.";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    void getName_ShouldReturnTheRightName() {
        Horse horse = new Horse("name", 3.3, 2.7);
        assertEquals("name", horse.getName());
    }

    @Test
    void getSpeed_ShouldReturnTheRightSpeed() {
        Horse horse = new Horse("someName", 3.2, 2.8);
        assertEquals(3.2, horse.getSpeed());
    }

    @Test
    void getDistance_ShouldReturnTheRightDistance() {
        Horse horse = new Horse("someName", 3.2, 2.8);
        assertEquals(2.8, horse.getDistance());
    }

    @Test
    void getDistance_WithoutDistanceParameter_ShouldReturnZero() {
        Horse horse = new Horse("someName", anyDouble());
        assertEquals(0, horse.getDistance());
    }

    @Test
    void move_WithCorrectParameters_ShouldCallGetRandomMethod() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("name", 3.2, 2.3).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 3.1, 3.2, 9.1, 4.1, 2.4, 2.7, 4.3, 8.3})
    void move_ShouldCalculateDistanceWithCorrectFormula(double randomDouble) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)){
            Horse horse = new Horse("name", anyDouble(), anyDouble());
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomDouble);
            horse.move();
            assertEquals(horse.getDistance() + horse.getSpeed() * randomDouble, horse.getDistance());
        }
    }
}