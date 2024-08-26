package com.njx.dao;

import com.njx.entity.BuildingsEntity;

/**
 * ClassName: IBuildingsDao
 * Package: com.njx.dao
 * Description:
 *
 * @Author 南极星
 * @Create 2024/6/16 14:05
 * Version 1.0
 */
public interface IBuildingsDao {
    public boolean addBuilding(int x,int y,String name,String introduce);
    public boolean queryBuilding(int x,int y);
    public BuildingsEntity[] queryBuildings();
    public BuildingsEntity queryBuilding(String name);
    public int queryBuildingID(String name);
    public String[] queryNames();
    public String queryName(int id);
    //修改名字和介绍
    public boolean updBuilding(int x,int y,String name,String introduce);
    //修改坐标和介绍
    public boolean updBuilding1(int x, int y, String name, String introduce);
    public boolean delBuilding(int x,int y);
    public String queryBuilding1(int x, int y);
}
