package com.taobao.mybow.web.action;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.opensymphony.xwork2.ActionSupport;
import com.taobao.mybow.util.PrintExpress;
import com.taobao.mybow.web.interfaces.ShopperInterface;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;
import org.apache.struts2.convention.annotation.*;
import org.apache.struts2.json.annotations.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author qinanhg@gmail.com
 * @Date 2016/11/15 2:01
 */
@ParentPackage("json-default")
@Namespace(value = "/util")
@Results({
        @Result(name = "success", type="json")
})
public class ShopperAction extends ActionSupport implements ShopperInterface {

    private JSONObject json = new JSONObject();
    private String tb_string;
    private PrintExpress tools;

    private static Logger log = Logger.getLogger(ShopperAction.class);

    @Action(value = "update")
    public String update() {


//        log.info("info");
//        log.error("error");
//        log.debug("debug");
        try {
            Object document = Configuration.defaultConfiguration().jsonProvider().parse(tb_string);
            String nick = JsonPath.read(document, "$.mainOrder.buyer.nick");
            String address = JsonPath.read(document, "$.tabs[0].content.address");
            String express = JsonPath.read(document, "$.express");

            tools = new PrintExpress(address, nick, express);
            tools.printTask();

            json.put("success", true);

        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", e.getStackTrace());
        }

        return SUCCESS;
    }


    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    @JSON(serialize = false)
    public String getTb_string() {
        return tb_string;
    }

    public void setTb_string(String tb_string) {
        this.tb_string = tb_string;
    }
}
