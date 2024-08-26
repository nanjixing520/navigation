package com.njx.dao;

import com.njx.entity.BuildingsEntity;
import com.njx.entity.RoadEntity;
import com.njx.utils.DBUtils;

import java.util.List;
import java.util.Map;

/**
 * ClassName: RoadDaoImpl
 * Package: com.njx.dao
 * Description:
 *
 * @Author 南极星
 * @Create 2024/6/17 15:49
 * Version 1.0
 */
public class RoadDaoImpl implements IRoadDao{

    @Override
    public int queryRoadLength(String start, String end) {
        BuildingsDaoImpl bd = new BuildingsDaoImpl();
        int i = bd.queryBuildingID(start);
        int j = bd.queryBuildingID(end);
        if(i==0||j==0){
            return 0;
        }
        String sql = "select * from road where startb=? and endb=? and status=?";
        List<Map<String, Object>> maps = DBUtils.queryMap(sql,i,j,0);
        Map<String, Object> map = maps.get(0);
        int length = Integer.parseInt(map.get("length").toString());
        return length;
    }

    @Override
    public boolean queryRoad(String start, String end) {
        BuildingsDaoImpl bd = new BuildingsDaoImpl();
        int i = bd.queryBuildingID(start);
        int j = bd.queryBuildingID(end);
        if(i==0||j==0){
            return false;
        }
        String sql = "select * from road where startb=? and endb=? and status=?";
        List<Map<String, Object>> maps = DBUtils.queryMap(sql,i,j,0);
        return maps.size() > 0 ;
    }

    @Override
    public String[] queryStartb() {
        BuildingsDaoImpl bd = new BuildingsDaoImpl();
        String sql1="select * from road where status=?";
        List<Map<String, Object>> maps = DBUtils.queryMap(sql1,0);
        if(maps.size()>0){
            String[] be=new String[maps.size()];
            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> map = maps.get(i);
                int num = Integer.parseInt(map.get("startb").toString());
                be[i]=bd.queryName(num);
            }
            return be;
        }
        return null;
    }
    @Override
    public String[] queryEndb() {
        BuildingsDaoImpl bd = new BuildingsDaoImpl();
        String sql1="select * from road where status=?";
        List<Map<String, Object>> maps = DBUtils.queryMap(sql1,0);
        if(maps.size()>0){
            String[] be=new String[maps.size()];
            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> map = maps.get(i);
                int num = Integer.parseInt(map.get("endb").toString());
                be[i]=bd.queryName(num);
            }
            return be;
        }
        return null;
    }

    @Override
    public boolean deleteRoad(int id) {
        String sql="update road set status=? where startb=?";
        int i = DBUtils.update(sql,1,id);
        return i>0 ? true:false;
    }

    @Override
    public boolean deleteRoad1(int id) {
        String sql="update road set status=? where endb=?";
        int i = DBUtils.update(sql,1,id);
        return i>0 ? true:false;
    }

    @Override
    public boolean addRoad(String start, String end, int length) {
        BuildingsDaoImpl bd = new BuildingsDaoImpl();
        int startb = bd.queryBuildingID(start);
        int endb = bd.queryBuildingID(end);
        if(startb==0||endb==0){
            return false;
        }
       String sql="insert into road values(null,?,?,?,0)";
       int i = DBUtils.update(sql,startb,endb,length);
       return i>0 ? true:false;
    }

    @Override
    public boolean delRoad(String start, String end) {
        BuildingsDaoImpl bd = new BuildingsDaoImpl();
        int startb = bd.queryBuildingID(start);
        int endb = bd.queryBuildingID(end);
        if(startb==0||endb==0){
            return false;
        }
        String sql="update road set status=? where startb=? and endb=?";
        int i = DBUtils.update(sql,1,startb,endb);
        return i>0 ? true:false;
    }

    @Override
    public boolean updRoad(String start, String end, int length) {
        BuildingsDaoImpl bd = new BuildingsDaoImpl();
        int startb = bd.queryBuildingID(start);
        int endb = bd.queryBuildingID(end);
        if(startb==0||endb==0){
            return false;
        }
        String sql="update road set length=? where startb=? and endb=? and status=?";
        int i = DBUtils.update(sql,length,startb,endb,0);
        return i>0 ? true:false;
    }

    @Override
    public RoadEntity[] queryRoadAll() {
        BuildingsDaoImpl bd = new BuildingsDaoImpl();
        String sql1="select * from road where status=?";
        List<Map<String, Object>> maps = DBUtils.queryMap(sql1,0);
        if(maps.size()>0){
            RoadEntity[] be=new RoadEntity[maps.size()];
            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> map = maps.get(i);
                be[i]=new RoadEntity();
                int num = Integer.parseInt(map.get("startb").toString());
                be[i].setStartb(new String(bd.queryName(num)));
                int num1 = Integer.parseInt(map.get("endb").toString());
                be[i].setEndb(new String(bd.queryName(num1)));
                int length=Integer.parseInt(map.get("length").toString());
                be[i].setLength(length);
                int id=Integer.parseInt(map.get("id").toString());
                be[i].setId(id);
            }
            return be;
        }
        return null;
    }
}
