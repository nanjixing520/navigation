package com.njx.dao;

import com.njx.entity.BuildingsEntity;
import com.njx.utils.DBUtils;

import java.util.List;
import java.util.Map;

/**
 * ClassName: IBuildingsDao
 * Package: com.njx.dao
 * Description:
 *
 * @Author 南极星
 * @Create 2024/6/16 14:04
 * Version 1.0
 */
public class BuildingsDaoImpl implements IBuildingsDao{
    @Override
    public boolean addBuilding(int x, int y, String name, String introduce) {
        String sql1="insert into buildings values(?,?,?,?,null,0)";
        int i = DBUtils.update(sql1,name,x,y,introduce);
        return i>0 ? true:false;
    }

    @Override
    public boolean queryBuilding(int x, int y) {
        String sql1="select * from buildings where x=? and y=? and status=?";
        List<Map<String, Object>> maps = DBUtils.queryMap(sql1,x,y,0);
        return maps.size()>0 ? true:false;
    }
    public String queryBuilding1(int x, int y) {
        String sql1="select * from buildings where x=? and y=? and status=?";
        List<Map<String, Object>> maps = DBUtils.queryMap(sql1,x,y,0);
        Map<String, Object> map = maps.get(0);
        return map.get("name").toString();
    }

    @Override
    public BuildingsEntity[] queryBuildings() {
        String sql1="select * from buildings where status=?";
        List<Map<String, Object>> maps = DBUtils.queryMap(sql1,0);
        if(maps.size()>0){
            BuildingsEntity[] be=new BuildingsEntity[maps.size()];
            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> map = maps.get(i);
                be[i]=new BuildingsEntity();
                be[i].setName((String)map.get("name"));
                int x=Integer.parseInt(map.get("x").toString());
                int y=Integer.parseInt(map.get("y").toString());
                be[i].setX(x);
                be[i].setY(y);
                be[i].setIntroduce(map.get("introduce").toString());
                int id=Integer.parseInt(map.get("id").toString());
                be[i].setId(id);
            }
            return be;
        }
        return null;
    }
    @Override
    public BuildingsEntity queryBuilding(String name) {
        String sql="select * from buildings where name=? and status=?";
        List<Map<String, Object>> maps = DBUtils.queryMap(sql, name,0);
        if(maps.size()>0){
            Map<String, Object> map = maps.get(0);
            BuildingsEntity buildingsEntity=new BuildingsEntity();
            buildingsEntity.setName((String)map.get("name"));
            int x=Integer.parseInt(map.get("x").toString());
            int y=Integer.parseInt(map.get("y").toString());
            buildingsEntity.setX(x);
            buildingsEntity.setY(y);
            buildingsEntity.setIntroduce(map.get("introduce").toString());
            int id=Integer.parseInt(map.get("id").toString());
            buildingsEntity.setId(id);
            return buildingsEntity;
        }
        return null;
    }

    @Override
    public int queryBuildingID(String name) {
        String sql="select * from buildings where name=? and status=?";
        List<Map<String, Object>> maps = DBUtils.queryMap(sql, name,0);
        if(maps.size()>0){
            Map<String, Object> map = maps.get(0);
            int id=Integer.parseInt(map.get("id").toString());
            return id;
        }
        return 0;
    }

    @Override
    public String[] queryNames() {
        String sql1="select * from buildings where status=?";
        List<Map<String, Object>> maps = DBUtils.queryMap(sql1,0);
        if(maps.size()>0){
            String[] be=new String[maps.size()];
            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> map = maps.get(i);
                be[i]=new String(map.get("name").toString());
            }
            return be;
        }
        return null;
    }

    @Override
    public String queryName(int id) {
        String sql1="select * from buildings where id=? and status=?";
        List<Map<String, Object>> maps = DBUtils.queryMap(sql1,id,0);
        if(maps.size()>0){
            Map<String, Object> map = maps.get(0);
            return map.get("name").toString();
        }
        return null;
    }

    @Override
    public boolean updBuilding(int x, int y, String name, String introduce) {
        String sql="update buildings set name=?,introduce=? where x=? and y=? and status=?";
        int i = DBUtils.update(sql, name, introduce,x,y,0);
        return i>0 ? true:false;
    }
    public boolean updBuilding1(int x, int y, String name, String introduce) {
        String sql="update buildings set x=?,y=?,introduce=? where name=? and status=?";
        int i = DBUtils.update(sql,x,y,introduce,name,0);
        return i>0 ? true:false;
    }

    @Override
    public boolean delBuilding(int x, int y) {
        String sql="update buildings set status=? where x=? and y=?";
        int i = DBUtils.update(sql,1,x,y);
        return i>0 ? true:false;
    }
}
