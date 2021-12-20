import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Parser parser             = new Parser("input.txt");
        ArrayList<char[]> numbers = parser.getNumbers();
        Solution solution         = new Solution(numbers);

        solution.solve();
    }
}