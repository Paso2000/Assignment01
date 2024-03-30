package pcd.ass01.simtrafficbase;

import pcd.ass01.simengineseq.Task;

import static java.rmi.server.LogStream.log;


public class Worker extends Thread{
    private TaskBag bagOfTask;
    private TaskCompletionLatch latch;
    public Worker(TaskCompletionLatch latch){
        this.latch=latch;
    }

    @Override
    public void run() {

        log("started");
        while (true){
            //wait for a task
            Task t = bagOfTask.getATask();
            TypeTask.enumTask type = t.getTypeTask();
            switch (type){
                case AGENT_INIT :
                    break;
                case SENSE_DECIDE:
                    break;
                case ACT:
                    break;

            }

            //task done
            latch.notifyCompletion();
        }

    }

    private void compute(Task taskInput) {
        taskInput.computeTask();
    }

}
