package com.taobao.mybow.taobao;

import com.taobao.api.ApiException;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.request.TradeGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradeGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.taobao.mybow.taobao.pojo.Trade;
import com.taobao.mybow.taobao.result.TradeResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinanhg@gmail.com on 2017/2/3 0003 下午 10:27.
 */
public class TradeAPI extends TaobaoAPI<Trade> {
    public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
    public static final String WAIT_SELLER_SEND_GOODS = "WAIT_SELLER_SEND_GOODS";
    public static final String SELLER_CONSIGNED_PART = "SELLER_CONSIGNED_PART";
    public static final String WAIT_BUYER_CONFIRM_GOODS = "WAIT_BUYER_CONFIRM_GOODS";
    public static final String TRADE_BUYER_SIGNED = "TRADE_BUYER_SIGNED";
    public static final String TRADE_FINISHED = "TRADE_FINISHED";
    public static final String TRADE_CLOSED = "TRADE_CLOSED";
    public static final String TRADE_CLOSED_BY_TAOBAO = "TRADE_CLOSED_BY_TAOBAO";

    private TradeResult result;

    public TradeAPI() {
        result = new TradeResult();
    }

    /**
     * 按交易状态来搜索当前订单
     * @param status 交易状态
     * @throws ApiException
     */
    public List<Trade> getTradesSold(String status) throws ApiException {
        List<Trade> trades = new ArrayList<Trade>();
        TradesSoldGetRequest req = new TradesSoldGetRequest();
        req.setStatus(status);
        req.setFields("tid");

        TradesSoldGetResponse rsp = client.execute(req, SESSIONKEY);
        for(long l : result.parseGetTradesResult(rsp.getBody()))
            trades.add(getTradeFullInfo(l));

        return trades;
    }

    /**
     * 获取单笔交易的详细信息
     * @param tid 订单号码
     * @throws ApiException
     */
    public Trade getTradeFullInfo(long tid) throws ApiException {
        TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
        req.setTid(tid);
        req.setFields("tid, buyer_nick, pay_time, buyer_message, seller_memo,  receiver_name, receiver_state, receiver_city, receiver_district, receiver_address, receiver_zip, receiver_mobile, receiver_phone, payment");
        TradeFullinfoGetResponse rsp = client.execute(req, SESSIONKEY);

        return result.parseGetTradeFullInfoResult(rsp.getBody());
    }

    public static void main(String[] args) {
        try {
            TradeAPI api = new TradeAPI();
            Trade trade;

            trade = api.getTradeFullInfo(3079057115190257l);

//            System.out.println(trade.getReceiver_city());

//            trade = api.getTradesSold(WAIT_SELLER_SEND_GOODS);
//
//            for (long l : trade.getTids()) {
//                Trade t1 = api.getTradeFullInfo(l);
//                System.out.println(t1.getReceiver_state() + " " + t1.getReceiver_city() + " " + (t1.getReceiver_district() ==null ? " " : t1.getReceiver_district()) + " " + t1.getReceiver_address() + " " + t1.getTid());
//            }
            //new TradeAPI().getTrade(3028689418086124l);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
