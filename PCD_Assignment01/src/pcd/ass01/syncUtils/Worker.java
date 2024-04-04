package pcd.ass01.syncUtils;

import pcd.ass01.simengineseq.AbstractAgent;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker extends Thread{
    private int dt;
    private List<AbstractAgent> agents;
    private CyclicBarrier barrier;

    public Worker(List<AbstractAgent> agents, CyclicBarrier barrier){
        this.agents = agents;
        this.barrier = barrier;
    }

    public void setDt(int dt){
        this.dt = dt;
    }
    @Override
    public void run(){
        for (AbstractAgent agent: agents){
            agent.step(dt);
        }
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

    }
}
