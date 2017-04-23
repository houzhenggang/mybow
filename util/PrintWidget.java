package com.taobao.mybow.util;

import java.awt.*;
import java.awt.print.*;
import java.util.*;
import java.util.List;

/**
 * Created by qinanhg@gmail.com on 2017/2/4 0004 上午 2:36.
 */
public class PrintWidget implements Printable {
    private float oneMM  = (float)(72 / 25.4);

    private List<PrintField> fields = new ArrayList<PrintField>();
    private PrinterJob job;

    private int width, height;

    private int xOffset = -2;
    private  int yOffset = 2;

    public PrintWidget() {
        // 默认快递单大小
        this(210, 127);
    }

    public PrintWidget(int width, int height) {
        this.width = width;
        this.height = height;

        init();
    }

    public void setXYOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    /**
     *  初始化打印设置
     */
    private void init() {
        // 通俗理解就是书、文档
        Book book = new Book();

        // 设置成竖打
        PageFormat pf = new PageFormat();
        pf.setOrientation(PageFormat.PORTRAIT);

        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
        Paper p = new Paper();
        p.setSize(this.width * oneMM, this.height * oneMM);
        p.setImageableArea(0, 0, this.width * oneMM, this.height * oneMM);
        pf.setPaper(p);

        book.append(this, pf);

        // 获取打印服务对象
        job = PrinterJob.getPrinterJob();
        job.setJobName("打印快递单");

        // 设置打印类
        job.setPageable(book);

        // 设置打印类
        job.setPageable(book);
    }

    public void printTask() {
        try {
            job.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

    /**
     *  添加打印字符串
     *  @param  left 左边距
     *  @param  top 上边距
     *  @param  text 要打印的字符串
     *  @param  fontSize 要打印字体的大小
     */
    public void addPrintText(int left, int top, String text, int fontSize) {
        fields.add(new PrintField(left, top, text, fontSize));
    }

    public void addPrintText(String XY_VALUE, String text, int fontSize) {
        if(text != null & XY_VALUE != null) {
            if(!XY_VALUE.equals(""))
                fields.add(new PrintField(XY_VALUE, text, fontSize));
        }
    }

    public void addPrintText(String XY_VALUE, String text) {
        addPrintText(XY_VALUE, text, 12);
    }

    public void addPrintText(PrintField field) {
        if(field != null)
            fields.add(field);
    }

    public static void main(String[] args) {
        for(int i =0; i < 15; i++) {
            new PrintWidget().printTask();
        }
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

        Graphics2D g2 = (Graphics2D) graphics;

        // 创建字体
        Font font = null;

        for (PrintField field : fields) {
            font = new Font("宋体", Font.PLAIN, field.getFontSize());
            g2.setFont(font);
            g2.drawString(field.getText(), (field.getLeft() + xOffset) * oneMM, (field.getTop() + yOffset) * oneMM);
        }

        return Printable.PAGE_EXISTS;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     *  打印的坐标位置
     */
    public class PrintField {

        /**
         * 坐标值：打印文字：字体大小
         * "senderName": "36:34",
         * @param XY_VALUE
         * @param text
         * @param fontSize
         */
        public PrintField(String XY_VALUE, String text, Integer fontSize) {
            this.left = Integer.valueOf(spiltXY(XY_VALUE)[0]);
            this.top = Integer.valueOf(spiltXY(XY_VALUE)[1]);

            this.fontSize = fontSize;
            this.text = text;
        }



        /**
         * 坐标值X：坐标值Y：打印文字：字体大小
         * @param left
         * @param top
         * @param text
         * @param fontSize
         */
        public PrintField(int left, int top, String text, Integer fontSize) {
            this.left = left;
            this.top = top;
            this.text = text;
            this.fontSize = fontSize;
        }

        // 左边距
        private int left;

        // 上边距
        private int top;

        // 要打印的字符串
        private String text;

        // 打印的字体大小
        private  int fontSize;

        /**
         * 获取X、Y的值
         * @param value 36:34
         * @return 分割好的数组，value[0]是X，value[1]是Y
         */
        private String[] spiltXY(String value) {
            return value.split(":");
        }

        public int getFontSize() {
            return fontSize;
        }

        public void setFontSize(int fontSize) {
            this.fontSize = fontSize;
        }

        public int getTop() {
            return top;
        }

        public String getText() {
            return text;
        }

        public int getLeft() {
            return left;
        }
    }
}