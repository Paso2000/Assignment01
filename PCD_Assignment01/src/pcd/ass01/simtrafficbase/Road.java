package pcd.ass01.simtrafficbase;

import java.util.ArrayList;
import java.util.List;



public class Road {

	private double len;
	private pcd.ass01.simtrafficbase.P2d from;
	private pcd.ass01.simtrafficbase.P2d to;
	private List<pcd.ass01.simtrafficbase.TrafficLightInfo> trafficLights;

	public Road(pcd.ass01.simtrafficbase.P2d from, pcd.ass01.simtrafficbase.P2d to) {
		this.from = from;
		this.to = to;
		this.len = pcd.ass01.simtrafficbase.P2d.len(from, to);
		trafficLights = new ArrayList<>();
	}
	
	public double getLen() {
		return len;
	}
	
	public pcd.ass01.simtrafficbase.P2d getFrom() {
		return from;
	}
	
	public P2d getTo() {
		return to;
	}
	
	public void addTrafficLight(TrafficLight sem, double pos) {
		trafficLights.add(new pcd.ass01.simtrafficbase.TrafficLightInfo(sem, this, pos));
	}
	
	public List<TrafficLightInfo> getTrafficLights(){
		return trafficLights;
	}
}
