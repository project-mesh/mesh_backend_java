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
public class Assigns extends Model<Assigns> {

    private static final long serialVersionUID = 1L;

    private Integer TaskId;

    private String Title;

    private Integer UserId;

    private LocalDateTime CreatedTime;

    private LocalDateTime UpdatedTime;


    public Integer getTaskId() {
        return TaskId;
    }

    public void setTaskId(Integer TaskId) {
        this.TaskId = TaskId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer UserId) {
        this.UserId = UserId;
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
        return "Assigns{" +
        "TaskId=" + TaskId +
        ", Title=" + Title +
        ", UserId=" + UserId +
        ", CreatedTime=" + CreatedTime +
        ", UpdatedTime=" + UpdatedTime +
        "}";
    }

    public Assigns(){
        this.CreatedTime = LocalDateTime.now();
        this.UpdatedTime = LocalDateTime.now();
    }
}
