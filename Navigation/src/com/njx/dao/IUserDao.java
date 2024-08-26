package com.njx.dao;

/**
 * ClassName: IUserDao
 * Package: com.njx.dao
 * Description:
 *
 * @Author 南极星
 * @Create 2024/6/15 13:00
 * Version 1.0
 */
public interface IUserDao {
    public boolean queryUser(String username);
    public boolean addUser(String username,String password);
    public String queryPassword(String username);
    public boolean updateUser(String username,String password);
}
