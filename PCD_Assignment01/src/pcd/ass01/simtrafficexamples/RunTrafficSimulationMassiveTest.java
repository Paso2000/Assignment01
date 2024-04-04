package pcd.ass01.simtrafficexamples;

public class RunTrafficSimulationMassiveTest {


	public static void main(String[] args) {

		 StartSynch sync= new StartSynch();
		 Flag flag= new Flag();
		 int numCars = 5000;
		 int nSteps = 100;
		
		var simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(flag, sync, numCars);
		simulation.setup();
		
		log("Running the simulation: " + numCars + " cars, for " + nSteps + " steps ...");
		
		simulation.run();

		long d = simulation.getSimulationDuration();
		log("Completed in " + d + " ms - average time per step: " + simulation.getAverageTimePerCycle() + " ms");
	}
	
	private static void log(String msg) {
		System.out.println("[ SIMULATION ] " + msg);
	}
}
