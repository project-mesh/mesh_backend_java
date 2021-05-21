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
public class Subtasks extends Model<Subtasks> {

    private static final long serialVersionUID = 1L;

    private String Title;

    private Integer TaskId;

    private String Description;

    private Boolean Finished;

    private LocalDateTime CreatedTime;

    private LocalDateTime UpdatedTime;


    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public Integer getTaskId() {
        return TaskId;
    }

    public void setTaskId(Integer TaskId) {
        this.TaskId = TaskId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Boolean getFinished() {
        return Finished;
    }

    public void setFinished(Boolean Finished) {
        this.Finished = Finished;
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
        return this.Title;
    }

    @Override
    public String toString() {
        return "Subtasks{" +
        "Title=" + Title +
        ", TaskId=" + TaskId +
        ", Description=" + Description +
        ", Finished=" + Finished +
        ", CreatedTime=" + CreatedTime +
        ", UpdatedTime=" + UpdatedTime +
        "}";
    }

    public Subtasks(){
        this.Description = "";
        this.Finished = false;
        this.CreatedTime = LocalDateTime.now();
        this.UpdatedTime = LocalDateTime.now();
    }
}
