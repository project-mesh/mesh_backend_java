package com.mesh.backend.datas;

import com.mesh.backend.entity.Bulletins;

import java.util.List;

/**
 * bulletin返回值
 *
 * @author xuedixuedi
 */
public class BaseBulletinData extends BaseData {
    public BulletinData bulletin;
    public List<BulletinData> bulletins;

    /**
     * 创建
     *
     * @param bulletins
     */
    public BaseBulletinData(Bulletins bulletins) {
        super(true, "");
        bulletin = new BulletinData(bulletins);
    }

    /**
     * 查询
     *
     * @param bulletins
     */
    public BaseBulletinData(List<BulletinData> bulletins) {
        super(true, "");
        this.bulletins = bulletins;
    }

}
