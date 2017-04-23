package com.taobao.mybow.temp;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.taobao.mybow.taobao.pojo.Trade;
import com.taobao.mybow.util.UtilTools;
import net.sf.ezmorph.object.StringMorpher;
import net.sf.ezmorph.primitive.IntMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;
import org.jsoup.Jsoup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qinanhg on 2017/2/2 0002.
 */
public class Temp {
    public Temp() {
        tt();
    }

    private void tt() {
        String[] citys = {"广东省 佛山市 顺德区 伦教街道伦教新丰路32号    广东祥和税务师事务所有限公司伦教分公司","上海 上海市 浦东新区 塘桥街道浦建路160号7号楼2楼","广东省 佛山市 顺德区 伦教街道伦教新丰路32号    广东祥和税务师事务所有限公司伦教分公司","江苏省 苏州市 常熟市 虞山镇大义镇光明路16号","湖南省 永州市 零陵区 徐家井街道三监狱北区8栋","重庆 重庆市 大渡口区 跃进村街道马王场正街易家小区","新疆维吾尔自治区 图木舒克市   兵团四十九团四十九团11连","云南省 昆明市 官渡区 云南省昆明市官渡区世纪城玉春苑3幢3单元7H","重庆 重庆市 九龙坡区 金凤镇车站内", "河南省 安阳市 安阳县 水冶镇蔬菜大鹏南500米东方家园小区3号楼最西头","上海 上海市 静安区 彭浦新村街道保德路1316弄75号602室","上海 上海市 松江区 岳阳街道松汇中路971弄21号602室","广东省 东莞市   沙田镇西大坦启盈国际快件中心15#递四方A集运仓@YI8KOM", "河南省 郑州市 金水区 花园路街道花园路英才街天地湾2期晴苑11号楼","浙江省 杭州市 西湖区 古荡街道古墩路浙商财富中心a座1楼小邮局","广东省 中山市   火炬开发区街道中山港大道139号水秀花园6栋","重庆 重庆市 沙坪坝区 小龙坎街道小龙坎正街369号 诺丁阳光3-3-6","福建省 漳州市 漳浦县 古雷镇新港城，15区，13栋，"};

        for (String address : citys) {
            String[] ads = address.split(" ");

            String value = "";
            value = autoCityInfo(ads[0], ads[1], ads[2], ads[3]);
            System.out.print(address + "---------------->");
            System.out.println(value);
        }
    }

    /**
     * 自动生成快递大头笔信息：
     * 一般地方： 广西 鹿寨县
     * 广东： 广东 中山镇
     * 上海： 沪东
     * 要求：一般地方只写到县，广东地方要求写到镇，上海地方要按区写（沪东、沪西）
     */
    private String autoCityInfo(String state, String city, String district, String address) {
        String stateCityDistrict = district + "-" + city + "-" + state;

        String value = null;
        String sh_rule1 = "黄浦区|虹口区|闸北区|徐汇区|长宁区|静安区|普陀区|闵行区|宝山区|嘉定区|金山区|松江区|青浦区|卢湾区|杨浦";
        String sh_rule2 = "康桥区|南汇区|长兴区|奉贤区|崇明县|浦东新区";

        String[] regex = {"[\u4e00-\u9fa5]*镇", "[\u4e00-\u9fa5]*县", "[\u4e00-\u9fa5]*市"};

        if (state.contains("上海")) {
            if(sh_rule1.contains(district))
                value = "沪西";
            else if(sh_rule2.contains(district))
                value = "沪东";
        } else if (state.contains("广东")) {
            value = loopRegexFind(0, stateCityDistrict);
        } else {
            value =loopRegexFind(1, stateCityDistrict);
        }

        return state + value;
    }

    private String  loopRegexFind(int startValue, String findString) {
        String[] regex = {"[\u4e00-\u9fa5]*镇", "[\u4e00-\u9fa5]*县", "[\u4e00-\u9fa5]*市"};

        String value = null;

        String[] ad = findString.split("-");

        for (int i = startValue; i < regex.length; i++) {
            for (String s : ad) {
                value = regexFind(s, regex[i]);

                if (value != null) {
                    return value;
                }
            }
        }

        return value;
    }

    private String  regexFind(String allAddress, String regex1) {
        Pattern pattern = Pattern.compile(regex1);
        Matcher matcher = pattern.matcher(allAddress);

        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public Temp(String string) {
        System.out.println(string);
    }

    public static void main(String[] args) {
//        String json = "{\"mainOrder\":{\"operations\":[],\"statusInfo\":{\"text\":\"当前订单状态：买家已付款，等待商家发货\",\"type\":\"t0\"},\"totalPrice\":[{\"type\":\"line\",\"content\":[{\"type\":\"text\",\"value\":\"352.00\"}]},{\"type\":\"line\",\"content\":[{\"type\":\"text\",\"value\":\"(快递:10.00\"}]},{\"type\":\"line\",\"content\":[{\"type\":\"text\",\"value\":\")\"}]}],\"columns\":[\"宝贝\",\"宝贝属性\",\"状态\",\"服务\",\"单价\",\"数量\",\"优惠\",\"商品总价\"],\"extra\":{\"isShowSellerService\":false},\"orderInfo\":{\"lines\":[{\"type\":\"line\",\"content\":[]},{\"type\":\"line\",\"content\":[{\"type\":\"namevalue\",\"value\":{\"name\":\"订单编号:\",\"value\":[{\"type\":\"text\",\"value\":\"3152961891490574\"}]}},{\"type\":\"namevalue\",\"value\":{\"name\":\"支付宝交易号:\",\"value\":\"2017021921001001440281205356\"}},{\"type\":\"namevalue\",\"value\":{\"name\":\"创建时间:\",\"value\":\"2017-02-19 20:10:10\"}},{\"type\":\"namevalue\",\"value\":{\"name\":\"付款时间:\",\"value\":\"2017-02-19 20:17:09\"}}]}],\"type\":\"group\"},\"id\":\"3152961891490574\",\"subOrders\":[{\"priceInfo\":\"88.00\",\"quantity\":\"4\",\"service\":[],\"extra\":{\"overSold\":false,\"alicommunOrderDirect\":false,\"needShowQuantity\":0,\"xt\":false,\"needDisplay\":false,\"payStatus\":0,\"opWeiQuan\":false,\"notSupportReturn\":true},\"tradeStatus\":[{\"type\":\"line\",\"content\":[{\"type\":\"text\",\"value\":\"未发货\"}]}],\"id\":3152961891490574,\"itemInfo\":{\"skuText\":[{\"type\":\"line\",\"content\":[{\"type\":\"namevalue\",\"value\":{\"name\":\"颜色分类：\",\"value\":\"拍下请按宝贝说明留言颜色\"}}]},{\"type\":\"line\",\"content\":[{\"type\":\"operation\",\"value\":{\"data\":{\"width\":820,\"crossOrigin\":false,\"height\":370},\"action\":\"a8\",\"style\":\"t16\",\"text\":\"修改订单属性\",\"type\":\"operation\",\"url\":\"//trade.taobao.com/trade/modify_sku.htm?bizOrderId=3152961891490574\"}}]}],\"auctionUrl\":\"//trade.taobao.com/trade/detail/tradeSnap.htm?trade_id=3152961891490574\",\"extra\":[{\"name\":\"此商品性质不支持7天退货\",\"value\":\"详情\",\"url\":\"//service.taobao.com/support/knowledge-5701303.htm?spm=a1z09.5.0.0.8LDL5E\"}],\"pic\":\"//img.alicdn.com/bao/uploaded/i3/T1wAxhFXVfXXXXXXXX_!!0-item_pic.jpg_sum.jpg\",\"title\":\"T30 多色定制贡缎特大蝴蝶结可配腰带 婚纱礼新娘伴娘衣服配饰DIY\",\"serviceIcons\":[{\"linkTitle\":\"保障卡\",\"linkUrl\":\"http://trade.taobao.com/trade/security/security_card.htm?bizOrderId=3152961891490574\",\"type\":3,\"url\":\"//img.alicdn.com/tps/i2/T1S4ysXh8pXXXXXXXX-52-16.png\"}]},\"promotionInfo\":[]}],\"payInfo\":{\"tmallYfx_bizOrderId\":0,\"sellerYfx_bizOrderId\":0,\"showPayDetail\":false,\"cod\":false,\"sendPromotions\":[],\"xiaobao315Yfx_bizOrderId\":0,\"actualFee\":{\"name\":\"实收款\",\"value\":\"362.00\"},\"fullPromotion\":{\"valid\":false}},\"buyer\":{\"nick\":\"emmachoi301\",\"mail\":\"***\",\"city\":\" \",\"payToBuyerUrl\":\"//trade.taobao.com/trade/payToUser.htm?user_type=buyer&biz_order_id=3152961891490574\",\"phoneNum\":\"00614****0498\",\"privateMsgUrl\":\"//member1.taobao.com/message/addPrivateMsg.htm?recipient_nickname=emmachoi301\",\"id\":3163307405,\"guestUser\":false,\"alipayAccount\":\"6***\"}},\"buyMessage\":\"需要peach ( 蜜桃色），謝謝！\",\"orderBar\":{\"nodes\":[{\"index\":1,\"text\":\"1. 买家下单\"},{\"index\":2,\"text\":\"2. 买家付款\"},{\"index\":3,\"text\":\"3. 发货\"},{\"index\":4,\"text\":\"4. 买家确认收货\"},{\"index\":5,\"text\":\"5. 评价\"}],\"currentStepIndex\":0,\"currentIndex\":3},\"crumbs\":[{\"text\":\"首页\",\"url\":\"//www.taobao.com\"},{\"text\":\"我的淘宝\",\"url\":\"//i.taobao.com/myTaobao.htm?nekot=1487559642115\"},{\"text\":\"已卖出的宝贝\",\"url\":\"//trade.taobao.com/trade/itemlist/listSoldItems.htm?nekot=1487559642115\"}],\"operationsGuide\":[{\"layout\":\"li\",\"lines\":[{\"type\":\"line\",\"content\":[{\"type\":\"text\",\"value\":\"请您尽快发货，如果缺货，可联系买家协商退款。\"}]},{\"type\":\"line\",\"content\":[{\"type\":\"text\",\"value\":\"买家已付款，请尽快发货，否则买家有权申请退款。\"}]},{\"type\":\"line\",\"content\":[{\"type\":\"text\",\"value\":\"如果无法发货，请及时与买家联系并说明情况。\"}]},{\"type\":\"line\",\"content\":[{\"type\":\"text\",\"value\":\"买家申请退款后，须征得买家同意后再操作发货，否则买家有权拒收货物。\"}]}],\"type\":\"group\"},{\"lines\":[{\"type\":\"line\",\"content\":[]},{\"type\":\"line\",\"content\":[{\"type\":\"operation\",\"value\":{\"style\":\"t3\",\"text\":\"发货\",\"type\":\"operation\",\"url\":\"//trade.taobao.com/trade/logistics_status.htm?logisType=1&bizOrderId=3152961891490574&bizType=200\"}},{\"type\":\"operation\",\"value\":{\"style\":\"t4\",\"text\":\"标记\",\"type\":\"operation\",\"url\":\"//trade.taobao.com/trade/memo/updateSellMemo.htm?bizOrderId=3152961891490574&sellerId=40592164&returnUrl=%2F%2Ftrade.taobao.com%2Ftrade%2Fdetail%2FtradeItemDetail.htm%3Fbiz_order_id%3D3152961891490574\"}},{\"type\":\"operation\",\"value\":{\"style\":\"t4\",\"text\":\"订单优惠详情\",\"type\":\"operation\",\"url\":\"http://smf.taobao.com/index.htm?menu=yhjk&module=yhjk&orderNo=3152961891490574\"}}]}],\"type\":\"group\"}],\"tabs\":[{\"id\":\"logistics\",\"title\":\"收货和物流信息\",\"content\":{\"alingPhone\":\"075523238333\",\"nick\":\"myleft1\",\"address\":\"Emma choi，075523238333，0755-23238333，广东省 东莞市 沙田镇 西大坦启盈国际快件中心15#递四方D集运仓@FWXMLQB ，518101\",\"shipType\":\"快递\",\"transitValue\":\"Emma choi，61-430290498，澳大利亚       Unit 408, 9-11 Wollongong road, Arncliffe, NSW 2205, Sydney ，000000\"}}],\"detailExtra\":{\"op\":false,\"b2c\":false,\"ccc\":false,\"tradeEnd\":false,\"outShopOrder\":false,\"wakeupOrder\":false,\"refundByTb\":false,\"success\":false,\"inRefund\":false,\"viewed_flag\":false}}";
//
//        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
//        String nick = JsonPath.read(document, "$.mainOrder.buyer.nick");
//        String address = JsonPath.read(document, "$.tabs[0].content.address");
//        System.out.println(nick);
//        System.out.println(address);
//        String str = "{\"name\": \"qinanhg\"}";
//        JSONObject o = JSONObject.fromObject(str);
//        System.out.println(o.get("name"));
//        String string = "杨丽莉，15752496556，18677285453，云南省 昆明市 官渡区 太和街道 明通商场，送之前电话 ，650220";
//        String[] regs = {"\\d{11}|\\d{1,4}-\\d{6,8}", "[\u4e00-\u9fa5]"};
//        String reg_phone = "\\d{11}|\\d{1,4}-\\d{6,8}";
//
//        String[] tb_info = string.split("，");
//        String phoneNum = "";
//        String address = "";
//
//        for(int i = 1; i < tb_info.length - 1; i++) {
//
//            if (tb_info[i].matches(reg_phone)) {
//                phoneNum += tb_info[i] + ", ";
//            } else {
//                address += tb_info[i];
//            }
//
//        }
//
//        System.out.println(phoneNum);
//        System.out.println(address);
        String num = "13608300211,";
        num = num.replaceAll(",$", "");
        System.out.println(num);

    }
}
