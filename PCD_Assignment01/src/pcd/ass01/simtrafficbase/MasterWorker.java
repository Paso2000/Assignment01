package pcd.ass01.simtrafficbase;

import pcd.ass01.simengineseq.AbstractEnvironment;
import pcd.ass01.simengineseq.Task;

import java.util.concurrent.ConcurrentLinkedQueue;

import static java.rmi.server.LogStream.log;

public class MasterWorker extends Thread {
    private ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<>();
    private AbstractEnvironment env;
    private final int nCarAgents;
    public MasterWorker(int nCarAgents){
        this.nCarAgents=nCarAgents;
    }
    @Override
    public void run(){
        int nWorker = Runtime.getRuntime().availableProcessors() +1  ;
        TaskBag bag = new TaskBag();
        TaskCompletionLatch latch = new TaskCompletionLatch(nWorker);
        for (int i = 0; i<nWorker; i++){
            Worker worker = new Worker(latch);
            worker.start();
        }
        env.init();// inizializzo l'envirment
        latch.reset();
        bag.clear();

        //inserisco nella bag of task tante task agent init quanti sono gli agent
        //con il latch aspetto mando in wait il master finche gli agent non sono tutti inizializzati

        try {
            for(int j=0; j<nCarAgents;j++){
                Task t = new AgentInitTask(env);
                bag.addNewTask(t);
            }
            log("waiting for every agent to inizialize");
            latch.waitCompletion();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //thread che fa andare il semaforo
        while(true){
            try {
                latch.reset();
                bag.clear();
                for (int k=0;k<nCarAgents;k++){
                    Task t = new SenseDecideTask();
                    bag.addNewTask(t);
                }
                log("aspetto tutte le sense e le decide");
                latch.notifyCompletion();
                bag.addNewTask(new ActTask(dt));

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
