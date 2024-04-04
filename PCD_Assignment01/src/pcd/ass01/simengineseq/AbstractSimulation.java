package pcd.ass01.simengineseq;

import pcd.ass01.syncUtils.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Base class for defining concrete simulations
 *  
 */
public abstract class AbstractSimulation {

	/* environment of the simulation */
	private AbstractEnvironment env;
	
	/* list of the agents */
	private List<AbstractAgent> agents;
	
	/* simulation listeners */
	private List<SimulationListener> listeners;

	/* logical time step */
	private int dt;
	
	/* initial logical time */
	private int t0;

	/* in the case of sync with wall-time */
	private boolean toBeInSyncWithWallTime;
	private int nStepsPerSec;
	
	/* for time statistics*/
	private long currentWallTime;
	private long startWallTime;
	private long endWallTime;
	private long averageTimePerStep;

	private List<Worker> workers;


	protected AbstractSimulation() {
		agents = new ArrayList<AbstractAgent>();
		listeners = new ArrayList<SimulationListener>();
		workers = new ArrayList<Worker>();
		toBeInSyncWithWallTime = false;
	}
	
	/**
	 * 
	 * Method used to configure the simulation, specifying env and agents
	 * 
	 */
	protected abstract void setup();
	
	/**
	 * Method running the simulation for a number of steps,
	 * using a sequential approach
	 * 
	 * @param numSteps
	 */
	public void run(int numSteps) {

		startWallTime = System.currentTimeMillis();

		/* initialize the env and the agents inside */
		int t = t0;
		/* lascio le Init serializzate*/
		env.init();
		for (var a: agents) {
			a.init(env);
		}

		this.notifyReset(t, agents, env);
		
		long timePerStep = 0;
		int nSteps = 0;

		/*Inizializzo thread e barrier*/
		int nThread = Runtime.getRuntime().availableProcessors();
		int barrierParts = Math.min(agents.size(), nThread) + 1;
		CyclicBarrier barrier = new CyclicBarrier(barrierParts);

		/*calcolo quanti agent dovrà gestire ciascun worker*/
		List<List<AbstractAgent>> parts = new ArrayList<List<AbstractAgent>>();
		getPartsForWorker(nThread, parts);

		/*istanzio i worker*/
		for (List<AbstractAgent> p : parts) {
			Worker worker = new Worker(p, barrier);
			workers.add(worker);
		}

		while (nSteps < numSteps) {

			currentWallTime = System.currentTimeMillis();
		
			/* make a step */
			env.step(dt);

			/*Paralellizzo la step degli agents*/
			for (Worker w : workers){
				w.setDt(dt);
				w.start();
			}
			try {
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				throw new RuntimeException(e);
			}

            t += dt;
			
			notifyNewStep(t, agents, env);

			nSteps++;			
			timePerStep += System.currentTimeMillis() - currentWallTime;
			
			if (toBeInSyncWithWallTime) {
				syncWithWallTime();
			}
		}	
		
		endWallTime = System.currentTimeMillis();
		this.averageTimePerStep = timePerStep / numSteps;
		
	}

	private void getPartsForWorker(int nThread, List<List<AbstractAgent>> parts) {
		int agentsSplitted = 0;
		int partsSize = agents.size() / (nThread - 1);
		if (partsSize == 0) {
			partsSize = 1;
		}
		int nParts = Math.min(agents.size(), nThread);
		for (int i = 0; i < nParts; i++) {
			int to = agentsSplitted + partsSize;
			if (i == nThread - 1) {
				to = agents.size();
			}
			parts.add(new ArrayList<AbstractAgent>(
					agents.subList(agentsSplitted, to)));
			agentsSplitted += partsSize;
		}
	}

	public long getSimulationDuration() {
		return endWallTime - startWallTime;
	}
	
	public long getAverageTimePerCycle() {
		return averageTimePerStep;
	}
	
	/* methods for configuring the simulation */
	
	protected void setupTimings(int t0, int dt) {
		this.dt = dt;
		this.t0 = t0;
	}
	
	protected void syncWithTime(int nCyclesPerSec) {
		this.toBeInSyncWithWallTime = true;
		this.nStepsPerSec = nCyclesPerSec;
	}
		
	protected void setupEnvironment(AbstractEnvironment env) {
		this.env = env;
	}

	protected void addAgent(AbstractAgent agent) {
		agents.add(agent);
	}
	
	/* methods for listeners */
	
	public void addSimulationListener(SimulationListener l) {
		this.listeners.add(l);
	}
	
	private void notifyReset(int t0, List<AbstractAgent> agents, AbstractEnvironment env) {
		for (var l: listeners) {
			l.notifyInit(t0, agents, env);
		}
	}

	private void notifyNewStep(int t, List<AbstractAgent> agents, AbstractEnvironment env) {
		for (var l: listeners) {
			l.notifyStepDone(t, agents, env);
		}
	}

	/* method to sync with wall time at a specified step rate */
	
	private void syncWithWallTime() {
		try {
			long newWallTime = System.currentTimeMillis();
			long delay = 1000 / this.nStepsPerSec;
			long wallTimeDT = newWallTime - currentWallTime;
			if (wallTimeDT < delay) {
				Thread.sleep(delay - wallTimeDT);
			}
		} catch (Exception ex) {}		
	}
}
