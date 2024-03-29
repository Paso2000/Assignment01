package pcd.ass01.simtrafficbase;

import pcd.ass01.simengineseq.AbstractEnvironment;
import pcd.ass01.simengineseq.AbstractTask;

public class EnvInitTask extends AbstractTask {
    private AbstractEnvironment env;
    @Override
    public void computeTask() {
        env.init();
    }

    public AbstractEnvironment getEnv(){
        return this.env;
    }
}
