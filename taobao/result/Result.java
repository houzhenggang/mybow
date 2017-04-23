package com.taobao.mybow.taobao.result;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by qinanhg@gmail.com on 2017/2/3 0003 下午 10:36.
 */
public  abstract  class Result<T> {
    protected String json;
    protected JSONObject rootJSON;

    /**
     * 获取JSON文件里面第二层路径里的JSON数组
     * {"trades_sold_get_response":{"total_results":11,"trades":{"trade":[{"tid":3028689418086124},{"tid":3047218903434972},{"tid":3041367089846472}]},"request_id":"s7ezljq8tz3j"}}
     * @return 第二层路径里的JSON数组
     */
    protected JSONArray get2LevelJSONArray(String first_key, String second_key) {
        return rootJSON.getJSONObject(first_key).getJSONArray(second_key);
    }

    protected void setRootJSON(String json, String root_key) {
        this.json = json;
        rootJSON = JSONObject.fromObject(json).getJSONObject(root_key);
    }
}
