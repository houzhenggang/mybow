package com.taobao.mybow.util;

import com.taobao.mybow.hibernate.pojo.Shopper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by qinanhg@gmail.com on 2017/2/2 0002 下午 7:03.
 */
public class TaoBaoShopperInfo {
    private String html;
    private static final String TAOBAO_SHOPPER_INFO_ADDRESS = "https://rate.taobao.com/user-rate-UMmNbOFIYMCQT.htm";

    private Document doc;



    private Shopper shopper;

    public TaoBaoShopperInfo() {
    }

    public void fetchData() throws IOException {
        openUrl();
        parseDocument();
    }

    private void openUrl() throws IOException {
        doc = Jsoup.connect(TAOBAO_SHOPPER_INFO_ADDRESS).get();
    }

    private void parseDocument() {
        Element element = doc.select("ul.sep").first();
        System.out.println(element.html());
    }

    public static void main(String[] args) {
        try {
            new TaoBaoShopperInfo().fetchData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Shopper getShopper() {
        return shopper;
    }
}
