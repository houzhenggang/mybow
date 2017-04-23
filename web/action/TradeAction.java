package com.taobao.mybow.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.taobao.api.ApiException;
import com.taobao.mybow.taobao.TradeAPI;
import com.taobao.mybow.taobao.pojo.Trade;
import com.taobao.mybow.util.PrintExpressByTaobao;
import com.taobao.mybow.util.UtilTools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.*;

import java.util.List;

/**
 * Created by qinanhg@gmail.com on 2017/2/7 0007 下午 11:21.
 */
@ParentPackage("json-default")
//@ParentPackage("json-default")
@Namespace(value = "/trade")
@Results({
        @Result(name = "success", type="json", params={"root","tradesJSON"})
})
public class TradeAction extends ActionSupport {
    private static Logger log = Logger.getLogger(TradeAction.class);

    private TradeAPI api = new TradeAPI();
    private JSONObject tradesJSON = new JSONObject();
    private Trade trade;
    PrintExpressByTaobao printExpressByTaobao = new PrintExpressByTaobao();

    @Action(value = "getTrades")
//    @Action(value = "getTrades", results = { @Result(name = "success", params={"root","msg"}, type = "json")})
    public String getTradesByStatus() {

        try {
            List<Trade> trades = api.getTradesSold(TradeAPI.WAIT_SELLER_SEND_GOODS);

            JSONArray tradesJson = new JSONArray();
            JSONObject tradeJSON = null;
            int count = 0;

            for (Trade trade : trades) {
                tradeJSON = new JSONObject();
                tradeJSON.put("id", ++count);
                tradeJSON.put("buyer_nick", trade.getBuyer_nick());
                tradeJSON.put("tid", trade.getTid());
                tradeJSON.put("pay_time", UtilTools.getStringDateTimeByDate(trade.getPay_time()));

                tradesJson.add(tradeJSON);
            }

            tradesJSON.put("success", true);
            tradesJSON.put("trades", tradesJson);
        } catch (ApiException e) {
            log.error("调用淘宝API出错", e);
        }

        return SUCCESS;
    }

    @Action(value = "printExpressByTaobao")
    public String printExpress() {

        if(null != trade) {
            if (trade.getTid() == null) {
                printExpressByTaobao.geTradeByTids(trade.getTids());
                printExpressByTaobao.printTask();
            } else {
                printExpressByTaobao.geTradeByTid(trade.getTid());
                printExpressByTaobao.printTask();
            }

            tradesJSON.put("tid", trade.getTid());
            tradesJSON.put("msg", "打印成功");
            tradesJSON.put("success", true);
        } else {
            tradesJSON.put("success", false);
            tradesJSON.put("msg", "打印失败");
        }

        return SUCCESS;
    }

    public JSONObject getTradesJSON() {
        return tradesJSON;
    }

    public void setTradesJSON(JSONObject tradesJSON) {
        this.tradesJSON = tradesJSON;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }
}
