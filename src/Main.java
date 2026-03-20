
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The main class handles the input/output as well as managing the threads for sorting and merging.
 */
public class Main {
    // I used a default file in case there is no file name inputted.
    private static final String DEFAULT_FILENAME = "numbers.txt";

    private static int[] originalArray;
    private static int[] sortedArray;

    /**
     * This is what gets a file name input from the user and makes an array of the integers in the file.
     * Uses a default for numbers.txt if there is no input.
     * 
     * @return The array of integers from the file.
     */
    private static int[] readIntsFromFile() throws IOException {
        System.out.println("Enter input file name or leave blank for default: ");
        
        //The scanner gets an input from the user. If there is no input the default is used
        //I check that the file is found just in case, then the file is read to create an array.
        Scanner stdin = new Scanner(System.in);
        String fileName;

        if (!stdin.hasNextLine()) {
            fileName = DEFAULT_FILENAME;
        } else {
            fileName = stdin.nextLine().trim();
        }
        if (fileName.isEmpty()) {
            fileName = DEFAULT_FILENAME;
        }
        //I don't need System.in after this at all
        stdin.close();

        File inputFile = new File(fileName);
        if (!inputFile.exists() || !inputFile.isFile()) {
            throw new IOException("Input file not found: " + fileName);
        }

        System.out.println("Reading from: " + fileName);

        //Using an array list to store them at first makes it easier for me to just create an array of the right size later.
        List<Integer> vals = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(inputFile)) {
            while (fileScanner.hasNextInt()) {
                vals.add(fileScanner.nextInt());
            }
        }
        catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            return new int[0];
        }

        //This makes the array that is returned as the input array of numbers.
        int[] inputArray = new int[vals.size()];
        for (int i = 0; i < vals.size(); i++) {
            inputArray[i] = vals.get(i);
        }
        return inputArray;
    }

    /**
     * 
     * The main method first stores the input array of numbers from a file and creates an empty array of the same size.
     * Then it prints the unsorted array.
     * 
     * The multithreading part starts by creating two sorter threads that sort half of the array.
     * After those two threads finish and are joined, the merger thread is created and merges the two sorted halves.
     * 
     * When the merger thread finishes, the sorted array is printed.
     */
    public static void main(String[] args) throws Exception {
        originalArray = readIntsFromFile();
        sortedArray = new int[originalArray.length];

        System.out.println("Unsorted: " + Arrays.toString(originalArray));

        int mid = originalArray.length / 2;

        //The full original array, start index, and end index respectively are passed to each sorter thread.
        Thread t1 = new Thread(new Sorter(originalArray, 0, mid), "sortingThread1");
        Thread t2 = new Thread(new Sorter(originalArray, mid, originalArray.length), "sortingThread2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        //The merge thread is given the original array which now has two sorted halves, and the empty array to merge them into.
        //The start and ends halves are also given to it.
        Thread mergeThread = new Thread(new Merger(originalArray, sortedArray, 0, mid, mid, originalArray.length), "mergingThread");
        mergeThread.start();
        mergeThread.join();

        System.out.println("Sorted:   " + Arrays.toString(sortedArray));
    }

}
