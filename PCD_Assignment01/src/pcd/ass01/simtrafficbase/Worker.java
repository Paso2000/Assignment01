package pcd.ass01.simtrafficbase;

import pcd.ass01.simengineseq.AbstractTask;

import java.util.concurrent.ConcurrentLinkedQueue;


public class Worker implements Runnable{
    private int id;
    private ConcurrentLinkedQueue<AbstractTask> taskQueue;

    @Override
    public void run() {
        while (true){
            AbstractTask taskInput = this.taskQueue.poll();
            if (taskInput == null){
                break;
            }
            compute(taskInput);
        }
    }

    private void compute(AbstractTask taskInput) {
        taskInput.computeTask();
    }

    public void setTaskQueue(ConcurrentLinkedQueue<AbstractTask> workQueue){
        this.taskQueue = workQueue;
    }

    public void setId(int id) {
        this.id = id;
    }
}
