package com.mesh.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 团队知识库
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
public class Teammemos extends Model<Teammemos> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    private String Title;

    private String Text;

    private LocalDateTime CreatedTime;

    private Integer UserId;

    private LocalDateTime UpdatedTime;

    private Integer CollectionId;


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

    public LocalDateTime getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(LocalDateTime CreatedTime) {
        this.CreatedTime = CreatedTime;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }

    public LocalDateTime getUpdatedTime() {
        return UpdatedTime;
    }

    public void setUpdatedTime(LocalDateTime UpdatedTime) {
        this.UpdatedTime = UpdatedTime;
    }

    public Integer getCollectionId() {
        return CollectionId;
    }

    public void setCollectionId(Integer CollectionId) {
        this.CollectionId = CollectionId;
    }

    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "Teammemos{" +
                "Id=" + Id +
                ", Title=" + Title +
                ", Text=" + Text +
                ", CreatedTime=" + CreatedTime +
                ", UserId=" + UserId +
                ", UpdatedTime=" + UpdatedTime +
                ", CollectionId=" + CollectionId +
                "}";
    }

    public Teammemos(){
        this.CreatedTime = LocalDateTime.now();
        this.UpdatedTime = LocalDateTime.now();
    }

}
