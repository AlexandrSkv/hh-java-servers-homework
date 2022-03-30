package App;

public class Counter {

    private int counter;

    Counter() {
        counter = 0;
    }

    public int getCounter() {
        return counter;
    }

    public synchronized void increaseCounter() {
        counter += 1;
    }

    public synchronized void decreaseCounter(int value) {
        counter -= value;
    }

    public synchronized void deleteCounter() {
        counter = 0;
    }

}
