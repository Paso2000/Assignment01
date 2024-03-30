package pcd.ass01.simtrafficbase;

import pcd.ass01.simengineseq.AbstractEnvironment;
import pcd.ass01.simengineseq.Task;

public class AgentInitTask implements Task {
    private AbstractEnvironment env;

    public AgentInitTask(AbstractEnvironment env) {
        this.env=env;
    }


    @Override
    public void computeTask() {
        env.init();
    }

    @Override
    public TypeTask.enumTask getTypeTask() {
        return TypeTask.enumTask.AGENT_INIT;
    }
}
