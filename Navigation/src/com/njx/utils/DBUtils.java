package com.njx.utils;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DBUtils
 * Package: com.itheima.jdbc
 * Description:
 *  封装：操作数据库的工具类
 * @Author 南极星
 * @Create 2024/6/13 19:47
 * Version 1.0
 */
public class DBUtils {
    /*
    加载驱动，经过分析可得只需要加载一次
     */
    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static String URL="jdbc:mysql://localhost:3306/navigation?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    public static String USERNAME="root";
    public static String PASSWORD="abc123";
    /*
    封装创建连接的方法
    封装方法三要素 方法名 返回值 形参列表
     */
    public static Connection getConn(){
        Connection conn=null;
        try {
           conn=DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    /*
    通用的增删改的封装：
    参数列表：
      分析：增删改的sql语句不一样 sql语句当作参数
         结果：
           sql语句 参数
           sql语句中的占位符？所代表的参数
    返回值：
      分析：
          需要返回增删改的结果 成功/失败
          执行sql语句为executeUpdate（）此方法返回执行后影响的行数
          成功：行数至少是1
          小于1说明就是失败了
          结果：
          返回true/false
          1/-1

     */
    public static int update(String sql,Object...params){
        Connection conn=getConn();
        PreparedStatement ps =null;
        try {
            ps = conn.prepareStatement(sql);
           setParam(ps,params);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //调用通用的关闭方法
           closeAll(null,ps,conn);
        }
        return -1;
    }
    /*
    封装通用的关闭的方法
    方法名：closeAll()
    参数列表: ResultSet set,PreparedStatement ps,Connection conn
    返回值：不需要返回值
     */
    public static void closeAll(ResultSet set, PreparedStatement ps, Connection conn){
        try {
            if(set!=null){
                set.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(ps!=null){
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn!=null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /*
    封装通用的查询方法
    做数据的转储：把查询出的数据在程序别的地方使用
    List<Map<String,Object>>
     */
    public static List<Map<String,Object>> queryMap(String sql,Object... params){
        Connection conn=getConn();
        PreparedStatement ps =null;
        ResultSet set =null;
        try {
            ps = conn.prepareStatement(sql);
            setParam(ps,params);
            set = ps.executeQuery();
            ResultSetMetaData metaData = set.getMetaData();
            ArrayList<Map<String,Object>> maps = new ArrayList<>();
            while(set.next()){
                Map<String, Object> map = new HashMap<>();
                for (int i = 1; i <=metaData.getColumnCount(); i++) {
                    map.put(metaData.getColumnName(i),set.getObject(i));
                }
                maps.add(map);
            }
            return maps;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closeAll(set,ps,conn);
        }
        return null;
    }


/*
封装通用的设置参数的方法
 */
    public static void setParam(PreparedStatement ps,Object...params){
        try {
            if(params!=null){
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i+1,params[i]);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
    封装 新增时候 返回自增主键的方法
     */
    public static int getParmaryKey(String sql,Object...params){
        PreparedStatement ps =null;
        Connection conn=getConn();
        try {
            //预编译sql时，需要多传入一个参数
            ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            setParam(ps,params);
            ps.executeUpdate();
            //获取了新增的主键值
            ResultSet set = ps.getGeneratedKeys();
            if(set.next()){
                int i = set.getInt(1);
                return i;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;

    }

}
