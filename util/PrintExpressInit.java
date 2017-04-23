package com.taobao.mybow.util;

import com.taobao.mybow.hibernate.pojo.ExpressFieldPosition;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qinanhg@gmail.com on 2017/2/5 0005 上午 3:46.
 */
public abstract class PrintExpressInit {
    protected ExpressFieldPosition fieldPosition;
    protected PrintWidget printWidget = new PrintWidget();

    private String filePath = "d:\\express.json";

    private String express_json;
    private String default_express_code = "ZTO";

    public PrintExpressInit() {
        init();
    }

    /**
     * 从本地读取配置文件，然后设置打印属性
     * @throws IOException
     */
    private void configPrinter() throws IOException {
        File file = new File(filePath);

        if (file.isFile() && file.exists()) {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            StringBuffer buffer = new StringBuffer();

            while((lineTxt = bufferedReader.readLine()) != null) {
                buffer.append(lineTxt);
            }
            express_json = buffer.toString();
            read.close();
        }
    }

    /**
     * 初始化打印程序
     */
    private void init() {
        try {
            configPrinter();
            //parseExpressJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 选择快递公司
     * @param expressCompany
     */
    protected void selectExpress(String expressCompany) {
        this.default_express_code = expressCompany;

        parseExpressJSON();
    }

    /**
     * 加载快递单配置，解析JSON，转换为类实例
     */
    private void parseExpressJSON() {

        JSONArray rootJSON = JSONObject.fromObject(express_json).getJSONArray("expresses");

        Iterator<JSONObject> iterator = rootJSON.iterator();
        JSONObject express = null;
        JSONObject fieldPositionJSON = null;

        while (iterator.hasNext()) {
            express = iterator.next();
            String code = express.getString("code");

            if (code.equals(default_express_code)) {
                fieldPositionJSON = express.getJSONObject("fieldPosition");

                fieldPosition = (ExpressFieldPosition) JSONObject.toBean(fieldPositionJSON, ExpressFieldPosition.class);
                fieldPosition.setName(express.getString("name"));
                fieldPosition.setCode(code);
                fieldPosition.setWidth(express.getInt("width"));
                fieldPosition.setHeight(express.getInt("height"));
               if(express.get("xOffset") != null)
                    fieldPosition.setxOffset(express.getInt("xOffset"));
                if(express.get("yOffset") != null)
                    fieldPosition.setyOffset(express.getInt("yOffset"));
            }
        }

        if(fieldPosition.getxOffset() != null)
            printWidget.setXYOffset(fieldPosition.getxOffset(), fieldPosition.getyOffset());
    }

    protected void fillMyselftInfo() {
        String[] date = UtilTools.getStringDate().split("-");

        printWidget.addPrintText(fieldPosition.getSenderName(), "安娜");
        printWidget.addPrintText(fieldPosition.getOriginCity(), "柳州");
        printWidget.addPrintText(fieldPosition.getSenderAddress1(), "MyBow 雪雪原创");
        printWidget.addPrintText(fieldPosition.getSenderAddress2(), "专业大蝴蝶结定制");
        printWidget.addPrintText(fieldPosition.getSenderCompany(), "http://mybow.taobao.com");
        printWidget.addPrintText(fieldPosition.getSenderPhone(), "18677299694");
        printWidget.addPrintText(fieldPosition.getSenderSign(), "安娜");
        printWidget.addPrintText(fieldPosition.getYear(), date[0]);
        printWidget.addPrintText(fieldPosition.getMonth(), date[1]);
        printWidget.addPrintText(fieldPosition.getDay(), date[2]);
    }

    /**
     *  "覃生, 18677285453，柳州，广西柳州，广西 柳州 城中区 文惠路87号"
     * @param tb_string "姓名，淘宝昵称，电话，买家所在城市，大头写，详细地址"
     */
    protected void fillBuyerInfo(String[] tb_string) {
        String receiverName = tb_string[0];
        String buyer_nick = tb_string[1];
        String receiverPhone = tb_string[2];
        String destinationCity = tb_string[3];
        String city = tb_string[4];
        String address = tb_string[5].replaceAll("\\s", "");

        int substringIndex = 15;

        String address1 = address, address2 = "";
        int address_font_size = 14;

        if(address.length() >= 35 && address.length() < 42) {
            address_font_size = 12;
            substringIndex += 3;
        } else if (address.length() > 42 && address.length() < 48) {
            address_font_size = 10;
            substringIndex += 8;
        } else if (address.length() > 48) {
            address_font_size = 8;
            substringIndex += 13;
        }

        if (address.length() > substringIndex + 1) {
            address1 = address.substring(0, substringIndex);
            address2 = address.substring(substringIndex);
        }

        printWidget.addPrintText(fieldPosition.getReceiverName(), receiverName);
        printWidget.addPrintText(fieldPosition.getDestinationCity(), destinationCity);
        printWidget.addPrintText(fieldPosition.getReceiverCompany(), buyer_nick);
        printWidget.addPrintText(fieldPosition.getReceiverAddress1(), address1, address_font_size);
        printWidget.addPrintText(fieldPosition.getReceiverAddress2(), address2, address_font_size);
        printWidget.addPrintText(fieldPosition.getReceiverPhone(), receiverPhone, 14);
        printWidget.addPrintText(fieldPosition.getCityInfo(), city, 28);
    }
    /*
     *  杨丽莉，15752496556，云南省 昆明市 官渡区 太和街道 明通商场送之前电话 ，650220
     *  杨丽莉，15752496556，云南省 昆明市 官渡区 太和街道 明通商场，送之前电话 ，650220
     *  杨丽莉，15752496556，18677285453，云南省 昆明市 官渡区 太和街道 明通商场，送之前电话 ，650220
     *
     */
    protected String[] getAddressInfo(String string, String buyer_nick) {
        String[] tbInfo = string.split("，");
        String buyerName = null, phoneNum = "", address = "", cityInfo = null, city = null;

        String reg_phone = "86-\\d{11}|\\d{11}|\\d{1,4}-\\d{6,8}";

        // 过滤地址信息不全的字符串，如：杨丽莉，15752496556
        if (tbInfo.length >= 4) {
            // 收件人名称
            buyerName = tbInfo[0];

            for(int i = 1; i < tbInfo.length - 1; i++) {

                if (tbInfo[i].matches(reg_phone)) {
                    phoneNum += tbInfo[i] + ", ";
                } else {
                    address += tbInfo[i] + " ";
                }
            }

            phoneNum = phoneNum.replaceAll("86-", "").replaceAll(", $", "");


            String[] ads = address.split(" ");
            city = ads[1];
            cityInfo = autoCityInfo(ads);
        }

        System.out.println(buyerName + "\t" + buyer_nick + "\t" + phoneNum + "\t" + address + "\t" + cityInfo);

        return new String[]{buyerName, buyer_nick, phoneNum, city, cityInfo, address};
    }

    /**
     * 自动生成快递大头笔信息：
     * 一般地方： 广西 鹿寨县
     * 广东： 广东 中山镇
     * 上海： 沪东
     * 要求：一般地方只写到县，广东地方要求写到镇，上海地方要按区写（沪东、沪西）
     */
    protected String autoCityInfo(String[] ads) {
        String state = ads[0];
        String city = ads[1];
        String district = ads[2];

        String stateCityDistrict = "";

        for(String s: ads)
            stateCityDistrict += s + "-";

        stateCityDistrict = stateCityDistrict.replaceAll("-$", "");

        String value = null;
        String sh_rule1 = "黄浦区|虹口区|闸北区|徐汇区|长宁区|静安区|普陀区|闵行区|宝山区|嘉定区|金山区|松江区|青浦区|卢湾区|杨浦";
        String sh_rule2 = "康桥区|南汇区|长兴区|奉贤区|崇明县|浦东新区";

        String[] regex = {"[\u4e00-\u9fa5]*镇", "[\u4e00-\u9fa5]*县", "[\u4e00-\u9fa5]*市$"};

        if (state.contains("上海")) {
            state = "";

            if(sh_rule1.contains(district))
                value = "沪西";
            else if(sh_rule2.contains(district))
                value = "沪东";
        } else if (state.contains("广东")) {
            value = loopRegexFind(0, stateCityDistrict);
        } else {
            value =loopRegexFind(1, stateCityDistrict);
        }

        value = value.replaceAll("新疆维吾尔自治区图木舒克", "新疆");
        value = value.replaceAll("广西壮族自治区", "广西");
        value = value.replaceAll("[省|市]", "");

        if(!state.equals(value))
            value = state + value;

        // 如果城市名太太，就只打印6个字
        if(value.length() > 6)
            value = value.substring(0, 6);

        return value;
    }

    private String  loopRegexFind(int startValue, String findString) {
        String[] regex = {"[\u4e00-\u9fa5]*镇$", "[\u4e00-\u9fa5]*县$", "[\u4e00-\u9fa5]*市$"};

        String value = null;

        String[] ad = findString.split("-");
        Collections.reverse(Arrays.asList(ad));

        for (String s : ad) {
            for (int i = startValue; i < regex.length; i++) {
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
}
