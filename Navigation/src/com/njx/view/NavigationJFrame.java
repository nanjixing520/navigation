package com.njx.view;

import com.njx.dao.BuildingsDaoImpl;
import com.njx.dao.RoadDaoImpl;
import com.njx.dao.UserDaoImpl;
import com.njx.entity.BuildingsEntity;
import com.njx.service.DijkstraImpl;
import com.njx.utils.StringUtils;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * ClassName: NavigationJFrame
 * Package: com.nanjiixng.ui
 * Description:
 *
 * @Author 南极星
 * @Create 2024/6/12 19:52
 * Version 1.0
 */
public class NavigationJFrame extends JFrame implements MouseListener{
    //有插件，需要刷新
    //JLabel maps =new JLabel(new ImageIcon("src//image//西邮地图.jpg"));
    //JLabel maps =new JLabel();
    JPanel maps=new JPanel();
    JButton queryr=new JButton("查询路径");
    JButton updb=new JButton("修改信息");
    JButton delb=new JButton("删除建筑");
    JButton addb=new JButton("添加建筑");
    JButton updr=new JButton("修改道路");
    JButton delr=new JButton("断开道路");
    JButton addr=new JButton("添加道路");
    JButton queryb=new JButton("查询地点");
    JButton updpassword=new JButton("修改密码");
    JTextField answer=new JTextField();
    JTextField introduce=new JTextField();
    JPanel jPanel2=new JPanel();
    JPanel jpanel=new JPanel();
    JPanel jPanel3=new JPanel();
    JPanel jpanel1=new JPanel();
    String[] ssee=new String[2];
    JPasswordField p1=new JPasswordField();
    JPasswordField p2=new JPasswordField();
    JPasswordField p3=new JPasswordField();
    String username=null;
    public NavigationJFrame() {
        //设置界面
        initJFrame();
        //设置组件
        initView();
        //设置按钮
        initButton();
        //将各建筑的图标展示出来
        initBuildings();
        //让界面显示出来，写在最后
        this.setVisible(true);
    }
    public NavigationJFrame(String username) {
        this();
        this.username=username;
    }

    private void initButton() {
        //设置按钮
        Font font=new Font(null,1,20);
        queryr.setFont(font);
        queryr.setForeground(Color.black);
        queryr.setBounds(1005,200,140,40);
        queryr.addMouseListener(this);
        this.getContentPane().add(queryr);
        updb.setFont(font);
        updb.setForeground(Color.black);
        updb.setBounds(1005, 280, 140, 40);
        updb.addMouseListener(this);
        this.getContentPane().add(updb);
        delb.setFont(font);
        delb.setForeground(Color.black);
        delb.setBounds(1005, 360, 140, 40);
        delb.addMouseListener(this);
        this.getContentPane().add(delb);
        addb.setFont(font);
        addb.setForeground(Color.black);
        addb.setBounds(1005, 440, 140, 40);
        addb.addMouseListener(this);
        this.getContentPane().add(addb);
        updr.setFont(font);
        updr.setForeground(Color.black);
        updr.setBounds(1005, 520, 140, 40);
        updr.addMouseListener(this);
        this.getContentPane().add(updr);
        delr.setFont(font);
        delr.setForeground(Color.black);
        delr.setBounds(1005, 600, 140, 40);
        delr.addMouseListener(this);
        this.getContentPane().add(delr);
        addr.setFont(font);
        addr.setForeground(Color.black);
        addr.setBounds(1005, 680, 140, 40);
        addr.addMouseListener(this);
        this.getContentPane().add(addr);
        queryb.setFont(font);
        queryb.setForeground(Color.black);
        queryb.setBounds(1005, 760, 140, 40);
        queryb.addMouseListener(this);
        this.getContentPane().add(queryb);
        updpassword.setFont(font);
        updpassword.setForeground(Color.black);
        updpassword.setBounds(1005, 840, 140, 40);
        updpassword.addMouseListener(this);
        this.getContentPane().add(updpassword);
        //在按钮下面为了填空加一张图片
        JLabel background=new JLabel(new ImageIcon("src//image//西柚.png"));
        background.setBounds(1000,900,150,270);
        this.getContentPane().add(background);
    }
    //用来初始化，刷新maps
    private void initBuildings(){
        //设置大地图面板内容
        maps.setBounds(0,0,1000,1000);
        maps.setOpaque(false);//设置为透明
        maps.setLayout(null);
        maps.setBorder(BorderFactory.createLineBorder(Color.black,3));
        this.getContentPane().add(maps);
        maps.removeAll();
        BuildingsDaoImpl buildingsDao=new BuildingsDaoImpl();
        for (BuildingsEntity buildingsEntity : buildingsDao.queryBuildings()) {
            //之前是在坐标处绘一个图片，注释之后是去绘制一个圆点
//            JLabel label = new JLabel(new ImageIcon("src//image/建筑.jpg"));
//            label.setSize(new Dimension(45,50));// 设置新JLabel的大小
//            label.setLocation(buildingsEntity.getX(), buildingsEntity.getY()); // 设置新JLabel的位置
//            label.setLayout(null);
            JPanel dot = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(Color.RED);
                    g.fillOval(0, 0, 25, 25); // 绘制一个半径为2的圆点，可以根据需要调整大小
                }
            };
            dot.setSize(new Dimension(25, 25)); // 设置小点的大小
            dot.setLocation(buildingsEntity.getX(), buildingsEntity.getY()); // 设置小点的位置
            dot.setLayout(null);
            //设置鼠标划过坐标就可以展示坐标位置的一个文本框
            JLabel label1 = new JLabel(" [ X:"+buildingsEntity.getX()+",Y:"+buildingsEntity.getY()+" ]");
            label1.setLayout(null);
            label1.setSize(new Dimension(250,30));// 设置新JLabel的大小
            label1.setFont(new Font("黑体",3,25));
            label1.setForeground(Color.orange);
            // 添加边框以便于看到面板
           // label1.setBorder(BorderFactory.createLineBorder(Color.blue,3));
            label1.setLocation(buildingsEntity.getX()+20, buildingsEntity.getY()); // 设置新JLabel的位置
            JLabel label2 = new JLabel(buildingsEntity.getName());
            label2.setLayout(null);
            label2.setSize(new Dimension(250,30));// 设置新JLabel的大小
            label2.setFont(new Font("黑体",1,20));
            label2.setForeground(Color.darkGray);
            label2.setLocation(buildingsEntity.getX()-10, buildingsEntity.getY()+30);
            maps.add(label2);
            dot.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    maps.add(label1);
                    // 非常重要！！！：使用SwingUtilities.invokeLater以确保在事件分发线程(EDT)上更新UI
                    SwingUtilities.invokeLater(() -> {
                        maps.revalidate();// 重验证以确保组件正确显示
                        maps.repaint();// 重绘map面板
                    });
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    if (label1 != null) {
                        // 从map面板中移除label1
                        maps.remove(label1);
                        SwingUtilities.invokeLater(() -> {
                            maps.revalidate();
                            maps.repaint();
                        });
                    }
                }
            });
            maps.add(dot);
        }
        //以下是添加路径的线
        RoadDaoImpl roadDao=new RoadDaoImpl();
        BuildingsDaoImpl buildingDao=new BuildingsDaoImpl();
        String[] strings = roadDao.queryStartb();
        String[] strings1 = roadDao.queryEndb();
        int x,y,x1,y1;
        for(int i=0;i<strings.length;i++){
            x=buildingDao.queryBuilding(strings[i]).getX();
            System.out.println(x);
            y=buildingDao.queryBuilding(strings[i]).getY();
            System.out.println(y);
            x1 = buildingDao.queryBuilding(strings1[i]).getX();
            System.out.println(x1);
            y1 = buildingDao.queryBuilding(strings1[i]).getY();
            System.out.println(y1);
            MyCanvas myCanvas=new MyCanvas(x, y, x1, y1);
            myCanvas.setLayout(null);
            //非常重要，没有这一行根本显示不出来
            //相当于是myCanvas是一个JPanel
            // 要说他在整个窗口的大小不然的话会默认为0，就加不进去绘图，虽然坐标已经确定了
            //在Swing中，组件的默认大小可能是0x0，
            // 因此即使添加到面板上也不会显示。需要确保为MyCanvas设置合适的大小。
            myCanvas.setBounds(0, 0,1000,1000); // 设置大小和位置
            JLabel label2 = new JLabel(roadDao.queryRoadLength(strings[i],strings1[i])+"米");
            System.out.println("路径长度："+roadDao.queryRoadLength(strings[i],strings1[i]));
            label2.setLayout(null);
            label2.setSize(new Dimension(250,30));// 设置新JLabel的大小
            label2.setFont(new Font(null,3,22));
            label2.setForeground(Color.RED);
            label2.setLocation((x+x1)/2+10,(y+y1)/2+10); // 设置新JLabel的位置
            maps.add(label2);
            maps.add(myCanvas);
        }
        SwingUtilities.invokeLater(() -> {
            maps.revalidate();// 重验证以确保组件正确显示
            maps.repaint();// 重绘map面板
        });
    }
    private void initView(){
        jpanel.removeAll();
        jpanel1.removeAll();
        jPanel2.removeAll();
        jPanel3.removeAll();
        //设置两个文本框面板(内容为出发地和目的地的信息)
        //使用了 null 作为布局管理器
        // jPanel2 上的组件将不会自动调整大小或位置以适应面板尺寸的变化。
        // 如果之后调整面板的大小，需要手动更新其上所有组件的位置和大小。
        //jPanel2.setLayout(null);
        jPanel2.setOpaque(true); // 设置面板背景透明
        jPanel2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4));
        jPanel2.setBounds(15,1010,480,130);
        JLabel jLabel2=new JLabel("出 发 地 信 息");
        jLabel2.setFont(new Font(null,1, 20));
        jLabel2.setForeground(Color.darkGray);
        jLabel2.setBounds(13,1030,480,100);
        jPanel2.add(jLabel2);
        //jPanel3.setLayout(null);
        jPanel3.setOpaque(true); // 设置面板背景透明
        jPanel3.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4));
        jPanel3.setBounds(510,1010,480,130);
        JLabel jLabel3=new JLabel("目 的 地 信 息");
        jLabel3.setFont(new Font(null,1, 20));
        jLabel3.setForeground(Color.darkGray);
        jLabel3.setBounds(510,1030,480,130);
        jPanel3.add(jLabel3);
        //设置出发地
        //设置面板
        JLabel jLabel=new JLabel("出发地");
        jLabel.setFont(new Font(null,1,20));
        RoadDaoImpl rd=new RoadDaoImpl();
        //获取道路数据库的起始地和目的地将其放入一个String数组中存储，展示在目的地和起始地的面板中
        String[] strings = rd.queryStartb();
        String[] strings1 = rd.queryEndb();
        if(strings!=null&&strings1!=null){
            String[] strings2 = StringUtils.mergeAndDeduplicate(strings, strings1);
            //创建出发地下拉框
            JComboBox<String> comboBox=new JComboBox<>(strings2);
            //设置宽度和高度：使用 setPreferredSize 方法来设置 JComboBox 的首选大小
            comboBox.setPreferredSize(new Dimension(120,40));
            //调整字体大小：增大 JComboBox 中的字体大小也会使下拉列表看起来更大。
            comboBox.setFont(new Font(null,1,20));
            //设置comboBox默认选中的内容
            comboBox.setSelectedIndex(1);
            ssee[0]=comboBox.getSelectedItem().toString();
            //为初始地jPanel2添加文本框
            JTextArea textArea=new JTextArea(5,20);

            //过时啦！！！还很卡顿

            //为JComboBox添加了一个 ItemListener。
            //  当用户选择下拉列表中的一个选项时，会触发 itemStateChanged 方法。
            //  ItemEvent 对象 e 提供了事件的详细信息。
            //  e.getStateChange() 返回一个整数，表示状态变化的类型。
            //  如果返回值是 ItemEvent.SELECTED，则表示一个选项被选中。
            comboBox.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("选中索引"+comboBox.getSelectedIndex()+"选中内容"+comboBox.getSelectedItem());
                    BuildingsDaoImpl buildingsDao=new BuildingsDaoImpl();
                    BuildingsEntity buildingsEntity = buildingsDao.queryBuilding(comboBox.getSelectedItem().toString());
                    ssee[0]=comboBox.getSelectedItem().toString();
                    textArea.setText("");
                    textArea.setEditable(false);//设置为不可编辑
                    textArea.setLineWrap(true);//开启自动换行
                    textArea.setWrapStyleWord(true);//开启启用了单词边界换行（word wrap）
                    textArea.setText(buildingsEntity.toString());
                    textArea.setBounds(15,1010,480,100);
                    textArea.setFont(new Font(null,1,20));
                    textArea.setForeground(Color.black);
                    textArea.setOpaque(false);
                    // textArea.setLocation(buildingsEntity.getX()+50, buildingsEntity.getY());
                    jPanel2.add(textArea);
                }
            });
            this.getContentPane().add(jPanel2);
            jpanel.add(jLabel);
            jpanel.add(comboBox);
            jpanel.setBounds(1005,10,140,70);
            this.getContentPane().add(jpanel);
            //选择目的地
            //设置面板
            JLabel jLabel1=new JLabel("目的地");
            jLabel1.setFont(new Font(null,1,20));
            JComboBox<String> comboBox1=new JComboBox<>(strings2);
            comboBox1.setPreferredSize(new Dimension(120,40));
            comboBox1.setFont(new Font(null,1,20));
            comboBox1.setSelectedIndex(1);
            ssee[1]=comboBox1.getSelectedItem().toString();
            //为初始地jPanel2添加文本框
            JTextArea textArea1=new JTextArea(5,20);
            comboBox1.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("选中索引"+comboBox1.getSelectedIndex()+"选中内容"+comboBox1.getSelectedItem());
                    BuildingsDaoImpl buildingsDao=new BuildingsDaoImpl();
                    BuildingsEntity buildingsEntity = buildingsDao.queryBuilding(comboBox1.getSelectedItem().toString());
                    ssee[1]=comboBox1.getSelectedItem().toString();
                    textArea1.setText("");
                    textArea1.setEditable(false);//设置为不可编辑
                    textArea1.setLineWrap(true);//开启自动换行
                    textArea1.setWrapStyleWord(true);//开启启用了单词边界换行（word wrap）
                    textArea1.setText(buildingsEntity.toString());
                    textArea1.setBounds(510,1010,480,100);
                    textArea1.setFont(new Font(null,1,20));
                    textArea1.setForeground(Color.black);
                    textArea1.setOpaque(false);
                    // textArea.setLocation(buildingsEntity.getX()+50, buildingsEntity.getY());
                    jPanel3.add(textArea1);
                }
            });
            this.getContentPane().add(jPanel3);
            jpanel1.add(jLabel1);
            jpanel1.add(comboBox1);
            jpanel1.setBounds(1005,90,140,70);
            this.getContentPane().add(jpanel1);
            SwingUtilities.invokeLater(() -> {
                jpanel.revalidate();// 重验证以确保组件正确显示
                jpanel.repaint();
                jpanel1.revalidate();// 重验证以确保组件正确显示
                jpanel1.repaint();
                jPanel2.revalidate();// 重验证以确保组件正确显示
                jPanel2.repaint();
                jPanel3.revalidate();// 重验证以确保组件正确显示
                jPanel3.repaint();
            });
        }
    }

    private void initJFrame(){
        //设置界面的标题
        this.setTitle("校园导航系统 v1.0");
        //设置界面的宽高
        this.setSize(1200, 1200);
        //设置背景色
        this.setBackground(Color.pink);
        //设置界面置顶，盖住其他软件
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(3);
        //取消默认的居中放置，只有取消了才会按照XY轴的形式添加组件
        this.setLayout(null);

    }
    //展示弹窗
    public void showJDialog(String content){
        JDialog jDialog=new JDialog();
        jDialog.setSize(400,150);
        jDialog.setTitle("温馨贴士");
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        //弹窗不关闭永远无法操作下面的界面
        jDialog.setModal(true);
        JLabel warning=new JLabel(content);
        warning.setBounds(0,0,200,150);
        warning.setFont(new Font(null,1,20));
        jDialog.getContentPane().add(warning);
        SwingUtilities.invokeLater(() -> {
            jDialog.revalidate();// 重验证以确保组件正确显示
            jDialog.repaint();// 重绘map面板
        });
        jDialog.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object obj=e.getSource();
        if(obj==addb){
            initBuildings();
            maps.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel label = new JLabel(new ImageIcon("src//image/建筑.jpg"));
                    label.setSize(new Dimension(45,50));// 设置新JLabel的大小
                    label.setLocation(e.getX(), e.getY()); // 设置新JLabel的位置
                    if(showJDialogAddb("该位置坐标-->X: " + e.getX() + ",Y: " + e.getY())>0){
                        BuildingsDaoImpl i=new BuildingsDaoImpl();
                        if(i.queryBuilding(e.getX(),e.getY())||i.queryBuilding(answer.getText())!=null){
                            showJDialog("该位置已有建筑或该建筑已被建造");
                            maps.removeMouseListener(this);
                            return;
                        }else{
                            if(i.addBuilding(e.getX(),e.getY(), answer.getText(), introduce.getText())){
                                showJDialog("添加成功");
                                initBuildings();
                                initView();
                            }else{
                                showJDialog("添加失败");
                                maps.removeMouseListener(this);
                                return;
                            }
                        }
                        // 将新JLabel添加到面板上
                        maps.add(label);
                        //重绘图形界面
                        maps.repaint();
                        // 记录坐标值，这里只是打印出来，你可以根据需要进行记录
                        System.out.println("Label added at X: " + e.getX() + ", Y: " + e.getY());
                    }
                    maps.removeMouseListener(this);
                }
            });
        }else if(obj==queryb){
            initBuildings();
            showJDialogQuerybName();
        }else if(obj==updb){
            initBuildings();
            //地图可能会变，建筑名字可能会变
           showJDialogUpdb("请输入要修改建筑的名称：");
        }else if(obj==delb){
            initBuildings();
            showJDialogDelb("请输入要删除建筑的X坐标：");
        }else if(obj==addr){
            initBuildings();
            showJDialogAddr();
        }else if(obj==delr){
            initBuildings();
            showJDialogDelr();
        }else if(obj==updr){
            initBuildings();
            showJDialogUpdr();
        }else if(obj==queryr){
            initBuildings();
            DijkstraImpl dijkstra=new DijkstraImpl();
            if(ssee[0].equals(ssee[1])){
                showJDialog("起始地和目的地不能相同！");
                return;
            }
            ArrayList<String> dijkstra1 = dijkstra.dijkstra(ssee[0], ssee[1]);
            if(dijkstra1==null){
                showJDialog("起始地和目的地无连通路径");
                return;
            }
            String[] a=new String[dijkstra1.size()];
            int i=dijkstra1.size();
            for(String d:dijkstra1){
                a[--i]=d;
            }
            showJDialogQueryr(a);
            BuildingsDaoImpl b=new BuildingsDaoImpl();
            for (int j = 0; j < a.length-1; j++) {
                int x = b.queryBuilding(a[j]).getX();
                int y = b.queryBuilding(a[j]).getY();
                int x1=b.queryBuilding(a[j+1]).getX();
                int y1=b.queryBuilding(a[j+1]).getY();
                MyCanvas1 canvas=new MyCanvas1(x,y,x1,y1);
                canvas.setLayout(null);
                canvas.setBounds(0,0,1000,1000);
                maps.add(canvas,1);
            }
            SwingUtilities.invokeLater(() -> {
                maps.revalidate();// 重验证以确保组件正确显示
                maps.repaint();// 重绘map面板
            });
        }else if(obj==updpassword){
            showJDialogUpdpassword();
        }
    }
    public int showJDialogAddb(String content){
        JDialog jDialog=new JDialog();
        jDialog.setSize(400,400);
        jDialog.setTitle("增加建筑--点右上角×号完成添加！");
        jDialog.setLayout(null);
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        //弹窗不关闭永远无法操作下面的界面
        jDialog.setModal(true);
        JLabel warning=new JLabel(content);
        warning.setBounds(0,0,400,40);
        warning.setFont(new Font(null,1,20));
        JLabel building=new JLabel("输入您要添加的建筑名称：");
        building.setBounds(20,60,400,40);
        building.setFont(new Font(null,1,20));
        //填入该建筑名称的文本框
        answer.setText("");
        answer.setBounds(20, 120, 200, 40);
        JLabel building1=new JLabel("输入该建筑的基本介绍：");
        building1.setBounds(20,180,400,40);
        building1.setFont(new Font(null,1,20));
        introduce.setText("");
        introduce.setBounds(20, 240, 200, 40);
//        JButton jButton=new JButton("确认");
//        jButton.setFont(new Font(null,1,20));
//        jButton.setBounds(20,300,120,50);
//        jButton.addActionListener(new AbstractAction() {
//              @Override
//              public void actionPerformed(ActionEvent e) {
//
//              }
//          }
//        );
        JButton jButton1=new JButton("取消");
        jButton1.setFont(new Font(null,1,20));
        jButton1.setBounds(70,300,220,50);
        jButton1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
            }
        });
        //jDialog.getContentPane().add(jButton);
        jDialog.getContentPane().add(jButton1);
        jDialog.getContentPane().add(building1);
        jDialog.getContentPane().add(introduce);
        jDialog.getContentPane().add(answer);
        jDialog.getContentPane().add(warning);
        jDialog.getContentPane().add(building);
        //让弹框展示出来
        jDialog.setVisible(true);
        return answer.getText().length();
    }

    private void showJDialogUpdpassword() {
        JDialog jDialog=new JDialog();
        jDialog.setSize(600,350);
        jDialog.setLayout(null);
        jDialog.setTitle("修改密码");
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        jDialog.setModal(true); // 弹窗不关闭永远无法操作下面的界面
        //初始化用户名密码的字体
        Font font=new Font(null,1,20);
        //JLabel这是一个管理容器，标签组件，用于显示文本或图标，
        // 将文本和图对象加入进来，将该管理容器加到界面

        //添加用户名文字
        JLabel usernameText=new JLabel("原密码");
        usernameText.setFont(font);
        usernameText.setForeground(Color.BLUE);
        usernameText.setBounds(140, 45, 100, 35);
        jDialog.getContentPane().add(usernameText);
        p1.setFont(new Font(null,1,20));
        p1.setBounds(243, 40, 200, 35);
        p1.setFont(font);
        jDialog.getContentPane().add(p1);
        //添加密码文字
        JLabel passwordText=new JLabel("新密码");
        passwordText.setFont(font);
        passwordText.setForeground(Color.BLUE);
        passwordText.setBounds(140, 90, 100, 35);
        jDialog.getContentPane().add(passwordText);
        JLabel passwordText1=new JLabel("再次输入");
        passwordText1.setFont(font);
        passwordText1.setForeground(Color.BLUE);
        passwordText1.setBounds(140, 125, 100, 35);
        jDialog.getContentPane().add(passwordText1);
        //添加密码输入框
        p2.setBounds(243, 85, 200, 35);
        p2.setFont(font);
        jDialog.getContentPane().add(p2);
        p3.setBounds(243, 125, 200, 35);
        p3.setFont(font);
        jDialog.getContentPane().add(p3);
        JButton login=new JButton("确 定");
        JButton cancel=new JButton("取 消");
        //添加登录按钮,新建登录按钮应该在类中，方便其他方法访问
        login.setFont(font);
        login.setForeground(Color.black);
        login.setBounds(123, 210, 128, 47);
        cancel.setFont(font);
        cancel.setForeground(Color.black);
        cancel.setBounds(345, 210, 128, 47);
        //login.setIcon(new ImageIcon("阳光温柔.jpg"));
//        //去除按钮背景
//        login.setContentAreaFilled(false);
//        //去除按钮边框
//        login.setBorderPainted(false);
        login.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDaoImpl userDao=new UserDaoImpl();
                String password = userDao.queryPassword(username);
                if(p1.getText().length()==0||p2.getText().length()==0||p3.getText().length()==0){
                    showJDialog("密码不能为空");
                    return;
                }
                if(!p1.getText().equals(password)){
                    showJDialog("原密码错误");
                    return;
                }
                if(p2.getText().length()>16||p2.getText().length()<2){
                    showJDialog("密码不能小于2位,不能长于16位");
                    return;
                }
                StringUtils stringUtils=new StringUtils();
                if(!stringUtils.isQuality(p2.getText())){
                    showJDialog("密码中有非法字符");
                    return;
                }
                if(!p2.getText().equals(p3.getText())){
                    showJDialog("密码输入不一致！");
                    return;
                }else if(userDao.updateUser(username,p2.getText())){
                    showJDialog("修改成功");
                }else showJDialog("修改失败");
            }
        });
        cancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
            }
        });
        jDialog.getContentPane().add(cancel);
        jDialog.getContentPane().add(login);
        jDialog.setVisible(true);
    }

    private void showJDialogQueryr(String[] a) {
            JDialog jDialog = new JDialog();
            jDialog.setSize(800, 150);
            jDialog.setTitle("最短路径路线");
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            jDialog.setModal(true); // 弹窗不关闭永远无法操作下面的界面
            // 将所有地点按顺序连接起来，中间用"-->"连接
            StringBuilder fullPath = new StringBuilder();
            for (String location : a) {
                fullPath.append(location).append("--> ");
            }
            // 移除最后一个多余的"--> "
            if (fullPath.length() > 0) {
                fullPath.setLength(fullPath.length() - 4);
            }

            // 创建一个JTextArea来显示完整的路径
            JTextArea textArea1 = new JTextArea(fullPath.toString(), 1, 9);
            textArea1.setEditable(false); // 设置为不可编辑
            textArea1.setLineWrap(true); // 开启自动换行
            textArea1.setWrapStyleWord(true); // 开启启用了单词边界换行（word wrap）
            textArea1.setFont(new Font(null, Font.BOLD, 30));
            textArea1.setForeground(Color.BLACK);
            textArea1.setBackground(Color.WHITE);
            textArea1.setOpaque(true); // 确保文本框背景是可见的

            jDialog.add(textArea1);
            SwingUtilities.invokeLater(() -> {
                jDialog.revalidate(); // 重验证以确保组件正确显示
                jDialog.repaint(); // 重绘对话框
            });
            jDialog.setVisible(true);
    }

    private void showJDialogUpdr() {
        String[] se = new String[2];
        JDialog jDialog6 = new JDialog();
        jDialog6.setSize(500,350);
        jDialog6.setLayout(null);
        jDialog6.setAlwaysOnTop(true);
        jDialog6.setTitle("更新路径长度");
        // 将对话框设置在屏幕中央
        jDialog6.setLocationRelativeTo(null);
        jDialog6.setModal(true);
        JPanel j = new JPanel();
        JPanel j1 = new JPanel();
        //设置出发地
        //设置面板
        JLabel jLabel=new JLabel("请选择待更新路径的出发地：");
        jLabel.setFont(new Font(null,1,20));
        BuildingsDaoImpl i=new BuildingsDaoImpl();
        String[] strings = i.queryNames();
        //创建出发地下拉框
        JComboBox<String> comboBox=new JComboBox(strings);
        //设置宽度和高度：使用 setPreferredSize 方法来设置 JComboBox 的首选大小
        comboBox.setPreferredSize(new Dimension(120,40));
        //调整字体大小：增大 JComboBox 中的字体大小也会使下拉列表看起来更大。
        comboBox.setFont(new Font(null,1,20));
        //设置comboBox默认选中的内容
        comboBox.setSelectedIndex(1);
        se[0] = comboBox.getSelectedItem().toString();

//        使用 ActionListener 替代 ItemListener：
//        如果只需在用户选择一个新项时立即做出反应，
//        可以考虑使用 ActionListener 而不是 ItemListener。
//        ActionListener 会在用户选择下拉列表中的某一项并点击时触发，相比之下更加直接和及时。
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                se[0] = comboBox.getSelectedItem().toString();
                System.out.println("选中内容：" + se[0]);
            }
        });
        //为JComboBox添加了一个 ItemListener。
        //  当用户选择下拉列表中的一个选项时，会触发 itemStateChanged 方法。
        //  ItemEvent 对象 e 提供了事件的详细信息。
        //  e.getStateChange() 返回一个整数，表示状态变化的类型。
        //  如果返回值是 ItemEvent.SELECTED，则表示一个选项被选中。

//        comboBox.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                //只处理选中的
//                if(e.getStateChange()==ItemEvent.SELECTED){
//                    System.out.println("选中索引"+comboBox.getSelectedIndex()+"选中内容"+comboBox.getSelectedItem());
//                    se[0]= comboBox.getSelectedItem().toString();
//                }
//            }
//        });
        j.add(jLabel);
        j.add(comboBox);
        j.setBounds(10,10,400,60);
        jDialog6.getContentPane().add(j);
//        //选择目的地
//        //设置面板
        JLabel jLabel1=new JLabel("请选择待更新路径的目的地：");
        jLabel1.setFont(new Font(null,1,20));
        //创建出发地下拉框
        JComboBox<String> comboBox1=new JComboBox(strings);
        //设置宽度和高度：使用 setPreferredSize 方法来设置 JComboBox 的首选大小
        comboBox1.setPreferredSize(new Dimension(120,40));
        //调整字体大小：增大 JComboBox 中的字体大小也会使下拉列表看起来更大。
        comboBox1.setFont(new Font(null,1,20));
        //设置comboBox默认选中的内容
        comboBox1.setSelectedIndex(1);
        se[1] = comboBox1.getSelectedItem().toString();
        //为JComboBox添加了一个 ItemListener。
        //  当用户选择下拉列表中的一个选项时，会触发 itemStateChanged 方法。
        //  ItemEvent 对象 e 提供了事件的详细信息。
        //  e.getStateChange() 返回一个整数，表示状态变化的类型。
        //  如果返回值是 ItemEvent.SELECTED，则表示一个选项被选中。
//        comboBox1.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                //只处理选中的
//                if(e.getStateChange()==ItemEvent.SELECTED){
//                    System.out.println("选中索引"+comboBox1.getSelectedIndex()+"选中内容"+comboBox1.getSelectedItem());
//                    se[1]= comboBox1.getSelectedItem().toString();
//                }
//            }
//        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                se[1] = comboBox1.getSelectedItem().toString();
                System.out.println("选中内容：" + se[1]);
            }
        });
        j1.add(jLabel1);
        j1.add(comboBox1);
        j1.setBounds(10,80,400,60);
        jDialog6.getContentPane().add(j1);
        JLabel jLabel2=new JLabel("请输入两地点间最新距离：");
        jLabel2.setFont(new Font(null,1,20));
        jLabel2.setBounds(17,165,250,30);
        jDialog6.getContentPane().add(jLabel2);
        JTextField textField=new JTextField();
        textField.setFont(new Font(null,1,20));
        textField.setBounds(265,165,140,40);
        jDialog6.getContentPane().add(textField);
        JButton jButton=new JButton("确定修改");
        jButton.setFont(new Font(null,1,20));
        jButton.setBounds(50,230,120,50);
        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i;
                int sum=0;
                try {
                    i = Integer.parseInt(textField.getText());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    sum++;
                }
                String start=se[0];
                String end=se[1];
                if(start==end){
                    showJDialog("起始地和目的地不能相同");
                } else if(textField.getText().length()==0){
                    showJDialog("距离不能为空！");
                } else if (sum!=0) {
                    showJDialog("⚠：输入错误❌");
                } else{
                    i = Integer.parseInt(textField.getText());
                    RoadDaoImpl rd=new RoadDaoImpl();
                    if(i<=0)  showJDialog("路径不能为负数或0");
                    else if(i>=10000){
                        showJDialog("距离不能太大超过10千米！");
                    }
                    if (rd.queryRoad(start, end)) {
                        showJDialog("更新成功");
                        rd.updRoad(start,end,i);
                        initBuildings();
                        initView();
                    } else if (rd.queryRoad(end, start)) {
                        showJDialog("更新成功");
                        rd.updRoad(start,end,i);
                        initBuildings();
                        initView();
                    }else{
                        showJDialog("没有该路径");
                    }
                }
            }
        });
        jDialog6.getContentPane().add(jButton);
        JButton jButton1=new JButton("取消");
        jButton1.setFont(new Font(null,1,20));
        jButton1.setBounds(255,230,120,50);
        jButton1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog6.dispose();
            }
        });
        jDialog6.getContentPane().add(jButton1);
        jDialog6.setVisible(true);
    }

    private void showJDialogDelr(){
        String[] se = new String[2];
        JDialog jDialog6 = new JDialog();
        jDialog6.setSize(500,350);
        jDialog6.setLayout(null);
        jDialog6.setAlwaysOnTop(true);
        jDialog6.setTitle("删除路径");
        // 将对话框设置在屏幕中央
        jDialog6.setLocationRelativeTo(null);
        jDialog6.setModal(true);
        JPanel j = new JPanel();
        JPanel j1 = new JPanel();
        //设置出发地
        //设置面板
        JLabel jLabel=new JLabel("请选择待删除的出发地：");
        jLabel.setFont(new Font(null,1,20));
        BuildingsDaoImpl i=new BuildingsDaoImpl();
        String[] strings = i.queryNames();
        //创建出发地下拉框
        JComboBox<String> comboBox=new JComboBox(strings);
        //设置宽度和高度：使用 setPreferredSize 方法来设置 JComboBox 的首选大小
        comboBox.setPreferredSize(new Dimension(120,40));
        //调整字体大小：增大 JComboBox 中的字体大小也会使下拉列表看起来更大。
        comboBox.setFont(new Font(null,1,20));
        //设置comboBox默认选中的内容
        comboBox.setSelectedIndex(1);
//        使用 ActionListener 替代 ItemListener：
//        如果只需在用户选择一个新项时立即做出反应，
//        可以考虑使用 ActionListener 而不是 ItemListener。
//        ActionListener 会在用户选择下拉列表中的某一项并点击时触发，相比之下更加直接和及时。
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                se[0] = comboBox.getSelectedItem().toString();
                System.out.println("选中内容：" + se[0]);
            }
        });
        j.add(jLabel);
        j.add(comboBox);
        j.setBounds(10,10,400,60);
        jDialog6.getContentPane().add(j);
//        //选择目的地
//        //设置面板
        JLabel jLabel1=new JLabel("请选择待删除的目的地：");
        jLabel1.setFont(new Font(null,1,20));
        //创建出发地下拉框
        JComboBox<String> comboBox1=new JComboBox(strings);
        //设置宽度和高度：使用 setPreferredSize 方法来设置 JComboBox 的首选大小
        comboBox1.setPreferredSize(new Dimension(120,40));
        //调整字体大小：增大 JComboBox 中的字体大小也会使下拉列表看起来更大。
        comboBox1.setFont(new Font(null,1,20));
        //设置comboBox默认选中的内容
        comboBox1.setSelectedIndex(1);
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                se[1] = comboBox1.getSelectedItem().toString();
                System.out.println("选中内容：" + se[1]);
            }
        });
        j1.add(jLabel1);
        j1.add(comboBox1);
        j1.setBounds(10,80,400,60);
        jDialog6.getContentPane().add(j1);
        JButton jButton=new JButton("确定删除");
        jButton.setFont(new Font(null,1,20));
        jButton.setBounds(50,230,120,50);
        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start=se[0];
                String end=se[1];
                if (start == end) {
                    showJDialog("起始地和目的地不能相同");
                } else {
                    RoadDaoImpl rd = new RoadDaoImpl();
                    if (rd.queryRoad(start, end)) {
                        showJDialog("删除成功");
                        rd.delRoad(start, end);
                        initBuildings();
                        initView();
                    } else if (rd.queryRoad(end, start)) {
                        showJDialog("删除成功");
                        rd.delRoad(end, start);
                        initBuildings();
                        initView();
                    } else {
                        showJDialog("没有该路径");
                    }
                }
            }
        });
        jDialog6.getContentPane().add(jButton);
        JButton jButton1=new JButton("取消");
        jButton1.setFont(new Font(null,1,20));
        jButton1.setBounds(255,230,120,50);
        jButton1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog6.dispose();
            }
        });
        jDialog6.getContentPane().add(jButton1);
        jDialog6.setVisible(true);
    }

    /**
     * 为了增加新路径
     */
    private void showJDialogAddr() {
        String[] se = new String[2]; // 使用数组包装字符串，可以使其"有效地"等同于 final
        JDialog jDialog6 = new JDialog();
        jDialog6.setSize(500,350);
        jDialog6.setLayout(null);
        jDialog6.setAlwaysOnTop(true);
        jDialog6.setTitle("增加路径");
        // 将对话框设置在屏幕中央
        jDialog6.setLocationRelativeTo(null);
        jDialog6.setModal(true);
        JPanel j = new JPanel();
        JPanel j1 = new JPanel();
        //设置出发地
        //设置面板
        JLabel jLabel=new JLabel("请选择待添加的出发地：");
        jLabel.setFont(new Font(null,1,20));
        BuildingsDaoImpl i=new BuildingsDaoImpl();
        String[] strings = i.queryNames();
        //创建出发地下拉框
        JComboBox<String> comboBox=new JComboBox(strings);
        //设置宽度和高度：使用 setPreferredSize 方法来设置 JComboBox 的首选大小
        comboBox.setPreferredSize(new Dimension(120,40));
        //调整字体大小：增大 JComboBox 中的字体大小也会使下拉列表看起来更大。
        comboBox.setFont(new Font(null,1,20));
        //设置comboBox默认选中的内容
        comboBox.setSelectedIndex(1);
        se[0] = comboBox.getSelectedItem().toString();

//        使用 ActionListener 替代 ItemListener：
//        如果只需在用户选择一个新项时立即做出反应，
//        可以考虑使用 ActionListener 而不是 ItemListener。
//        ActionListener 会在用户选择下拉列表中的某一项并点击时触发，相比之下更加直接和及时。
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                se[0] = comboBox.getSelectedItem().toString();
                System.out.println("选中内容：" + se[0]);
            }
        });
        //为JComboBox添加了一个 ItemListener。
        //  当用户选择下拉列表中的一个选项时，会触发 itemStateChanged 方法。
        //  ItemEvent 对象 e 提供了事件的详细信息。
        //  e.getStateChange() 返回一个整数，表示状态变化的类型。
        //  如果返回值是 ItemEvent.SELECTED，则表示一个选项被选中。

//        comboBox.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                //只处理选中的
//                if(e.getStateChange()==ItemEvent.SELECTED){
//                    System.out.println("选中索引"+comboBox.getSelectedIndex()+"选中内容"+comboBox.getSelectedItem());
//                    se[0]= comboBox.getSelectedItem().toString();
//                }
//            }
//        });
        j.add(jLabel);
        j.add(comboBox);
        j.setBounds(10,10,400,60);
        jDialog6.getContentPane().add(j);
//        //选择目的地
//        //设置面板
        JLabel jLabel1=new JLabel("请选择待添加的目的地：");
        jLabel1.setFont(new Font(null,1,20));
        //创建出发地下拉框
        JComboBox<String> comboBox1=new JComboBox(strings);
        //设置宽度和高度：使用 setPreferredSize 方法来设置 JComboBox 的首选大小
        comboBox1.setPreferredSize(new Dimension(120,40));
        //调整字体大小：增大 JComboBox 中的字体大小也会使下拉列表看起来更大。
        comboBox1.setFont(new Font(null,1,20));
        //设置comboBox默认选中的内容
        comboBox1.setSelectedIndex(1);
        se[1] = comboBox1.getSelectedItem().toString();
        //为JComboBox添加了一个 ItemListener。
        //  当用户选择下拉列表中的一个选项时，会触发 itemStateChanged 方法。
        //  ItemEvent 对象 e 提供了事件的详细信息。
        //  e.getStateChange() 返回一个整数，表示状态变化的类型。
        //  如果返回值是 ItemEvent.SELECTED，则表示一个选项被选中。
//        comboBox1.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                //只处理选中的
//                if(e.getStateChange()==ItemEvent.SELECTED){
//                    System.out.println("选中索引"+comboBox1.getSelectedIndex()+"选中内容"+comboBox1.getSelectedItem());
//                    se[1]= comboBox1.getSelectedItem().toString();
//                }
//            }
//        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                se[1] = comboBox1.getSelectedItem().toString();
                System.out.println("选中内容：" + se[1]);
            }
        });
        j1.add(jLabel1);
        j1.add(comboBox1);
        j1.setBounds(10,80,400,60);
        jDialog6.getContentPane().add(j1);
        JLabel jLabel2=new JLabel("请输入两地点间距离：");
        jLabel2.setFont(new Font(null,1,20));
        jLabel2.setBounds(40,165,200,30);
        jDialog6.getContentPane().add(jLabel2);
        JTextField textField=new JTextField();
        textField.setFont(new Font(null,1,20));
        textField.setBounds(245,165,150,40);
        jDialog6.getContentPane().add(textField);
        JButton jButton=new JButton("确定添加");
        jButton.setFont(new Font(null,1,20));
        jButton.setBounds(50,230,120,50);
        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i;
                int sum=0;
                try {
                    i = Integer.parseInt(textField.getText());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    sum++;
                }
                String start=se[0];
                String end=se[1];
                if(start==end){
                    showJDialog("起始地和目的地不能相同");
                } else if(textField.getText().length()==0){
                    showJDialog("距离不能为空！");
                } else if (sum!=0) {
                    showJDialog("⚠：输入错误❌");
                } else{
                    i = Integer.parseInt(textField.getText());
                    RoadDaoImpl rd=new RoadDaoImpl();
                    if(rd.queryRoad(start,end)||rd.queryRoad(end,start)){
                        showJDialog("该路径已存在！");
                    }else{
                        if(i<=0)  showJDialog("路径不能为负数或0");
                        else if(i>=10000){
                            showJDialog("距离不能太大超过10千米！");
                        }
                        else if(rd.addRoad(start,end,i)){
                            showJDialog("添加成功");
                            initBuildings();
                            initView();
                        }else{
                            showJDialog("添加失败");
                        }
                    }
                }
                }
        });
        jDialog6.getContentPane().add(jButton);
        JButton jButton1=new JButton("取消");
        jButton1.setFont(new Font(null,1,20));
        jButton1.setBounds(255,230,120,50);
        jButton1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog6.dispose();
            }
        });
        jDialog6.getContentPane().add(jButton1);
        jDialog6.setVisible(true);
    }

    /**
     * 该方法为删除建筑而写弹窗
     * @param content
     */
    private void showJDialogDelb(String content){
        JDialog jDialog = new JDialog();
        jDialog.setSize(440, 260);
        jDialog.setTitle("删除建筑");
        jDialog.setLayout(null);
        jDialog.setAlwaysOnTop(true);
        // 将对话框设置在屏幕中央
        jDialog.setLocationRelativeTo(null);
        //弹窗不关闭永远无法操作下面的界面
        jDialog.setModal(true);
        JLabel warning = new JLabel(content);
        warning.setBounds(10, 10, 280, 25);
        warning.setFont(new Font(null, 1, 20));
        JTextField jTextField = new JTextField();
        jTextField.setBounds(300, 10, 100, 25);
        JLabel building = new JLabel("请输入要删除建筑的Y坐标：");
        building.setBounds(10, 60, 280, 25);
        building.setFont(new Font(null, 1, 20));
        JTextField jTextField1 = new JTextField();
        jTextField1.setBounds(300, 60, 100, 25);
        jDialog.add(jTextField);
        jDialog.add(jTextField1);
        jDialog.add(warning);
        jDialog.add(building);
        BuildingsDaoImpl buildingsDao = new BuildingsDaoImpl();
        JButton button = new JButton("确认删除");
        button.setFont(new Font(null, 1, 20));
        button.setForeground(Color.black);
        button.setBounds(40,130,120,50);
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i, j;
                int sum = 0;
                try {
                    i = Integer.parseInt(jTextField.getText());
                } catch (NumberFormatException ex) {
                    sum++;
                    ex.printStackTrace();
                }
                try {
                    j = Integer.parseInt(jTextField1.getText());
                } catch (NumberFormatException ex) {
                    sum++;
                    ex.printStackTrace();
                }
                if (sum != 0) {
                    showJDialog("建筑的坐标不能不填");
                    return;
                }
                i = Integer.parseInt(jTextField.getText());
                j = Integer.parseInt(jTextField1.getText());
                if(sum==0&&buildingsDao.queryBuilding(i,j)){
                    String s = buildingsDao.queryBuilding1(i, j);
                    int id = buildingsDao.queryBuildingID(s);
                    if (buildingsDao.delBuilding(i, j)) {
                        showJDialog("删除成功");
                        RoadDaoImpl roadDao = new RoadDaoImpl();
                        roadDao.deleteRoad(id);
                        roadDao.deleteRoad1(id);
                        initBuildings();
                        initView();
                    }else{
                        showJDialog("删除失败");
                    }
                } else {
                    showJDialog("该位置处没有建筑");
                }
            }
        });
        jDialog.getContentPane().add(button);
        JButton jButton1=new JButton("取消");
        jButton1.setFont(new Font(null,1,20));
        jButton1.setBounds(180,130,120,50);
        jButton1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
            }
        });
        jDialog.getContentPane().add(jButton1);
        //试过的错误的方法
//        //让弹框展示出来
//        SwingUtilities.invokeLater(() -> {
//            maps.revalidate();// 重验证以确保组件正确显示
//            maps.repaint();// 重绘map面板
//        });
//        map.removeAll();// 重验证以确保组件正确显示
//        shuffle();// 重绘map面板
        //里面是刷新maps，删除掉maps的组件然后重新加载
        jDialog.setVisible(true);
    }

    /**
     * 更新建筑信息
     * @param content
     */
    private void showJDialogUpdb(String content){
        JDialog jDialog=new JDialog();
        jDialog.setSize(800,400);
        jDialog.setTitle("修改建筑信息");
        jDialog.setLayout(null);
        jDialog.setAlwaysOnTop(true);
        //？
        jDialog.setLocationRelativeTo(null);
        //弹窗不关闭永远无法操作下面的界面
        jDialog.setModal(true);
        JLabel warning=new JLabel(content);
        warning.setBounds(10,10,400,25);
        warning.setFont(new Font(null,1,20));
        JTextField jTextField=new JTextField();
        jTextField.setBounds(510,10,200,25);
        JLabel building=new JLabel("请输入新X坐标");
        building.setBounds(10,60,400,25);
        building.setFont(new Font(null,1,20));
        JTextField jTextField1=new JTextField();
        jTextField1.setBounds(510,60,200,25);
        JLabel building1=new JLabel("请输入新Y坐标");
        building1.setBounds(10,120,400,25);
        building1.setFont(new Font(null,1,20));
        JTextField jTextField2=new JTextField();
        jTextField2.setBounds(510,120,200,25);
        JLabel building2=new JLabel("请输入新介绍：");
        building2.setBounds(10,180,400,25);
        building2.setFont(new Font(null,1,20));
        JTextField jTextField3=new JTextField();
        jTextField3.setBounds(510,180,200,25);
        JButton button=new JButton("修改");
        button.setFont(new Font(null,1,30));
        button.setForeground(Color.black);
        button.setBounds(200, 290, 128, 47);
        JButton jButton1=new JButton("取消");
        jButton1.setFont(new Font(null,1,20));
        jButton1.setBounds(500,290,128,47);
        jButton1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
            }
        });
        jDialog.getContentPane().add(jButton1);
        BuildingsDaoImpl buildingsDao=new BuildingsDaoImpl();
        jDialog.add(warning);
        jDialog.add(building);
        jDialog.add(building1);
        jDialog.add(building2);
        jDialog.add(jTextField);
        jDialog.add(jTextField1);
        jDialog.add(jTextField2);
        jDialog.add(jTextField3);
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i,j;
                int sum=0;
                try {
                    i= Integer.parseInt(jTextField1.getText());
                } catch (NumberFormatException ex) {
                    sum++;
                    ex.printStackTrace();
                }
                try {
                    j = Integer.parseInt(jTextField2.getText());
                } catch (NumberFormatException ex) {
                    sum++;
                    ex.printStackTrace();
                }
                if(sum!=0){
                    showJDialog("建筑的坐标不能不填");
                    return;
                }
                i= Integer.parseInt(jTextField1.getText());
                j = Integer.parseInt(jTextField2.getText());
                if(buildingsDao.queryBuilding(jTextField.getText())!=null&&sum==0){
                    if(i<=0||j<=0||i>=1000||j>=1000){
                        showJDialog("建筑坐标不合法！");
                        return;
                    }else if(buildingsDao.queryBuilding(i,j)){
                        showJDialog("该位置已经有建筑");
                        return;
                    }
                    else if(buildingsDao.updBuilding1(i,j,jTextField.getText(),jTextField3.getText())){
                        showJDialog("更新成功");
                        initBuildings();
                        initView();
                    }else{
                        showJDialog("更新失败");
                    }
                }else{
                    showJDialog("没有该建筑");
                }
            }
        });
        jDialog.getContentPane().add(button);
        //让弹框展示出来
        jDialog.setVisible(true);
    }

    private void showJDialogQueryb(String name){
        JDialog jDialog=new JDialog();
        jDialog.setSize(400,200);
        jDialog.setTitle("建筑信息");
        jDialog.setLayout(null);
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        //弹窗不关闭永远无法操作下面的界面
        jDialog.setModal(true);
        if(name.length()>0){
            BuildingsDaoImpl buildingsDao=new BuildingsDaoImpl();
            BuildingsEntity buildingsEntity= buildingsDao.queryBuilding(name);
            if(buildingsEntity!=null){
                JLabel building=new JLabel(buildingsEntity.getName()+"的坐标-->X:"+buildingsEntity.getX()+" Y:"+buildingsEntity.getY());
                building.setBounds(20,10,400,40);
                building.setFont(new Font(null,1,20));
                jDialog.getContentPane().add(building);
                JLabel building1=new JLabel(buildingsEntity.getName()+"的介绍:"+buildingsEntity.getIntroduce());
                building1.setBounds(20,70,400,40);
                building1.setFont(new Font(null,1,20));
                jDialog.getContentPane().add(building1);
            }else{
                JLabel building=new JLabel("没有该建筑");
                building.setBounds(30,40,300,40);
                building.setFont(new Font(null,1,40));
                jDialog.getContentPane().add(building);
            }
        }else return;
        //让弹框展示出来
        SwingUtilities.invokeLater(() -> {
            // 这里放置需要在EDT上执行的代码
            jDialog.revalidate();
            jDialog.repaint();
        });
        jDialog.setVisible(true);

    }

    //查询建筑名称输入建筑名的窗口
    private void  showJDialogQuerybName() {
        JDialog jDialog=new JDialog();
        jDialog.setSize(400,250);
        jDialog.setTitle("建筑信息");
        jDialog.setLayout(null);
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        //弹窗不关闭永远无法操作下面的界面
        jDialog.setModal(true);
        JLabel building=new JLabel("输入您要查找的建筑名称：");
        building.setBounds(10,0,400,40);
        building.setFont(new Font(null,1,20));
        //填入该建筑名称的文本框
        JTextField answer1=new JTextField();
        answer1.setBounds(20, 60, 200, 40);
        jDialog.getContentPane().add(answer1);
        jDialog.getContentPane().add(building);
        JButton jButton=new JButton("查询");
        jButton.setFont(new Font(null,1,20));
        jButton.setBounds(40,140,120,50);
        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(answer1.getText().length()>0){
                    showJDialogQueryb(answer1.getText());
                }else showJDialog("建筑名称不能为空");

            }
        });
        JButton jButton1=new JButton("取消");
        jButton1.setFont(new Font(null,1,20));
        jButton1.setBounds(180,140,120,50);
        jButton1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
            }
        });
        jDialog.getContentPane().add(jButton);
        jDialog.getContentPane().add(jButton1);
        //让弹框展示出来
        jDialog.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        Object obj=e.getSource();
//        if(obj==addb){
//            System.out.println("按压");
//        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        Object obj=e.getSource();
//        if(obj==addb){
//            System.out.println("释放");
//        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {
//        Object obj=e.getSource();
//        if(obj==addb){
//            System.out.println("退出");
//        }
    }
}

