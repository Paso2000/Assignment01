package pcd.ass01.simtrafficbase;

import pcd.ass01.simengineseq.AbstractEnvironment;
import pcd.ass01.simengineseq.Action;
import pcd.ass01.simengineseq.Percept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class RoadsEnv extends AbstractEnvironment {
		
	private static final int MIN_DIST_ALLOWED = 5;
	private static final int CAR_DETECTION_RANGE = 30;
	private static final int SEM_DETECTION_RANGE = 30;
	
	/* list of roads */
	private List<Road> roads;

	/* traffic lights */
	private List<pcd.ass01.simtrafficbase.TrafficLight> trafficLights;
	
	/* cars situated in the environment */	
	private HashMap<String, pcd.ass01.simtrafficbase.CarAgentInfo> registeredCars;


	public RoadsEnv() {
		super("traffic-env");
		registeredCars = new HashMap<>();	
		trafficLights = new ArrayList<>();
		roads = new ArrayList<>();
	}
	
	@Override
	public void init() {
		for (var tl: trafficLights) {
			tl.init();
		}
	}
	
	@Override
	public void step(int dt) {
		for (var tl: trafficLights) {
			tl.step(dt);
		}
	}
	
	public void registerNewCar(CarAgent car, Road road, double pos) {
		registeredCars.put(car.getId(), new pcd.ass01.simtrafficbase.CarAgentInfo(car, road, pos));
	}

	public Road createRoad(P2d p0, P2d p1) {
		Road r = new Road(p0, p1);
		this.roads.add(r);
		return r;
	}

	public pcd.ass01.simtrafficbase.TrafficLight createTrafficLight(P2d pos, pcd.ass01.simtrafficbase.TrafficLight.TrafficLightState initialState, int greenDuration, int yellowDuration, int redDuration) {
		pcd.ass01.simtrafficbase.TrafficLight tl = new pcd.ass01.simtrafficbase.TrafficLight(pos, initialState, greenDuration, yellowDuration, redDuration);
		this.trafficLights.add(tl);
		return tl;
	}

	@Override
	public Percept getCurrentPercepts(String agentId) {
		
		pcd.ass01.simtrafficbase.CarAgentInfo carInfo = registeredCars.get(agentId);
		double pos = carInfo.getPos();
		Road road = carInfo.getRoad();
		Optional<pcd.ass01.simtrafficbase.CarAgentInfo> nearestCar = getNearestCarInFront(road,pos, CAR_DETECTION_RANGE);
		Optional<TrafficLightInfo> nearestSem = getNearestSemaphoreInFront(road,pos, SEM_DETECTION_RANGE);
		
		return new CarPercept(pos, nearestCar, nearestSem);
	}

	private Optional<pcd.ass01.simtrafficbase.CarAgentInfo> getNearestCarInFront(Road road, double carPos, double range){
		return 
				registeredCars
				.entrySet()
				.stream()
				.map(el -> el.getValue())
				.filter((carInfo) -> carInfo.getRoad() == road)
				.filter((carInfo) -> {
					double dist = carInfo.getPos() - carPos;
					return dist > 0 && dist <= range;
				})
				.min((c1, c2) -> (int) Math.round(c1.getPos() - c2.getPos()));
	}

	private Optional<TrafficLightInfo> getNearestSemaphoreInFront(Road road, double carPos, double range){
		return 
				road.getTrafficLights()
				.stream()
				.filter((TrafficLightInfo tl) -> tl.roadPos() > carPos)
				.min((c1, c2) -> (int) Math.round(c1.roadPos() - c2.roadPos()));
	}
	
	
	@Override
	public void doAction(String agentId, Action act) {
		switch (act) {
		case MoveForward mv: {
			pcd.ass01.simtrafficbase.CarAgentInfo info = registeredCars.get(agentId);
			Road road = info.getRoad();
			Optional<pcd.ass01.simtrafficbase.CarAgentInfo> nearestCar = getNearestCarInFront(road, info.getPos(), CAR_DETECTION_RANGE);
			
			if (!nearestCar.isEmpty()) {
				double dist = nearestCar.get().getPos() - info.getPos();
				if (dist > mv.distance() + MIN_DIST_ALLOWED) {
					info.updatePos(info.getPos() + mv.distance());
				}
			} else {
				info.updatePos(info.getPos() + mv.distance());
			}

			if (info.getPos() > road.getLen()) {
				info.updatePos(0);
			}
			break;
		}
		default: break;
		}
	}
	
	
	public List<CarAgentInfo> getAgentInfo(){
		return this.registeredCars.entrySet().stream().map(el -> el.getValue()).toList();
	}

	public List<Road> getRoads(){
		return roads;
	}
	
	public List<TrafficLight> getTrafficLights(){
		return trafficLights;
	}
}
