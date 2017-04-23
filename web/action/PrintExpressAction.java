package com.taobao.mybow.web.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * Created by qinanhg@gmail.com on 2017/2/22 0022 上午 12:41.
 */
@ParentPackage("json-default")
//@ParentPackage("json-default")
@Namespace(value = "/utils")
@Results({
        @Result(name = "success", type="json", params={"root","tradesJSON"})
})
public class PrintExpressAction extends ActionSupport {
    private static Logger log = Logger.getLogger(PrintExpressAction.class);

    public String printExpress() {
        return SUCCESS;
    }
}
