package explorer;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

import static java.util.Collections.*;

public class Test {
    private static int[] resultArray;
    private static String current;

    public static void main(String[] args) {
        int[] array = {8, 9, 9};
        System.out.println(Arrays.toString(plusOne(array)));
    }

    private static int[] plusOne(int[] arr) {
        for (int i = arr.length - 1; i >= 0 ; i--) {
            if (arr[i] < 9) {
                arr[i]++;
                return arr;
            }
            arr[i] = 0;
        }
        int[] resultArray = new int[arr.length + 1];
        resultArray[0] = 1;

        return resultArray;
    }

}
