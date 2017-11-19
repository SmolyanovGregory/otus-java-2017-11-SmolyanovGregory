import com.google.common.collect.Lists;
import java.util.List;

/**
 * Created by Gregory Smolyanov.
 *
 * Home work 01 - maven and obfuscation/
 *
 * Creating list of 1 to 100 and output even numbers.
 */

public class Main {

  public static void main(String[] args) {
    // creating the list...
    List<Integer> numbers = Lists.newArrayList(1, 2, 3, 4, 5);

    // reversing the list...
    List<Integer> reversedNumbers = Lists.reverse(numbers);

    // output the revesed list elements...
    System.out.println("Reversed list of 1 to 5 elements:");
    for(Integer i: reversedNumbers)
      System.out.println(i);
  }
}
