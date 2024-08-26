package com.njx.dao;

import com.njx.entity.RoadEntity;

/**
 * ClassName: IRoadDao
 * Package: com.njx.dao
 * Description:
 *
 * @Author 南极星
 * @Create 2024/6/17 15:49
 * Version 1.0
 */
public interface IRoadDao {
    public int queryRoadLength(String start, String end);
    //查询是否已存在该路径
    public boolean queryRoad(String start, String end);
    //查找得到初始地和目的地的名字的数组
    public String[] queryStartb();
    public String[] queryEndb();
    //删除建筑自动断掉路径
    public boolean deleteRoad(int id);
    public boolean deleteRoad1(int id);
    public boolean addRoad(String start, String end,int length);
    public boolean delRoad(String start, String end);
    public boolean updRoad(String start, String end,int length);
    public RoadEntity[] queryRoadAll();
}
