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
public class Teams extends Model<Teams> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    private String Name;

    private Integer AdminId;

    private LocalDateTime CreatedTime;

    private LocalDateTime UpdatedTime;


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

    public Integer getAdminId() {
        return AdminId;
    }

    public void setAdminId(Integer AdminId) {
        this.AdminId = AdminId;
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
        return this.Id;
    }

    @Override
    public String toString() {
        return "Teams{" +
        "Id=" + Id +
        ", Name=" + Name +
        ", AdminId=" + AdminId +
        ", CreatedTime=" + CreatedTime +
        ", UpdatedTime=" + UpdatedTime +
        "}";
    }
    public Teams(){
        this.CreatedTime = LocalDateTime.now();
        this.UpdatedTime = LocalDateTime.now();
    }
}
