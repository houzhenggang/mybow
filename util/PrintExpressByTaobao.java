package com.taobao.mybow.util;

import com.taobao.api.ApiException;
import com.taobao.mybow.taobao.TradeAPI;
import com.taobao.mybow.taobao.pojo.Trade;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinanhg@gmail.com on 2017/2/4 0004 上午 2:56.
 */
public class PrintExpressByTaobao extends PrintExpressInit {

    public static String ZTO = "ZTO";
    public static String SF = "SF";
    public static String HKSF = "HKSF";

    private PrintWidget printWidget = new PrintWidget();

    private List<Trade> trades = new ArrayList<Trade>();
    private TradeAPI api;

    public PrintExpressByTaobao() {
        api = new TradeAPI();
    }

    /**
     * 批量获取淘宝订单
     */
    public void getTrade() {
        try {
            trades = api.getTradesSold(TradeAPI.WAIT_SELLER_SEND_GOODS);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public void geTradeByTids(List<Long> tids) {
        for (long tid: tids)
            geTradeByTid(tid);
    }

    public void geTradeByTid(long tid) {
        try {
            Trade trade = api.getTradeFullInfo(tid);
            trades.add(trade);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private void fillTradeInfo(Trade trade) {

        String mobile = trade.getReceiver_mobile();
        String phone = trade.getReceiver_phone();

        int address_font_size = 14;

        String address = trade.getReceiver_state() + trade.getReceiver_city()  +
                (trade.getReceiver_district() ==null ? "" : trade.getReceiver_district()) + trade.getReceiver_address();

        // 分段打印收货人地址
        String  address1 = "", address2 = "";
        if(address.length() > 15) {
            address1 = address.substring(0, 15).replaceAll(" ", "");
            address2 = address.substring(15).replaceAll(" ", "");
        } else {
            address1 = address;
        }

        if(address.length() > 36)
            address_font_size = 12;

        // 如果手机和固定电话都不为空，那么全加上去
        String phoneNum = (mobile == null ? "" : mobile) + " " + (phone == null ? "" : phone);
        phoneNum.replaceAll("86-", "");
        //String cityInfo = autoCityInfo(trade.getReceiver_state(), trade.getReceiver_city(), trade.getReceiver_district(), trade.getReceiver_address());

        printWidget.addPrintText(fieldPosition.getReceiverName(), trade.getReceiver_name());
        printWidget.addPrintText(fieldPosition.getDestinationCity(), trade.getReceiver_city());
        printWidget.addPrintText(fieldPosition.getReceiverCompany(), trade.getBuyer_nick());
        printWidget.addPrintText(fieldPosition.getReceiverAddress1(), address1, address_font_size);
        printWidget.addPrintText(fieldPosition.getReceiverAddress2(), address2, address_font_size);

        printWidget.addPrintText(fieldPosition.getReceiverPhone(), phoneNum, 14);
        //printWidget.addPrintText(fieldPosition.getCityInfo(), cityInfo, 28);

        printWidget.printTask();
    }

    public void printTask() {
        batchPrintExpress();
    }

    /**
     * 批量打印快递单
     */
    private void batchPrintExpress() {

        for (Trade trade : trades) {
            printWidget = new PrintWidget();
            printWidget.setXYOffset(fieldPosition.getxOffset(), fieldPosition.getyOffset());

            fillMyselftInfo();
            fillTradeInfo(trade);
        }
    }

    public static void main(String[] args) {
        try {
            PrintExpressByTaobao printExpressByTaobao = new PrintExpressByTaobao();
            printExpressByTaobao.selectExpress(PrintExpressByTaobao.SF);
            printExpressByTaobao.geTradeByTid(2534190592856642l);
//            printExpressByTaobao.geTradeByTids(new long[]{3l, 4l});
            printExpressByTaobao.printTask();
            System.out.println("打印完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
