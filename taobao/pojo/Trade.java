package com.taobao.mybow.taobao.pojo;

import java.util.Date;
import java.util.List;

/**
 * 调用淘宝API得到的数据
 * Created by qinanhg@gmail.com on 2017/2/3 0003 下午 11:32.
 */
public class Trade {
    private Long tid;
    private String buyer_nick;
    private Double payment;
    private Double received_payment;
    private Float post_fee;
    private String buyer_message;
    private Date pay_time;

    // 收件人名称
    private String receiver_name;

    // 省
    private String receiver_state;

    // 市
    private String receiver_city;

    // 区
    private String receiver_district;

    // 街道
    private String receiver_address;
    private String receiver_mobile;
    private String receiver_phone;
    private String receiver_zip;

    private List<Long> tids;

    @Override
    public String toString() {
        return "Trade{" +
                "tid=" + tid +
                ", payment=" + payment +
                ", received_payment=" + received_payment +
                ", post_fee=" + post_fee +
                ", seller_nick='" +
                ", receiver_name='" + receiver_name + '\'' +
                ", receiver_state='" + receiver_state + '\'' +
                ", receiver_city='" + receiver_city + '\'' +
                ", receiver_address='" + receiver_address + '\'' +
                ", receiver_district='" + receiver_district + '\'' +
                ", receiver_mobile='" + receiver_mobile + '\'' +
                ", receiver_phone='" + receiver_phone + '\'' +
                ", tids=" + tids +
                '}';
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Double getReceived_payment() {
        return received_payment;
    }

    public void setReceived_payment(Double received_payment) {
        this.received_payment = received_payment;
    }

    public Float getPost_fee() {
        return post_fee;
    }

    public void setPost_fee(Float post_fee) {
        this.post_fee = post_fee;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_state() {
        return receiver_state;
    }

    public void setReceiver_state(String receiver_state) {
        this.receiver_state = receiver_state;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public void setReceiver_mobile(String receiver_mobile) {
        this.receiver_mobile = receiver_mobile;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public List<Long> getTids() {
        return tids;
    }

    public void setTids(List<Long> tids) {
        this.tids = tids;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public void setReceiver_city(String receiver_city) {
        this.receiver_city = receiver_city;
    }

    public String getReceiver_district() {
        return receiver_district;
    }

    public void setReceiver_district(String receiver_district) {
        this.receiver_district = receiver_district;
    }

    public String getBuyer_nick() {
        return buyer_nick;
    }

    public void setBuyer_nick(String buyer_nick) {
        this.buyer_nick = buyer_nick;
    }

    public String getReceiver_zip() {
        return receiver_zip;
    }

    public void setReceiver_zip(String receiver_zip) {
        this.receiver_zip = receiver_zip;
    }

    public String getBuyer_message() {
        return buyer_message;
    }

    public void setBuyer_message(String buyer_message) {
        this.buyer_message = buyer_message;
    }

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }
}
