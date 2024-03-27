package PCD.ass01.simtrafficbase;

import java.util.Random;

public abstract class TrafficLightWorker extends Thread {

    private Random rand;

    public TrafficLightWorker(String name){
        super(name);
        rand = new Random();
    }

    protected void wasteTime(long ms){
        try {
            sleep(ms);
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public void run(){

    }

    protected void wasteRandomTime(long min, long max){
        try {
            double value = rand.nextDouble();
            double delay = min + value*(max-min);
            sleep((int)delay);
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    protected void print(String msg){
        synchronized (System.out){
            System.out.print(msg);
        }
    }

    protected void println(String msg){
        synchronized (System.out){
            System.out.println(msg);
        }
    }

    protected void log(String st){
        synchronized(System.out){
            System.out.println("["+this.getName()+"] "+st);
        }
    }
}
