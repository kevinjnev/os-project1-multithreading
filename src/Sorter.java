import java.util.Arrays;

/**
 * This is the class for the thread that sorts a half of an array.
 * 
 * @param data The array to sort.
 * @param start The start index of the half to sort. (inclusive)
 * @param end The end index of the half to sort. (exclusive)
 */
class Sorter implements Runnable {
    private final int[] data;
    private final int start;
    private final int end;

    Sorter(int[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    public void run() {
        //I just use the built in sort method for arrays, which is a type of quicksort.
        Arrays.sort(data, start, end);
    }
}
