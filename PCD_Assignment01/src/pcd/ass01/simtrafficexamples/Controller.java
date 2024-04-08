package pcd.ass01.simtrafficexamples;

/**
 * Controller part of the application - passive part.
 *
 * @author aricci
 *
 */
public class Controller implements ViewListener {

    private Synch synch;
    private SynchronizedFlag stopSynchronizedFlag;

    public Controller(Synch synch, SynchronizedFlag stopSynchronizedFlag) {
        this.synch = synch;
        this.stopSynchronizedFlag = stopSynchronizedFlag;
    }

    public synchronized void started(int nStep) {
        stopSynchronizedFlag.reset();
        synch.notifyStarted(nStep);
    }

    public synchronized void stopped() {
        stopSynchronizedFlag.set();
    }

}
