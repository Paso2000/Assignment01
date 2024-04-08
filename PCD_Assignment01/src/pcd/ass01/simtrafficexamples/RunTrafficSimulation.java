package pcd.ass01.simtrafficexamples;

/**
 * 
 * Main class to create and run a simulation
 * 
 */
public class RunTrafficSimulation {

	public static void main(String[] args) {

		Synch synch = new Synch();
		SynchronizedFlag stopSynchronizedFlag = new SynchronizedFlag();
		Controller controller = new Controller(synch, stopSynchronizedFlag);

		//var simulation = new TrafficSimulationSingleRoadTwoCars(stopFlag, synch);
		//var simulation = new TrafficSimulationSingleRoadSeveralCars(stopFlag, synch);
		//var simulation = new TrafficSimulationSingleRoadWithTrafficLightTwoCars(stopFlag, synch);
		//var simulation = new TrafficSimulationWithCrossRoads(stopFlag, synch);
		var simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(stopSynchronizedFlag, synch, 100);
		simulation.setup();

		StatisticsListener stat = new StatisticsListener();
		simulation.addSimulationListener(stat);

		RoadView view = new RoadView();
		StateListener stateListener = new StateListener(view);
		simulation.addSimulationListener(stateListener);

		view.setViewListener(controller);
		view.display();
		simulation.run(10000);

	}
}
