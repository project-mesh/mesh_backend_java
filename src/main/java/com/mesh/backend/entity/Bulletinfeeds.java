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
public class Bulletinfeeds extends Model<Bulletinfeeds> {

    private static final long serialVersionUID = 1L;

    private Integer BulletinId;

    private Integer UserId;

    private LocalDateTime CreatedTime;

    private LocalDateTime UpdatedTime;


    public Integer getBulletinId() {
        return BulletinId;
    }

    public void setBulletinId(Integer BulletinId) {
        this.BulletinId = BulletinId;
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
        return this.BulletinId;
    }

    @Override
    public String toString() {
        return "Bulletinfeeds{" +
        "BulletinId=" + BulletinId +
        ", UserId=" + UserId +
        ", CreatedTime=" + CreatedTime +
        ", UpdatedTime=" + UpdatedTime +
        "}";
    }
}
