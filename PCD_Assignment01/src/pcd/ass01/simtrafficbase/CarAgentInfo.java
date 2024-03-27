package pcd.ass01.simtrafficbase;

public  class CarAgentInfo {

	private pcd.ass01.simtrafficbase.CarAgent car;
	private double pos;
	private pcd.ass01.simtrafficbase.Road road;
	
	public CarAgentInfo(pcd.ass01.simtrafficbase.CarAgent car, pcd.ass01.simtrafficbase.Road road, double pos) {
		this.car = car;
		this.road = road;
		this.pos = pos;
	}
	
	public double getPos() {
		return pos;
	}
	
	public void updatePos(double pos) {
		this.pos = pos;
	}
	
	public CarAgent getCar() {
		return car;
	}	
	
	public Road getRoad() {
		return road;
	}
}
