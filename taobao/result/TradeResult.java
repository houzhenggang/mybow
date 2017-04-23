package com.taobao.mybow.taobao.result;

import com.taobao.mybow.taobao.pojo.Trade;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qinanhg@gmail.com on 2017/2/3 0003 下午 11:43.
 */
public class TradeResult extends Result {
    private Trade trade;

    public TradeResult() {
        trade = new Trade();
    }

    /**
     * 解析trades_sold_get_response的返回的JSON数据
     * {"trades_sold_get_response":{"total_results":11,"trades":{"trade":[{"tid":3028689418086124}]},"request_id":"rxn6gucwgtpv"}}
     * @return 封装好的数据
     */
    public List<Long> parseGetTradesResult(String json) {
        setRootJSON(json, "trades_sold_get_response");
        JSONArray array = rootJSON.getJSONObject("trades").getJSONArray("trade");

        Iterator<JSONObject> iterator = array.iterator();

        List<Long> list = new ArrayList<Long>();

        while (iterator.hasNext()) {
            JSONObject object = iterator.next();
            list.add(object.getLong("tid"));
        }

        return list;
    }

    /**
     * 解析trade_fullinfo_get_response返回的JSON数据
     * @param json
     * @return 封装好的数据
     */
    public Trade parseGetTradeFullInfoResult(String json) {
        setRootJSON(json, "trade_fullinfo_get_response");
        JSONObject tradeJSON = rootJSON.getJSONObject("trade");
        trade = (Trade) JSONObject.toBean(tradeJSON, Trade.class);
        return trade;
    }

}
