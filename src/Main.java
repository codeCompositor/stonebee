import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String args[]) {
        List<Integer> indexes = new ArrayList<>();
        indexes.add(0);
        indexes.add(2);
        indexes.add(6);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 7; ++i)
            strings.add(Integer.toString(7-i));
        List<String> l = indexes.stream().map(strings::get).collect(Collectors.toList());
        l.forEach(System.out::println);
    }
}
