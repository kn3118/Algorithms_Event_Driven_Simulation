package utils;

public class MinPriorityQueue<T extends Comparable<T>> {

    private T[] priorityQueue;
    private int sizeArray;
    /**
     * Creates an empty queue.
     */
    public MinPriorityQueue(int capacity) {
        // Using 1-based index, hence capacity + 1
        priorityQueue = (T[]) new Comparable[capacity + 1];
        sizeArray = 0;
    }

    public MinPriorityQueue() {
        // Default capacity of 1
        this(10);
    }

    /**
     * Returns the number of elements currently in the queue.
     */
    public int size() {
        return sizeArray;
    }
    
    /**
     * Adds elem to the queue.
     */
    public void add(T elem) {
        if (sizeArray == priorityQueue.length - 1) {
            resizeArray(2 * priorityQueue.length);
        }
        priorityQueue[++sizeArray] = elem;
        swim(sizeArray);
    }

    /**
     * Removes, and returns, the element at the front of the queue.
     */
    public T remove() {
        T min = priorityQueue[1];
        swap(1, sizeArray--);
        priorityQueue[sizeArray + 1] = null;
        if ((sizeArray > 0) && (sizeArray == (priorityQueue.length - 1) / 4)) {
            resizeArray(priorityQueue.length / 2);
        }
        sink(1);
        return min;
    }

    /**
     * Returns true if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return sizeArray == 0;
    }

    /**
     * Helper functions
     */
    private void swim(int index) {
        while (index > 1 && greater(index / 2, index)) {
            swap(index, index / 2);
            index = index / 2;
        }
    }

    private void sink(int index) {
        while (2 * index <= sizeArray) {
            int child = 2 * index;
            if (child < sizeArray && greater(child, child + 1)) {
                child++;
            }
            if (!greater(index, child)) {
                break;
            }
            swap(index, child);
            index = child;
        }
    }

    private boolean greater(int x, int y) {
        return priorityQueue[x].compareTo(priorityQueue[y]) > 0;
    }

    private void swap(int x, int y) {
        T tmp = priorityQueue[x];
        priorityQueue[x] = priorityQueue[y];
        priorityQueue[y] = tmp;
    }

    private void resizeArray(int new_capacity) {
        assert new_capacity > sizeArray;
        T[] tmp = (T[]) new Comparable[new_capacity];
        for (int i = 1; i <= sizeArray; i++) {
            tmp[i] = priorityQueue[i];
        }
        priorityQueue = tmp;
    }

}
