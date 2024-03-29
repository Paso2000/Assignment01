package pcd.ass01.simtrafficbase;

import pcd.ass01.simengineseq.AbstractTask;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {
    private ConcurrentLinkedQueue<AbstractTask> taskQueue = new ConcurrentLinkedQueue<>();
    private HashMap<String, Worker> workers = new HashMap<>();

    private int workerCount;
    public Master(int workerCount){
        this.workerCount= workerCount;
    }
}
