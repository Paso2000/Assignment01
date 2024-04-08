package pcd.ass01.simtrafficexamples;

public class Synch {

    private boolean started;
    private int steps;

    public Synch(){
        started = false;
    }

    public synchronized int waitStart() {
        while (!started) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        started = false;
        return steps;
    }

    public synchronized void notifyStarted(int steps) {
        started = true;
        this.steps = steps;
        notifyAll();
    }

}
