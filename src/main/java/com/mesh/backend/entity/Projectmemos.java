package com.mesh.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 项目知识库
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
public class Projectmemos extends Model<Projectmemos> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    private String Title;

    private String Text;

    private Integer CollectionId;

    private Integer UserId;

    private LocalDateTime CreatedTime;

    private LocalDateTime UpdatedTime;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getText() {
        return Text;
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    public Integer getCollectionId() {
        return CollectionId;
    }

    public void setCollectionId(Integer CollectionId) {
        this.CollectionId = CollectionId;
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
        return this.Id;
    }

    @Override
    public String toString() {
        return "Projectmemos{" +
                "Id=" + Id +
                ", Title=" + Title +
                ", Text=" + Text +
                ", CollectionId=" + CollectionId +
                ", UserId=" + UserId +
                ", CreatedTime=" + CreatedTime +
                ", UpdatedTime=" + UpdatedTime +
                "}";
    }

    public Projectmemos(){
        this.CreatedTime = LocalDateTime.now();
        this.UpdatedTime = LocalDateTime.now();
    }

}
