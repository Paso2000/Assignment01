package pcd.ass01.simtrafficexamples;

public class NoGuiTest {
    public static void main(String[] args) {
        int nSteps = 100;
        Synch synch = new Synch();
        SynchronizedFlag stopSynchronizedFlag = new SynchronizedFlag();
        var simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(stopSynchronizedFlag, synch, 100);
        simulation.setup();
        log("Running the simulation: " + 100 + " cars, for " + nSteps + " steps ...");

       // simulation.run(nSteps);

        long d = simulation.getSimulationDuration();
        log("Completed in " + d + " ms - average time per step: " + simulation.getAverageTimePerCycle() + " ms");
    }

    private static void log(String msg) {
        System.out.println("[ SIMULATION ] " + msg);
    }
}
