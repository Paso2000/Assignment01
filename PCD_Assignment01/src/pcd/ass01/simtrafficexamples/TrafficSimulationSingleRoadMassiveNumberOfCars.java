package pcd.ass01.simtrafficexamples;

import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficbase.*;

import java.util.Random;

public class TrafficSimulationSingleRoadMassiveNumberOfCars extends AbstractSimulation {

	private int numCars;

	public TrafficSimulationSingleRoadMassiveNumberOfCars(SynchronizedFlag stopSynchronizedFlag, Synch sync, int numCars) {
		super(stopSynchronizedFlag, sync);
		this.numCars = numCars;
	}

	public void setup() {
		this.setupTimings(0, 1);

		RoadsEnv env = new RoadsEnv();
		this.setupEnvironment(env);
		
		Road road = env.createRoad(new P2d(0,300), new P2d(3000,300));

		for (int i = 0; i < numCars; i++) {
			
			String carId = "car-" + i;
			double initialPos = i*10;
			Random gen = new Random();
			double carAcceleration = 1 + gen.nextDouble()/2;
			double carDeceleration = 0.3 + gen.nextDouble()/2;
			double carMaxSpeed = 4 + gen.nextDouble();
						
			CarAgent car = new CarAgentBasic(carId, env, 
									road,
									initialPos, 
									carAcceleration, 
									carDeceleration,
									carMaxSpeed);
			this.addAgent(car);

			this.syncWithTime(50);
		}
		
	}	
}
	