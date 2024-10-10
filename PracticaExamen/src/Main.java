import java.util.List;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class Main {
    private static void testcase(){
        BiFunction<Integer, List<Function<Integer,Integer>>,Integer>  //T y R= T es lo que le entra y R lo que retorna
        (number,operations)-> operations.stream().reduce(acc,fn) -> fn(acc),x);
    }

    public static void main(String[] args) {
       testcase();
    }
}