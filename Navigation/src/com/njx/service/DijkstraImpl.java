package com.njx.service;
import com.mysql.cj.util.TestUtils;
import com.njx.dao.BuildingsDaoImpl;
import com.njx.dao.RoadDaoImpl;
import com.njx.entity.BuildingsEntity;
import com.njx.entity.RoadEntity;
import org.junit.Test;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;
/**
 * ClassName: DijkstraImpl
 * Package: com.njx.service
 * Description:
 *   最短路径算法
 * @Author 南极星
 * @Create 2024/6/19 22:03
 * Version 1.0
 */
public class DijkstraImpl {
    static final int MAXVALUE=9999999;
    private int[][] graph;
    private int numNodes;
    private int numEdges;
    RoadEntity[] roadEntities;
    BuildingsEntity[] buildingsEntities;
    BuildingsDaoImpl dao = new BuildingsDaoImpl();
    RoadDaoImpl roadDao = new RoadDaoImpl();
    private int[] s;
    private int[] dist;
    private int[] path;
    private int min;
    HashMap<String,Integer> hashMap = new HashMap();
    public DijkstraImpl() {
        this.numNodes=dao.queryBuildings().length;
        this.numEdges=roadDao.queryRoadAll().length;
        this.graph=new int[numNodes][numNodes];
        this.roadEntities = roadDao.queryRoadAll();
        this.buildingsEntities=dao.queryBuildings();
        this.s = new int[numNodes];
        this.dist = new int[numNodes];
        this.path = new int[numNodes];
        for(int i=0;i<numNodes;i++){
            hashMap.put(buildingsEntities[i].getName(),i);
            System.out.println(buildingsEntities[i].getName());
        }
    }
    public ArrayList<String> dijkstra(String start, String end) {
        int startb=hashMap.get(start);
        int endb= hashMap.get(end);
        //邻接矩阵初始化
        for (int i = 0; i <numNodes; i++) {
            for (int j = 0; j <numNodes; j++) {
                if(i==j) this.graph[i][j]=0;
                else this.graph[i][j]=MAXVALUE;
            }
        }
        for (int i = 0; i <numEdges; i++) {
            int ids = hashMap.get(roadEntities[i].getStartb());
            int ide=hashMap.get(roadEntities[i].getEndb());
            int length=roadEntities[i].getLength();
            this.graph[ids][ide]=length;
            this.graph[ide][ids]=length;
        }
        for (int i = 0; i <numNodes; i++) {
            dist[i]=graph[startb][i];
            if(dist[i]!=MAXVALUE&&dist[i]!=0){
                min=dist[i];
            }
            if(dist[i]!=MAXVALUE){
                path[i]=startb;
            }
            else{
                path[i]=-1;
            }
        }
        s[startb]=1;
        int num=1;
        while(num<numNodes){
            min=MAXVALUE;
            int mini=-1;
            for (int i = 0; i <dist.length; i++) {
                if(s[i]==0&&dist[i]<=min){
                    min=Math.min(min,dist[i]);
                    mini=i;
                }
                //为了确保有孤立点也可以去查找路径，有两种方法
                //一种就是以下这种已知它是孤立点距离为无穷大，那么无需考虑是否这条路会为其他路构造更短路径，将其放入s数组定义为已考虑过
                //一种就是dist[i]<min加上等于，不管怎样都考虑一遍，避免出现mini=-1
//                if(s[i] == 0 && dist[i] == MAXVALUE) {
//                    s[i] = 1;
//                    num += 1;
//                }
           }
            s[mini]=1;
            for (int i = 0; i <numNodes ; i++) {
                if(s[i]==0&&(dist[i]>dist[mini]+graph[mini][i])){
                    dist[i]=dist[mini]+graph[mini][i];
                    path[i]=mini;
                }
            }
            num++;
        }
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add(end);
        int i=hashMap.get(end);
        while(i!=hashMap.get(start)){
            if(path[i]!=-1){
                arrayList.add(buildingsEntities[path[i]].getName());
                i=path[i];
            }else return null;
        }
        return arrayList;
        //为了测试路径是否正确
//        System.out.println("最短路径为："+dist[hashMap.get(end)]);
//        int i=hashMap.get(end);
//        System.out.print("路径为："+i+" ");
//        while(i!=hashMap.get(start)){
//            System.out.print("路径为："+path[i]+" ");
//            i=path[i];
//        }
    }
    @Test
    public void test() {
       int[] a=new int[3];
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }

    }
}
