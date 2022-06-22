package ru.vsu.cs.course1.sort;

import java.lang.reflect.Array;
import java.util.function.ToIntFunction;

public class RadixSort {
    private static <T> int getMax(T[] data, ToIntFunction<T> toIntConverter) {
        int max = toIntConverter.applyAsInt(data[0]);
        for (T value : data) {
            int intValue = toIntConverter.applyAsInt(value);
            if (intValue > max) {
                max = intValue;
            }
        }
        return max;
    }

    private static <T> void countingSort(T[] data, int exp, int digitBase, ToIntFunction<T> toIntConverter)
    {
        int size = data.length;

        T[] output =  (T[]) Array.newInstance(data.getClass().getComponentType(), size);
        int[] counts = new int[digitBase];

        for (T value : data) {
            int intValue = toIntConverter.applyAsInt(value);
            int index = (intValue / exp) % digitBase;
            counts[index]++;
        }

        for (int i = 1; i < digitBase; i++) {
            counts[i] += counts[i - 1];
        }

        for (int i = size - 1; i >= 0; i--)
        {
            int intValue = toIntConverter.applyAsInt(data[i]);
            int index = (intValue / exp) % digitBase;
            output[counts[index] - 1] = data[i];
            counts[index]--;
        }

        System.arraycopy(output, 0, data, 0, size);
    }

    public static <T> void sort(T[] data, int digitBase, ToIntFunction<T> toIntConverter)
    {
        int max = getMax(data, toIntConverter);

        for (int exp = 1; max / exp > 0; exp *= digitBase) {
            countingSort(data, exp, digitBase, toIntConverter);
        }
    }

    public static void sort(Integer[] data, int digitBase) {
        sort(data, digitBase, x -> x);
    }
}
