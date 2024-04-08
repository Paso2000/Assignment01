package pcd.ass01.simtrafficexamples;

import pcd.ass01.simengineseq.AbstractAgent;
import pcd.ass01.simengineseq.AbstractEnvironment;
import pcd.ass01.simengineseq.SimulationListener;

import java.util.List;

public class StateListener implements SimulationListener {


    RoadView view;

    public StateListener(RoadView view) {
        super();
        this.view = view;
    }

    @Override
    public void notifyInit(int t, List<AbstractAgent> agents, AbstractEnvironment env) { }

    @Override
    public void notifyStepDone(int t, List<AbstractAgent> agents, AbstractEnvironment env) {
        view.notifyStepDone(t, agents, env);
    }
    @Override
    public void notifyStateChanged(String message) {
        view.changeState(message);
    }

    @Override
    public void notifyStepOver() {
        view.stepOver();
    }
}
