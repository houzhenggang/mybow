package com.taobao.mybow.taobao;

import com.taobao.api.Constants;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;

/**
 * Created by qinanhg@gmail.com on 2017/2/3 0003 下午 10:06.
 */
public abstract class TaobaoAPI<T> {
    protected final static String APP_KEY = "12383292";
    protected final static String APP_SERCET = "2e161123af8987b7b63518089705d351";
    protected final static String SESSIONKEY = "6100d092310b682c41333a29ad555611cda1a45b18cf9a840592164";

    protected final static String TAOBAO_SERVER_URL = "http://gw.api.taobao.com/router/rest";

    protected T resultObject;

    protected static TaobaoClient client = new DefaultTaobaoClient(TAOBAO_SERVER_URL, APP_KEY, APP_SERCET, Constants.FORMAT_JSON);

    public T getResult() {
        return resultObject;
    }
}
