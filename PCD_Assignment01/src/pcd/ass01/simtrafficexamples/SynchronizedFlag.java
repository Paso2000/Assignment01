package pcd.ass01.simtrafficexamples;

public class SynchronizedFlag {

    private boolean flag;

    public SynchronizedFlag() {
        flag = false;
    }

    public synchronized void reset() {
        flag = false;
    }

    public synchronized void set() {
        flag = true;
    }

    public synchronized boolean getValue() {
        return flag;
    }
}