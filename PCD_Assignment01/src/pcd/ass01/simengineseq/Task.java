package pcd.ass01.simengineseq;

import pcd.ass01.simtrafficbase.TypeTask;

public interface Task {
    public void computeTask();

    public TypeTask.enumTask getTypeTask();
}
