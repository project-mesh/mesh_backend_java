package com.mesh.backend.datas;

import com.mesh.backend.entity.Tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务返回值
 *
 * @author xuedixuedi
 */
public class BaseTaskData extends BaseData {
    public ArrayList<TaskData> tasks;
    public TaskData task;


    /**
     * 任务:update/create/query
     *
     * @param task
     * @param founder
     * @param principal
     * @param subTasks
     */
    public BaseTaskData(Tasks task, String founder, String principal, List<SubTaskData> subTasks) {
        super(true, "");
        this.task = new TaskData(task, founder, principal, subTasks);
        this.tasks = null;
    }

    public BaseTaskData(ArrayList<TaskData> tasks){
        super(true, "");
        this.tasks = tasks;
        this.task = null;
    }

}
