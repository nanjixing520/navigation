package com.njx.entity;

/**
 * ClassName: RoadEntity
 * Package: com.njx.entity
 * Description:
 *
 * @Author 南极星
 * @Create 2024/6/17 15:51
 * Version 1.0
 */
public class RoadEntity {
    private int id;
    private String startb;
    private String endb;
    private int length;
    public RoadEntity() {
    }

    public RoadEntity(int id, String endb, String startb, int length) {
        this.id = id;
        this.endb = endb;
        this.startb = startb;
        this.length = length;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartb() {
        return startb;
    }

    public void setStartb(String startb) {
        this.startb = startb;
    }

    public String getEndb() {
        return endb;
    }

    public void setEndb(String endb) {
        this.endb = endb;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "RoadEntity{" +
                "id=" + id +
                ", startb='" + startb + '\'' +
                ", endb='" + endb + '\'' +
                ", length=" + length +
                '}';
    }

}
