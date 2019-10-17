package io.jopen.core.common.util;

import io.jopen.core.common.text.StringHelper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

/**
 * @author maxuefeng
 */
public class StringTest {


    @Test
    public void testSubstringExclude() {

        String tmp = "abcacbannnh";

        String substring = tmp.substring(0, tmp.length());

        System.out.println(substring);
    }

    /**
     * @throws NullPointerException 空指针 String.valueOf(null)默认调用的是String.valueOf(byte[] a)
     */
    @Test
    public void getCharIndex() {

        String tmp = "HelloWorldW";

        System.err.println(tmp.indexOf("Z"));

        System.err.println(String.valueOf(null));

    }

    @Test
    public void extractInfo() {

        String source = "创 建 时 间 2019-03-18 19:14";

        System.err.println(source);

        int year = LocalDate.now().getYear();

        String[] afterSplit = source.split(year + "");

        String lastString = afterSplit[afterSplit.length - 1];

        // 从此处开始提取时间信息
        String newDateString = year + lastString;
    }

    @Test
    public void testStringHelperFormat() {
        String origin = "中国电信园11B/siD台必宋雌山19%I下午2:50\n" +
                "义账单详情全部账单\n" +
                "鸿鸿福东北饺子家常菜\n" +
                "当前状态“支付成功\n" +
                "商品美团     点评智能支付_鸿鸿福东北饺子\n" +
                "家常蔡\n" +
                "商户全称“北京鸿鸿福餐饮管理有限公司第三\n" +
                "分店\n" +
                "支付时间“2019-04-23 12:16:47\n" +
                "支付方式“招商银行(2949)\n" +
                "交易单号“4200000333201904235108966201\n" +
                "商户单号“可在支持的商户扫码退款";

        /*String result = StringHelper.format(origin, new Character[]{});

        System.err.println(result);*/

       /* char[] chars = "".toCharArray();

        System.err.println(chars.length);*/

       System.err.println(StringUtils.deleteWhitespace(origin));

        String[] split = origin.split("\n");

        System.err.println(split.length);
    }

}
