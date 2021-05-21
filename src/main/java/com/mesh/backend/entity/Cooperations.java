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
public class Cooperations extends Model<Cooperations> {

    private static final long serialVersionUID = 1L;

    private Integer UserId;

    private Integer TeamId;

    private Integer AccessCount;

    private LocalDateTime CreatedTime;

    private LocalDateTime UpdatedTime;


    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }

    public Integer getTeamId() {
        return TeamId;
    }

    public void setTeamId(Integer TeamId) {
        this.TeamId = TeamId;
    }

    public Integer getAccessCount() {
        return AccessCount;
    }

    public void setAccessCount(Integer AccessCount) {
        this.AccessCount = AccessCount;
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
        return this.UserId;
    }

    @Override
    public String toString() {
        return "Cooperations{" +
        "UserId=" + UserId +
        ", TeamId=" + TeamId +
        ", AccessCount=" + AccessCount +
        ", CreatedTime=" + CreatedTime +
        ", UpdatedTime=" + UpdatedTime +
        "}";
    }

    public Cooperations(){
        this.CreatedTime = LocalDateTime.now();
        this.UpdatedTime = LocalDateTime.now();
        this.AccessCount = 0;
    }
}
