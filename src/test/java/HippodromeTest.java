import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;

public class HippodromeTest {
    @Test
     void WhenHorsesIsNull_ShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }
    @Test
     void WhenHorsesIsNull_ShouldShowCorrectMessage(){
        try{
            new Hippodrome(null);
        }
        catch (IllegalArgumentException e){
            Assertions.assertEquals("Horses cannot be null.", e.getMessage());
        }
    }
    @Test
    void WhenHorsesIsEmpty_ShouldThrowIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }
    @Test
    void WhenHorsesIsEmpty_ShouldShowCorrectMessage(){
        try{
            new Hippodrome(new ArrayList<>());
        }
        catch(IllegalArgumentException e){
            Assertions.assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }
    @Test
    void getHorses_ShouldReturnRightHorsesIntTheSameOrder(){
        List<Horse> horses = new ArrayList<>();
        for(int number = 1; number <= 30; number++){
            horses.add(new Horse("Horse №" + number, number, number));
        }
        Assertions.assertEquals(horses, new Hippodrome(horses).getHorses());
    }
    @Test
    void move_ShouldMoveAllHorses(){
        List<Horse> horses = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            horses.add(Mockito.mock(Horse.class));
        }
        new Hippodrome(horses).move();
        for(Horse horse : horses) {
            Mockito.verify(horse).move();
        }
    }
    @Test
    void getWinner_ShouldReturnHorseWithBiggestDistance(){
        List<Horse> horses = new ArrayList<>();
        for(int i = 1; i <= 7; i++){
            horses.add(new Horse("Horse №" + i, 0.4, i));
        }
        Assertions.assertEquals(horses.get(6), new Hippodrome(horses).getWinner());
    }
}