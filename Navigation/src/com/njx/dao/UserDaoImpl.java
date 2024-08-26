package com.njx.dao;

import com.njx.utils.DBUtils;

import java.util.List;
import java.util.Map;

/**
 * ClassName: UserDaoImpl
 * Package: com.njx.dao
 * Description:
 *
 * @Author 南极星
 * @Create 2024/6/15 13:01
 * Version 1.0
 */
public class UserDaoImpl implements IUserDao{
    //判断是否存在该用户
    @Override
    public boolean queryUser(String username) {
        String sql="select * from user where username=? and status=0";
        List<Map<String, Object>> maps = DBUtils.queryMap(sql, username);
        return maps.size()>0 ? true:false;
    }
    //添加新用户
    @Override
    public boolean addUser(String username,String password) {
        String sql1="insert into user values(null,?,?,0)";
        int i = DBUtils.update(sql1, username,password);
        return i>0 ? true:false;
    }

    @Override
    public String queryPassword(String username) {
        String sql="select password from user where username=? and status=0";
        List<Map<String, Object>> maps = DBUtils.queryMap(sql, username);
        if(maps.size()>0){
            return maps.get(0).get("password").toString();
        }
        return null;
    }

    @Override
    public boolean updateUser(String username, String password) {
        String sql1="update user set password=? where username=? and status=0";
        int i = DBUtils.update(sql1, password, username);
        return i>0 ? true:false;
    }

    //判断用户账号和密码是否正确
    public boolean queryUser1(String username,String password){
        String sql="select * from user where username=? and status=0";
        List<Map<String, Object>> maps = DBUtils.queryMap(sql, username);
        if(maps.size()>0){
            Map<String, Object> map = maps.get(0);
            return password.equals((String)map.get("password"));
        }
            return false;
    }

}