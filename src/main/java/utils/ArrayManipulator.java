package utils;

import java.util.Arrays;
import java.util.Random;

// Contains functions for manipulating arrays.
public class ArrayManipulator
{

//  Scrambles the array items in an array of any given type.
    public static <T> T[] scrambleArray(T[] arr)
    {
        T[] newArr = Arrays.copyOf(arr, arr.length);

        for (int i = 0; i < newArr.length; i++)
        {
            swapArrItems(newArr,
                    i,
                    new Random().nextInt(newArr.length - i) + i);
        }

        return newArr;
    }


//  Swap array items. Takes an array of any given type and two indexes which
//  are to be swapped.
    public static <T> void swapArrItems(T[] arr, int index1, int index2)
    {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}
