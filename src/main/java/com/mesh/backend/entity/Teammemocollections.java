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
public class Teammemocollections extends Model<Teammemocollections> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    private String Description;

    private Integer TeamId;

    private LocalDateTime CreatedTime;

    private LocalDateTime UpdatedTime;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Integer getTeamId() {
        return TeamId;
    }

    public void setTeamId(Integer TeamId) {
        this.TeamId = TeamId;
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
        return "Teammemocollections{" +
        "Id=" + Id +
        ", Description=" + Description +
        ", TeamId=" + TeamId +
        ", CreatedTime=" + CreatedTime +
        ", UpdatedTime=" + UpdatedTime +
        "}";
    }

    public Teammemocollections(){
        this.Description = "";
        this.CreatedTime = LocalDateTime.now();
        this.UpdatedTime = LocalDateTime.now();
    }
}
