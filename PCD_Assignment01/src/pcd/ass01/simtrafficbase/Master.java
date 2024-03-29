package pcd.ass01.simtrafficbase;

import pcd.ass01.simengineseq.AbstractEnvironment;
import pcd.ass01.simengineseq.AbstractTask;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {
    private ConcurrentLinkedQueue<AbstractTask> taskQueue = new ConcurrentLinkedQueue<>();
    private HashMap<String, Worker> workers = new HashMap<>();
    private AbstractEnvironment env;
    private int workerCount;
    public Master(int workerCount){
        this.workerCount = workerCount;
        for (int i=0; i<this.workerCount; i++){
            Worker worker = new Worker();
            worker.setId(i);
            worker.setTaskQueue(taskQueue);
        }
    }

    public void execute(){
        env.init();
        //initAgent

    }
}
