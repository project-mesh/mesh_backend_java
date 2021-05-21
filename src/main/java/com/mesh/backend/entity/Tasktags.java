package com.mesh.backend.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
public class Tasktags extends Model<Tasktags> {

    private static final long serialVersionUID = 1L;

    private Integer TaskId;

    private String tag;

    private LocalDateTime CreatedTime;

    private LocalDateTime UpdatedTime;


    public Integer getTaskId() {
        return TaskId;
    }

    public void setTaskId(Integer TaskId) {
        this.TaskId = TaskId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LocalDateTime getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(LocalDateTime CreatedTime) {
        this.CreatedTime = CreatedTime;
    }

    public LocalDateTime getUpdatedTime() {
        return UpdatedTime;
    }

    public void setUpdatedTime(LocalDateTime UpdatedTime) {
        this.UpdatedTime = UpdatedTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.TaskId;
    }

    @Override
    public String toString() {
        return "Tasktags{" +
        "TaskId=" + TaskId +
        ", tag=" + tag +
        ", CreatedTime=" + CreatedTime +
        ", UpdatedTime=" + UpdatedTime +
        "}";
    }
}
