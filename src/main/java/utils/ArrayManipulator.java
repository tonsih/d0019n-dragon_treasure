package utils;

import java.util.Arrays;
import java.util.Random;

/**
 * This class contains class methods used to manipulate arrays.
 */
public class ArrayManipulator
{
    /**
     * A private no-arg constructor for the class. Prevents initiation of an
     * instance.
     */
    private ArrayManipulator()
    {
    }

    /**
     * Returns a new scrambled array, i.e. mixes up the contents of the provided
     * array randomly and returns the mixed up array.
     *
     * @param arr Array which contents are to be scrambled.
     * @return A new scrambled array.
     * @param <T> Type of elements contained in the array.
     */
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

    /**
     * Swaps the elements in the provided indexes to respective index.
     *
     * @param arr Array where the elements are to be swapped.
     * @param index1 Index of the first element to be swapped with the second.
     * @param index2 Index of the second element to be swapped with the first.
     * @param <T> Type of element which the array contains.
     */
    public static <T> void swapArrItems(T[] arr, int index1, int index2)
    {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}