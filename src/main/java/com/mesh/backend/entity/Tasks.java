package com.mesh.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Tasks extends Model<Tasks> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    private String Name;

    private Integer Priority;

    private String Description;

    private LocalDateTime StartTime;

    private LocalDateTime EndTime;

    private LocalDateTime CreatedTime;

    private LocalDateTime UpdatedTime;

    private Boolean Finished;

    private Integer BoardId;

    private Integer LeaderId;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Integer getPriority() {
        return Priority;
    }

    public void setPriority(Integer Priority) {
        this.Priority = Priority;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public LocalDateTime getStartTime() {
        return StartTime;
    }

    public void setStartTime(LocalDateTime StartTime) {
        this.StartTime = StartTime;
    }

    public LocalDateTime getEndTime() {
        return EndTime;
    }

    public void setEndTime(LocalDateTime EndTime) {
        this.EndTime = EndTime;
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

    public Boolean getFinished() {
        return Finished;
    }

    public void setFinished(Boolean Finished) {
        this.Finished = Finished;
    }

    public Integer getBoardId() {
        return BoardId;
    }

    public void setBoardId(Integer BoardId) {
        this.BoardId = BoardId;
    }

    public Integer getLeaderId() {
        return LeaderId;
    }

    public void setLeaderId(Integer LeaderId) {
        this.LeaderId = LeaderId;
    }

    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "Tasks{" +
        "Id=" + Id +
        ", Name=" + Name +
        ", Priority=" + Priority +
        ", Description=" + Description +
        ", StartTime=" + StartTime +
        ", EndTime=" + EndTime +
        ", CreatedTime=" + CreatedTime +
        ", UpdatedTime=" + UpdatedTime +
        ", Finished=" + Finished +
        ", BoardId=" + BoardId +
        ", LeaderId=" + LeaderId +
        "}";
    }

    public Tasks(){
        this.Description = "";
        this.CreatedTime = LocalDateTime.now();
        this.UpdatedTime = LocalDateTime.now();
        this.Finished = false;
        this.StartTime = LocalDateTime.now();
    }
}
