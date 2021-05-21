package com.mesh.backend.datas;

import com.mesh.backend.entity.Bulletins;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 公告类
 *
 * @author xuedixuedi
 */
public class BulletinData {
    public int bulletinId;
    public String bulletinName;
    public String description;
    public LocalDate createTime;

    public BulletinData(int bulletinId, String bulletinName, String description, LocalDateTime createTime) {
        this.bulletinId = bulletinId;
        this.bulletinName = bulletinName;
        this.description = description;
        this.createTime = createTime.toLocalDate();
    }

    /**
     * 根据表生成bulletin
     * @param bulletins
     */
    public BulletinData(Bulletins bulletins){
        this.bulletinId = bulletins.getId();
        this.bulletinName = bulletins.getTitle();
        this.description = bulletins.getContent();
        this.createTime = bulletins.getCreatedTime().toLocalDate();
    }
}
