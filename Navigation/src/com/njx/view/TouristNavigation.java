package com.njx.view;

import com.njx.dao.BuildingsDaoImpl;
import com.njx.dao.RoadDaoImpl;
import com.njx.entity.BuildingsEntity;
import com.njx.service.DijkstraImpl;
import com.njx.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * ClassName: TouristNavigation
 * Package: com.njx.view
 * Description:
 *
 * @Author 南极星
 * @Create 2024/6/20 9:23
 * Version 1.0
 */
public class TouristNavigation extends JFrame implements MouseListener {
    JPanel maps = new JPanel();
    JButton queryr = new JButton("查询路径");
    JButton queryb = new JButton("查询地点");
    JTextField answer = new JTextField();
    JTextField introduce = new JTextField();
    JPanel jPanel2 = new JPanel();
    JPanel jpanel = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jpanel1 = new JPanel();
    String[] ssee = new String[2];

    public TouristNavigation() {
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

    private void initButton() {
        //设置按钮
        Font font = new Font(null, 1, 20);
        queryr.setFont(font);
        queryr.setForeground(Color.black);
        queryr.setBounds(1005, 200, 140, 40);
        queryr.addMouseListener(this);
        this.getContentPane().add(queryr);
        queryb.setFont(font);
        queryb.setForeground(Color.black);
        queryb.setBounds(1005, 280, 140, 40);
        queryb.addMouseListener(this);
        this.getContentPane().add(queryb);
        JLabel background = new JLabel(new ImageIcon("src//image//阿尼亚跳跃.jpg"));
        background.setBounds(1005, 340, 180, 766);
        this.getContentPane().add(background);
    }

    private void initBuildings() {
        maps.setBounds(0, 0, 1000, 1000);
        maps.setOpaque(false);//设置为透明
        maps.setLayout(null);
        maps.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        this.getContentPane().add(maps);
        maps.removeAll();
        BuildingsDaoImpl buildingsDao = new BuildingsDaoImpl();
        for (BuildingsEntity buildingsEntity : buildingsDao.queryBuildings()) {
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
            JLabel label1 = new JLabel(" [ X:" + buildingsEntity.getX() + ",Y:" + buildingsEntity.getY() + " ]");
            label1.setLayout(null);
            label1.setSize(new Dimension(250, 30));// 设置新JLabel的大小
            label1.setFont(new Font("黑体", 3, 25));
            label1.setForeground(Color.orange);
            label1.setLocation(buildingsEntity.getX() + 20, buildingsEntity.getY()); // 设置新JLabel的位置
            JLabel label2 = new JLabel(buildingsEntity.getName());
            label2.setLayout(null);
            label2.setSize(new Dimension(250, 30));// 设置新JLabel的大小
            label2.setFont(new Font("黑体", 1, 20));
            label2.setForeground(Color.darkGray);
            label2.setLocation(buildingsEntity.getX() - 10, buildingsEntity.getY() + 30);
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
        RoadDaoImpl roadDao = new RoadDaoImpl();
        BuildingsDaoImpl buildingDao = new BuildingsDaoImpl();
        String[] strings = roadDao.queryStartb();
        String[] strings1 = roadDao.queryEndb();
        int x, y, x1, y1;
        for (int i = 0; i < strings.length; i++) {
            x = buildingDao.queryBuilding(strings[i]).getX();
            System.out.println(x);
            y = buildingDao.queryBuilding(strings[i]).getY();
            System.out.println(y);
            x1 = buildingDao.queryBuilding(strings1[i]).getX();
            System.out.println(x1);
            y1 = buildingDao.queryBuilding(strings1[i]).getY();
            System.out.println(y1);
            MyCanvas myCanvas = new MyCanvas(x, y, x1, y1);
            myCanvas.setLayout(null);
            //非常重要，没有这一行根本显示不出来
            //相当于是myCanvas是一个JPanel
            // 要说他在整个窗口的大小不然的话会默认为0，就加不进去绘图，虽然坐标已经确定了
            //在Swing中，组件的默认大小可能是0x0，
            // 因此即使添加到面板上也不会显示。需要确保为MyCanvas设置合适的大小。
            myCanvas.setBounds(0, 0, 1000, 1000); // 设置大小和位置
            JLabel label2 = new JLabel(roadDao.queryRoadLength(strings[i], strings1[i]) + "米");
            System.out.println("路径长度：" + roadDao.queryRoadLength(strings[i], strings1[i]));
            label2.setLayout(null);
            label2.setSize(new Dimension(250, 30));// 设置新JLabel的大小
            label2.setFont(new Font(null, 3, 22));
            label2.setForeground(Color.RED);
            label2.setLocation((x + x1) / 2 + 10, (y + y1) / 2 + 10); // 设置新JLabel的位置
            maps.add(label2);
            maps.add(myCanvas);
        }
        SwingUtilities.invokeLater(() -> {
            maps.revalidate();// 重验证以确保组件正确显示
            maps.repaint();// 重绘map面板
        });
    }

    private void initView() {
        jpanel.removeAll();
        jpanel1.removeAll();
        jPanel2.removeAll();
        jPanel3.removeAll();
        jPanel2.setOpaque(true); // 设置面板背景透明
        jPanel2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4));
        jPanel2.setBounds(15, 1010, 480, 130);
        JLabel jLabel2 = new JLabel("出 发 地 信 息");
        jLabel2.setFont(new Font(null, 1, 20));
        jLabel2.setForeground(Color.darkGray);
        jLabel2.setBounds(13, 1030, 480, 100);
        jPanel2.add(jLabel2);
        jPanel3.setOpaque(true); // 设置面板背景透明
        jPanel3.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4));
        jPanel3.setBounds(510, 1010, 480, 130);
        JLabel jLabel3 = new JLabel("目 的 地 信 息");
        jLabel3.setFont(new Font(null, 1, 20));
        jLabel3.setForeground(Color.darkGray);
        jLabel3.setBounds(510, 1030, 480, 130);
        jPanel3.add(jLabel3);
        //设置出发地
        //设置面板
        JLabel jLabel = new JLabel("出发地");
        jLabel.setFont(new Font(null, 1, 20));
        RoadDaoImpl rd = new RoadDaoImpl();
        //获取道路数据库的起始地和目的地将其放入一个String数组中存储，展示在目的地和起始地的面板中
        String[] strings = rd.queryStartb();
        String[] strings1 = rd.queryEndb();
        if (strings != null && strings1 != null) {
            String[] strings2 = StringUtils.mergeAndDeduplicate(strings, strings1);
            //创建出发地下拉框
            JComboBox<String> comboBox = new JComboBox<>(strings2);
            //设置宽度和高度：使用 setPreferredSize 方法来设置 JComboBox 的首选大小
            comboBox.setPreferredSize(new Dimension(120, 40));
            //调整字体大小：增大 JComboBox 中的字体大小也会使下拉列表看起来更大。
            comboBox.setFont(new Font(null, 1, 20));
            //设置comboBox默认选中的内容
            comboBox.setSelectedIndex(1);
            ssee[0] = comboBox.getSelectedItem().toString();
            //为初始地jPanel2添加文本框
            JTextArea textArea = new JTextArea(5, 20);
            comboBox.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("选中索引" + comboBox.getSelectedIndex() + "选中内容" + comboBox.getSelectedItem());
                    BuildingsDaoImpl buildingsDao = new BuildingsDaoImpl();
                    BuildingsEntity buildingsEntity = buildingsDao.queryBuilding(comboBox.getSelectedItem().toString());
                    ssee[0] = comboBox.getSelectedItem().toString();
                    textArea.setText("");
                    textArea.setEditable(false);//设置为不可编辑
                    textArea.setLineWrap(true);//开启自动换行
                    textArea.setWrapStyleWord(true);//开启启用了单词边界换行（word wrap）
                    textArea.setText(buildingsEntity.toString());
                    textArea.setBounds(15, 1010, 480, 100);
                    textArea.setFont(new Font(null, 1, 20));
                    textArea.setForeground(Color.black);
                    textArea.setOpaque(false);
                    // textArea.setLocation(buildingsEntity.getX()+50, buildingsEntity.getY());
                    jPanel2.add(textArea);
                }
            });
            this.getContentPane().add(jPanel2);
            jpanel.add(jLabel);
            jpanel.add(comboBox);
            jpanel.setBounds(1005, 10, 140, 70);
            this.getContentPane().add(jpanel);
            //选择目的地
            //设置面板
            JLabel jLabel1 = new JLabel("目的地");
            jLabel1.setFont(new Font(null, 1, 20));
            JComboBox<String> comboBox1 = new JComboBox<>(strings2);
            comboBox1.setPreferredSize(new Dimension(120, 40));
            comboBox1.setFont(new Font(null, 1, 20));
            comboBox1.setSelectedIndex(1);
            ssee[1] = comboBox1.getSelectedItem().toString();
            //为初始地jPanel2添加文本框
            JTextArea textArea1 = new JTextArea(5, 20);
            comboBox1.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("选中索引" + comboBox1.getSelectedIndex() + "选中内容" + comboBox1.getSelectedItem());
                    BuildingsDaoImpl buildingsDao = new BuildingsDaoImpl();
                    BuildingsEntity buildingsEntity = buildingsDao.queryBuilding(comboBox1.getSelectedItem().toString());
                    ssee[1] = comboBox1.getSelectedItem().toString();
                    textArea1.setText("");
                    textArea1.setEditable(false);//设置为不可编辑
                    textArea1.setLineWrap(true);//开启自动换行
                    textArea1.setWrapStyleWord(true);//开启启用了单词边界换行（word wrap）
                    textArea1.setText(buildingsEntity.toString());
                    textArea1.setBounds(510, 1010, 480, 100);
                    textArea1.setFont(new Font(null, 1, 20));
                    textArea1.setForeground(Color.black);
                    textArea1.setOpaque(false);
                    // textArea.setLocation(buildingsEntity.getX()+50, buildingsEntity.getY());
                    jPanel3.add(textArea1);
                }
            });
            this.getContentPane().add(jPanel3);
            jpanel1.add(jLabel1);
            jpanel1.add(comboBox1);
            jpanel1.setBounds(1005, 90, 140, 70);
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
//                    JLabel background=new JLabel(new ImageIcon("src//image//西柚(1).png"));
//                    background.setBounds(0,0,1000,1000);
//                    this.getContentPane().add(background);
            });
        }
    }

    private void initJFrame() {
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
    public void showJDialog(String content) {
        JDialog jDialog = new JDialog();
        jDialog.setSize(400, 150);
        jDialog.setTitle("温馨贴士");
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        //弹窗不关闭永远无法操作下面的界面
        jDialog.setModal(true);
        JLabel warning = new JLabel(content);
        warning.setBounds(0, 0, 200, 150);
        warning.setFont(new Font(null, 1, 20));
        jDialog.getContentPane().add(warning);
        SwingUtilities.invokeLater(() -> {
            jDialog.revalidate();// 重验证以确保组件正确显示
            jDialog.repaint();// 重绘map面板
        });
        jDialog.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object obj = e.getSource();
        if (obj == queryb) {
            initBuildings();
            showJDialogQuerybName();
        } else if (obj == queryr) {
            initBuildings();
            DijkstraImpl dijkstra = new DijkstraImpl();
            if (ssee[0].equals(ssee[1])) {
                showJDialog("起始地和目的地不能相同！");
                return;
            }
            ArrayList<String> dijkstra1 = dijkstra.dijkstra(ssee[0], ssee[1]);
            if (dijkstra1 == null) {
                showJDialog("起始地和目的地无连通路径");
                return;
            }
            String[] a = new String[dijkstra1.size()];
            int i = dijkstra1.size();
            for (String d : dijkstra1) {
                a[--i] = d;
            }
            showJDialogQueryr(a);
            BuildingsDaoImpl b = new BuildingsDaoImpl();
            for (int j = 0; j < a.length - 1; j++) {
                int x = b.queryBuilding(a[j]).getX();
                int y = b.queryBuilding(a[j]).getY();
                int x1 = b.queryBuilding(a[j + 1]).getX();
                int y1 = b.queryBuilding(a[j + 1]).getY();
                MyCanvas1 canvas = new MyCanvas1(x, y, x1, y1);
                canvas.setLayout(null);
                canvas.setBounds(0, 0, 1000, 1000);
                maps.add(canvas, 1);
            }
            SwingUtilities.invokeLater(() -> {
                maps.revalidate();// 重验证以确保组件正确显示
                maps.repaint();// 重绘map面板
            });
        }
    }

    private void showJDialogQueryb(String name) {
        JDialog jDialog = new JDialog();
        jDialog.setSize(400, 200);
        jDialog.setTitle("建筑信息");
        jDialog.setLayout(null);
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        //弹窗不关闭永远无法操作下面的界面
        jDialog.setModal(true);
        if (name.length() > 0) {
            BuildingsDaoImpl buildingsDao = new BuildingsDaoImpl();
            BuildingsEntity buildingsEntity = buildingsDao.queryBuilding(name);
            if (buildingsEntity != null) {
                JLabel building = new JLabel(buildingsEntity.getName() + "的坐标-->X:" + buildingsEntity.getX() + " Y:" + buildingsEntity.getY());
                building.setBounds(20, 10, 400, 40);
                building.setFont(new Font(null, 1, 20));
                jDialog.getContentPane().add(building);
                JLabel building1 = new JLabel(buildingsEntity.getName() + "的介绍:" + buildingsEntity.getIntroduce());
                building1.setBounds(20, 70, 400, 40);
                building1.setFont(new Font(null, 1, 20));
                jDialog.getContentPane().add(building1);
            } else {
                JLabel building = new JLabel("没有该建筑");
                building.setBounds(30, 40, 300, 40);
                building.setFont(new Font(null, 1, 40));
                jDialog.getContentPane().add(building);
            }
        } else
            return;
        //让弹框展示出来
        SwingUtilities.invokeLater(() -> {
            // 这里放置需要在EDT上执行的代码
            jDialog.revalidate();
            jDialog.repaint();
        });
        jDialog.setVisible(true);

    }

    //查询建筑名称输入建筑名的窗口
    private void showJDialogQuerybName() {
        JDialog jDialog = new JDialog();
        jDialog.setSize(400, 250);
        jDialog.setTitle("建筑信息");
        jDialog.setLayout(null);
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        //弹窗不关闭永远无法操作下面的界面
        jDialog.setModal(true);
        JLabel building = new JLabel("输入您要查找的建筑名称：");
        building.setBounds(10, 0, 400, 40);
        building.setFont(new Font(null, 1, 20));
        //填入该建筑名称的文本框
        JTextField answer1 = new JTextField();
        answer1.setBounds(20, 60, 200, 40);
        jDialog.getContentPane().add(answer1);
        jDialog.getContentPane().add(building);
        JButton jButton = new JButton("查询");
        jButton.setFont(new Font(null, 1, 20));
        jButton.setBounds(40, 140, 120, 50);
        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (answer1.getText().length() > 0) {
                    showJDialogQueryb(answer1.getText());
                } else
                    showJDialog("建筑名称不能为空");

            }
        });
        JButton jButton1 = new JButton("取消");
        jButton1.setFont(new Font(null, 1, 20));
        jButton1.setBounds(180, 140, 120, 50);
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


    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}