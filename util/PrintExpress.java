package com.taobao.mybow.util;

/**
 * Created by qinanhg@gmail.com on 2017/2/22 0022 上午 12:52.
 */
public class PrintExpress extends PrintExpressInit {

    public PrintExpress(String tb, String buyer_nick, String expressName) {
        selectExpress(expressName);
        fillMyselftInfo();
        fillBuyerInfo(getAddressInfo(tb, buyer_nick));
    }

    public void printTask() {
        printWidget.printTask();
    }

    public static void main(String[] args) {
        new PrintExpress("陈颖璇，075523238333，0755-23238333，广东省 东莞市 沙田镇 西大坦启盈国际快件中心15#递四方A集运仓@ZW2YZR ，518128", "闪泪花残", "zto");
        //new PrintExpress("王慧，13754808202，山西省 太原市 小店区 营盘街道 王村小区 5号楼2单元 ，030032", "f闪泪花残", "zto");
    }
}
