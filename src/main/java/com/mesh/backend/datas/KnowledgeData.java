package com.mesh.backend.datas;

import com.mesh.backend.entity.Projectmemos;
import com.mesh.backend.entity.Teammemos;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author xuedixuedi
 * 知识库基本类
 */
public class KnowledgeData {
    public int knowledgeId;
    public String knowledgeName;
    //链接地址
    public String hyperlink;
    public String uploaderName;
    public LocalDate createTime;

    public KnowledgeData(int knowledgeId, String knowledgeName, String hyperlink, String uploaderName, LocalDateTime createTime) {
        this.knowledgeId = knowledgeId;
        this.knowledgeName = knowledgeName;
        this.hyperlink = hyperlink;
        this.uploaderName = uploaderName;
        this.createTime = createTime.toLocalDate();
    }

    /**
     * 项目知识库
     *
     * @param projectmemos
     * @param uploaderName
     */
    public KnowledgeData(Projectmemos projectmemos, String uploaderName) {
        this.knowledgeId = projectmemos.getId();
        this.knowledgeName = projectmemos.getTitle();
        this.hyperlink = projectmemos.getText();
        this.uploaderName = uploaderName;
        this.createTime = projectmemos.getCreatedTime().toLocalDate();
    }

    /**
     * 团队知识库
     *
     * @param teammemos
     * @param uploaderName
     */
    public KnowledgeData(Teammemos teammemos, String uploaderName) {
        this.knowledgeId = teammemos.getId();
        this.knowledgeName = teammemos.getTitle();
        this.hyperlink = teammemos.getText();
        this.uploaderName = uploaderName;
        this.createTime = teammemos.getCreatedTime().toLocalDate();
    }
}
