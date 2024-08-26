package com.njx.entity;

import com.njx.dao.BuildingsDaoImpl;

/**
 * ClassName: BuildingsEntity
 * Package: com.njx.entity
 * Description:
 *
 * @Author 南极星
 * @Create 2024/6/16 16:34
 * Version 1.0
 */
public class BuildingsEntity {
    private String name;
    private int x;
    private int y;
    private String introduce;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BuildingsEntity(){

    }
    public BuildingsEntity(int x, int y, String name, String introduce) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return " 该建筑名称：" + name + '\n' +
                " 该建筑坐标：x= " + x +
                " , y= " + y +
                "\n 该建筑信息介绍: " + introduce + '\n' ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
