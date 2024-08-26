package com.njx.view;

import com.njx.dao.UserDaoImpl;
import com.njx.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * ClassName: RegisterFrame
 * Package: com.nanjiixng.ui
 * Description:
 *
 * @Author 南极星
 * @Create 2024/6/12 19:16
 * Version 1.0
 */
public class RegisterJFrame extends JFrame implements MouseListener {
    String key="abc@123";
    JButton login=new JButton("注 册");
    JButton cancel=new JButton("取 消");
    JTextField username=new JTextField();
    JPasswordField password=new JPasswordField();
    JPasswordField password2=new JPasswordField();
    JPasswordField password3=new JPasswordField();
    public RegisterJFrame() {
        //设置界面
        initJFrame();
        //初始化组件
        initView();
        //让界面显示出来，写在最后
        this.setVisible(true);
    }

    private void initJFrame() {
        //设置界面的标题
        this.setTitle("校园导航系统-注册");
        //设置界面的宽高
        this.setSize(600, 350);
        //设置界面置顶，盖住其他软件
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(1);
    }
    private void initView() {
        //初始化用户名密码的字体
        Font font=new Font(null,1,20);
        //JLabel这是一个管理容器，标签组件，用于显示文本或图标，
        // 将文本和图对象加入进来，将该管理容器加到界面

        //添加用户名文字
        JLabel usernameText=new JLabel("用户名");
        usernameText.setFont(font);
        usernameText.setForeground(Color.BLUE);
        usernameText.setBounds(140, 45, 100, 35);
        this.getContentPane().add(usernameText);
        //添加用户名文本框
        username.setBounds(243, 40, 200, 35);
        username.setFont(font);
        this.getContentPane().add(username);
        //添加密码文字
        JLabel passwordText=new JLabel("密 码");
        passwordText.setFont(font);
        passwordText.setForeground(Color.BLUE);
        passwordText.setBounds(140, 90, 100, 35);
        this.getContentPane().add(passwordText);
        JLabel passwordText1=new JLabel("再次输入");
        passwordText1.setFont(font);
        passwordText1.setForeground(Color.BLUE);
        passwordText1.setBounds(140, 125, 100, 35);
        this.getContentPane().add(passwordText1);
        //添加密码输入框
        password.setBounds(243, 85, 200, 35);
        password.setFont(font);
        this.getContentPane().add(password);
        password2.setBounds(243, 125, 200, 35);
        password2.setFont(font);
        this.getContentPane().add(password2);
        JLabel building=new JLabel("请输入管理员密钥：");
        building.setBounds(140, 165, 250, 35);
        building.setFont(new Font(null,1,20));
        building.setForeground(Color.BLUE);
        this.getContentPane().add(building);
        password3.setFont(new Font(null,1,20));
        password3.setForeground(Color.BLUE);
        password3.setBounds(330, 165, 100, 35);
        this.getContentPane().add(password3);
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
        login.addMouseListener(this);
        cancel.addMouseListener(this);
        this.getContentPane().add(login);
        this.getContentPane().add(cancel);
        //添加背景图片
        JLabel background=new JLabel(new ImageIcon("src//image//西柚.png"));
        background.setBounds(0,0,733,423);
        this.getContentPane().add(background);
    }
    //展示弹窗
    public void showJDialog(String content){
        JDialog jDialog=new JDialog();
        jDialog.setSize(200,150);
        jDialog.setTitle("注册信息");
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

    @Override
    public void mouseClicked(MouseEvent e) {
        Object obj=e.getSource();
        if(obj==login){
            String usernameInput=username.getText();
            String passwordInput= password.getText();
            String password2Input=password2.getText();
            UserDaoImpl user1 = new UserDaoImpl();
            if(usernameInput.length()==0||passwordInput.length()==0||password2Input.length()==0){
                showJDialog("用户名或密码为空");
                return;
            }
            if(user1.queryUser(usernameInput)){
               showJDialog("该用户存在");
               return;
           }
            if(passwordInput.length()>16||passwordInput.length()<2){
                showJDialog("密码不能小于2位,不能长于16位");
                return;
            }
            StringUtils stringUtils=new StringUtils();
            if(!stringUtils.isQuality(passwordInput)){
                showJDialog("密码中有非法字符");
                return;
            }
            if(!passwordInput.equals(password2Input)){
                showJDialog("密码输入不一致！");
                return;
            }
            if(!password3.getText().equals(key)){
                showJDialog("密钥错误");
                return;
            } else if(passwordInput.equals(password2Input)){
               if(user1.addUser(usernameInput,passwordInput)){
                       showJDialog("注册成功");
               }else{
                   showJDialog("注册失败");
               }
           }
        }else if(obj==cancel){
            this.setVisible(false);
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
