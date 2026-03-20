/**
 * This is the class for the thread that merges the two sorted halves of an array into a single sorted array.
 * 
 * @param data The array with the two sorted halves that need to be merged.
 * @param mergedArray The array to merge the two sorted halves into.
 * @param leftHalfStart The start index of the left half.
 * @param leftHalfEnd The end index of the left half.
 * @param rightHalfStart The start index of the right half. (which is also the end of the left half)
 * @param rightHalfEnd The end index of the right half.
 */
class Merger implements Runnable {
    private final int[] data;
    private final int[] mergedArray;
    private final int leftHalfStart;
    private final int leftHalfEnd;
    private final int rightHalfStart;
    private final int rightHalfEnd;

    Merger(int[] data, int[] mergedArray, int leftHalfStart, int leftHalfEnd, int rightHalfStart, int rightHalfEnd) {
        this.data = data;
        this.mergedArray = mergedArray;
        this.leftHalfStart = leftHalfStart;
        this.leftHalfEnd = leftHalfEnd;
        this.rightHalfStart = rightHalfStart;
        this.rightHalfEnd = rightHalfEnd;
    }

    public void run() {
        // i walks through the left half, j walks through the right half.
        int i = leftHalfStart;
        // k is the write position in the output merged array.
        int j = rightHalfStart;
        int k = 0;

        // While both halves of the array have integers left, it picks the smaller one to add to the merged array.
        while (i < leftHalfEnd && j < rightHalfEnd) {
            if (data[i] <= data[j]) {
                mergedArray[k] = data[i];
                k++;
                i++;
            } 
            else {
                mergedArray[k] = data[j];
                k++;
                j++;
            }
        }
        // When one half is finished, it adds the rest of the other half to the merged array since they are already sorted.
        while (i < leftHalfEnd) {
            mergedArray[k] = data[i];
            k++;
            i++;
        }
        while (j < rightHalfEnd) {
            mergedArray[k] = data[j];
            k++;
            j++;
        }
    }
}
