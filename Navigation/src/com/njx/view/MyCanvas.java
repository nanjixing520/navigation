package com.njx.view;

import javax.swing.*;
import java.awt.*;

/**
 * ClassName: MyCanvas
 * Package: com.njx.view
 * Description:
 *
 * @Author 南极星
 * @Create 2024/6/19 17:31
 * Version 1.0
 */

/**
 * 在Java的Graphics类中，
 * 绘制线条的粗细不能直接通过Graphics对象进行设置。
 * 要设置绘制线条的粗细和其他属性，需要使用Graphics2D类，
 * 这是Graphics类的子类。
 * 可以通过将Graphics对象向下转型为Graphics2D来使用更多的绘图功能。
 *
 * 没这样改正之前我的其他组件会被覆盖掉，改后都很正常
 */
    public class MyCanvas extends JPanel {
        private int x1, y1, x2, y2;

        public MyCanvas(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            setOpaque(false); // 使面板透明
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // 设置抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 设置线条的粗细
            g2d.setStroke(new BasicStroke(5));

            // 设置线条颜色
            g2d.setColor(Color.BLACK);

            // 绘制线条
            g2d.drawLine(x1+12, y1+12, x2+12, y2+12);
        }
    }
class MyCanvas1 extends JPanel {
    private int x1, y1, x2, y2;
    private final int RADIUS = 18;

    public MyCanvas1(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        setOpaque(false); // 使面板透明
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 设置抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 设置线条的粗细
        g2d.setStroke(new BasicStroke(8)); // 线条宽度为3

        // 设置线条颜色
        g2d.setColor(Color.CYAN);

        // 计算圆心
        int centerX1 = x1 + RADIUS;
        int centerY1 = y1 + RADIUS;
        int centerX2 = x2 + RADIUS;
        int centerY2 = y2 + RADIUS;

        // 绘制线条，连接圆心
        g2d.drawLine(centerX1, centerY1, centerX2, centerY2);

        // 绘制圆圈
        g2d.setColor(Color.BLUE);
        g2d.fillOval(x1, y1, 2 * RADIUS, 2 * RADIUS);
        g2d.fillOval(x2, y2, 2 * RADIUS, 2 * RADIUS);
    }
}

