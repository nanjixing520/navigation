package com.njx.view;

import com.njx.dao.UserDaoImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * ClassName: LoginJFrame
 * Package: com.nanjiixng.ui
 * Description:
 *
 * @Author 南极星
 * @Create 2024/6/12 19:20
 * Version 1.0
 */
public class LoginJFrame extends JFrame implements MouseListener{
    JButton login=new JButton("登 录");
    JButton register=new JButton("注 册");
    JButton Guest=new JButton("游客登录");
    JTextField username=new JTextField();
    JPasswordField password=new JPasswordField();
    public LoginJFrame() {
        //初始化界面
        initJFrame();

        //初始化菜单
        initJMenuBar();

        //初始化组件，添加到桌面里
        initView();

        //让界面显示出来，写在最后
        this.setVisible(true);

    }

    private void initView(){
        //初始化用户名密码的字体
        Font font=new Font(null,1,25);
        //JLabel这是一个管理容器，标签组件，用于显示文本或图标，
        // 将文本和图对象加入进来，将该管理容器加到界面

        //添加用户名文字
        JLabel usernameText=new JLabel("用户名");
        usernameText.setFont(font);
        usernameText.setForeground(Color.blue);
        usernameText.setBounds(170, 65, 100, 50);
        this.getContentPane().add(usernameText);
        //添加用户名文本框
        username.setBounds(280, 60, 200, 50);
        username.setFont(font);
        this.getContentPane().add(username);
        //添加密码文字
        JLabel passwordText=new JLabel("密 码");
        passwordText.setFont(font);
        passwordText.setForeground(Color.BLUE);
        passwordText.setBounds(227, 150, 100, 50);
        this.getContentPane().add(passwordText);
        //添加密码输入框
        password.setBounds(327, 150, 160, 50);
        password.setFont(font);
        this.getContentPane().add(password);

        //添加登录按钮,新建登录按钮应该在类中，方便其他方法访问
        login.setFont(font);
        login.setForeground(Color.black);
        login.setBounds(123, 290, 128, 47);
        //login.setIcon(new ImageIcon("阳光温柔.jpg"));
//        //去除按钮背景
//        login.setContentAreaFilled(false);
//        //去除按钮边框
//        login.setBorderPainted(false);
        login.addMouseListener(this);
        this.getContentPane().add(login);
        //添加注册按钮
        register.setFont(font);
        register.setForeground(Color.black);
        register.setBounds(300, 290, 128, 47);
        register.addMouseListener(this);
        this.getContentPane().add(register);
        //添加游客登录按钮
        Guest.setFont(font);
        Guest.setForeground(Color.black);
        Guest.setBounds(477, 290, 150, 47);
        Guest.addMouseListener(this);
        this.getContentPane().add(Guest);
        //添加背景图片
        JLabel background=new JLabel(new ImageIcon("src//image//西柚.png"));
        background.setBounds(0,0,733,423);
        this.getContentPane().add(background);
    }

    private void initJMenuBar() {
        //创建整个的菜单对象
        JMenuBar jMenuBar=new JMenuBar();
        //创建菜单上面的两个选项的对象（功能 关于我们）
        JMenu functionJMenu=new JMenu("功能");
        JMenu aboutJMenu=new JMenu("关于我们");
        //创建选项下面的条目对象
        JMenuItem tuijianItem=new JMenuItem("校友有话说");
        JMenuItem liaoItem=new JMenuItem("聊聊吧");
        JMenuItem accountItem=new JMenuItem("公众号");
        tuijianItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showJDialogI("src//image//快逃.jpg");
            }
        });
        accountItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showJDialogI("src//image//二维码.jpg");
            }
        });
        //将每一个选项下面的条目添加到选项中
        functionJMenu.add(tuijianItem);
        functionJMenu.add(liaoItem);
        aboutJMenu.add(accountItem);
        //将两个选项添加到菜单中去
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);
        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        //设置界面的标题
        this.setTitle("西安邮电大学校园导航系统 v1.0");
        //设置界面的宽高
        this.setSize(733, 423);
        //设置界面置顶，盖住其他软件
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(3);
        //取消默认的居中放置，只有取消了才会按照XY轴的形式添加组件
        this.setLayout(null);
    }
    public void showJDialogI(String a){
        JDialog jDialog=new JDialog();
        jDialog.setSize(502,478);
        jDialog.setTitle("西邮小惊喜");
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        //弹窗不关闭永远无法操作下面的界面
        jDialog.setModal(true);
        JLabel label=new JLabel(new ImageIcon(a));
        label.setBounds(0,0,502,478);
        jDialog.getContentPane().add(label);
        //让弹框展示出来
        jDialog.setVisible(true);
    }
    //展示弹窗
    public void showJDialog(String content){
        JDialog jDialog=new JDialog();
        jDialog.setSize(200,150);
        jDialog.setTitle("错误信息");
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        //弹窗不关闭永远无法操作下面的界面
        jDialog.setModal(true);
        JLabel warning=new JLabel(content);
        warning.setBounds(0,0,200,150);
        warning.setFont(new Font(null,1,20));
        jDialog.getContentPane().add(warning);
        //让弹框展示出来
        jDialog.setVisible(true);
    }
//鼠标点击
    @Override
    public void mouseClicked(MouseEvent e) {
        Object obj=e.getSource();
        if(obj==login){
            //获取两个文本输入框中的内容
            String usernameInput=username.getText();
            String passwordInput= password.getText();
            UserDaoImpl user1 = new UserDaoImpl();
            if(usernameInput.length()==0||passwordInput.length()==0){
                showJDialog("用户名或密码为空");
                return;
            }else if(!user1.queryUser1(usernameInput,passwordInput)){
                showJDialog("账号或密码错误");
                return;
            }else if(user1.queryUser1(usernameInput,passwordInput)){
                this.setVisible(false);
                new NavigationJFrame(usernameInput);
            }
        }else if(obj==register){
            new RegisterJFrame();
            return;
        }else if(obj==Guest){
            this.setVisible(false);
            new TouristNavigation();
        }
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
