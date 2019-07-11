package com.gzhc365.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 17:51 2019/7/1 0001
 * @Modified by:
 */
public class TextUtils {

    public static void main(String[] args) {

        String context = "巴拉巴拉巴拉，擅长食管癌、肺癌、鼻咽癌、乳腺癌等各种肿瘤的常规放疗及肿瘤的适形、调强放疗等，在苏中地区率先开展了脑瘤、胰腺癌、肝癌、肺癌等肿瘤的适形放疗和X—刀治疗。你好";
        String reg = "擅长(.*?)。";    // ( 为特殊字符，需要用 \\ 转义
        String s = regexText(context, reg);
        System.out.println(s);

        String cnStr = "内科";
        System.out.println(getPingYin(cnStr));
        System.out.println(getPinYinHeadChar(cnStr));

        //    	String str = "\\u4E0E\\u6240\\u6709\\u836F\\u7269\\u4E00\\u6837\\uFF0C\\u672C\\u54C1\\u53EF\\u80FD\\u6709\\u4E0D\\u826F\\u53CD\\u5E94\\u3002\\n\\u4F9D\\u636E\\u4EE5\\u4E0B\\u53D1\\u751F\\u9891\\u7387\\u5C06\\u4E0D\\u826F\\u53CD\\u5E94\\u62A5\\u9053\\u5217\\u8868\\u5982\\u4E0B\\uFF1A</p>\\n\\n<p>\\u5E38\\u89C1\\u7684(\\uFF1E1/100\\u3001\\uFF1C1/10)</p>\\n\\n<p>\\u5C40\\u90E8\\u53CD\\u5E94\\uFF1A\\u7EA2\\u80BF\\u3001\\u75BC\\u75DB\\u3001\\u7EA2\\u6591\\uFF08\\u7600\\u4F24\\uFF09\\u3001\\u786C\\u7ED3\\u3002\\n\\u5168\\u8EAB\\u53CD\\u5E94\\uFF1A\\u53D1\\u70ED\\u3001\\u4E0D\\u9002\\u3001\\u5BD2\\u6218\\u3001\\u75B2\\u52B3\\u3001\\u5934\\u75DB\\u3001\\u51FA\\u6C57\\u3001\\u808C\\u75DB\\u3001\\u5173\\u8282\\u75DB\\u3002\\n\\u4E0A\\u8FF0\\u4E0D\\u826F\\u53CD\\u5E94\\u901A\\u5E38\\u57281\\u20142\\u5929\\u5185\\u6D88\\u5931\\uFF0C\\u65E0\\u9700\\u6CBB\\u7597\\u3002\\n\\u4E0D\\u5E38\\u89C1\\u7684(\\uFF1E1/1000\\u3001\\uFF1C1/100\\uFF09\\n\\u666E\\u901A\\u7684\\u76AE\\u80A4\\u53CD\\u5E94\\uFF0C\\u5305\\u62EC\\uFF1A\\u7619\\u75D2\\u3001\\u8368\\u9EBB\\u75B9\\uFF0C\\u975E\\u7279\\u5F02\\u6027\\u76AE\\u75B9\\u3002\\n\\u7F55\\u89C1\\u7684(\\uFF1E1/10000\\u3001\\uFF1C1/1000)</p>\\n\\n<p>\\u795E\\u7ECF\\u75DB\\uFF08\\u654F\\u611F\\u795E\\u7ECF\\u7684\\u795E\\u7ECF\\u533A\\u57DF\\u75BC\\u75DB\\uFF09\\u3001\\u611F\\u89C9\\u5F02\\u5E38\\uFF08\\u5F02\\u5E38\\u7684\\u611F\\u89C9\\u5982\\u70E7\\u707C\\u3001\\u9EBB\\u6728\\u3001\\u524C\\u75DB\\u7B49\\uFF09\\u3001\\u60CA\\u53A5\\u3001\\u4E00\\u8FC7\\u6027\\u8840\\u5C0F\\u677F\\u51CF\\u5C11\\u75C7\\uFF08\\u8840\\u5C0F\\u677F\\u51CF\\u5C11\\uFF09\\u3002\\u4E00\\u8FC7\\u6027\\u7684\\u6DCB\\u5DF4\\u7ED3\\u80BF\\u5927\\uFF08\\u77ED\\u6682\\u7684\\u8116\\u9888\\u3001\\u814B\\u7A9D\\u8179\\u80A1\\u6C9F\\u817A\\u4F53\\u80BF\\u5927\\uFF09\\u3002</p>\\n\\n<p>\\u8FC7\\u654F\\u53CD\\u5E94\\uFF0C\\u5728\\u6781\\u4E2A\\u522B\\u60C5\\u51B5\\u4E0B\\u9020\\u6210\\u4F11\\u514B\\u3002\\u4E25\\u91CD\\u8FC7\\u654F\\u53CD\\u5E94\\u5305\\u62EC\\uFF1A\\u4E25\\u91CD\\u548C\\u7A81\\u53D1\\u6027\\u4F4E\\u8840\\u538B\\uFF0C\\u5FC3\\u8DF3\\u52A0\\u901F\\u6216\\u51CF\\u6162\\uFF0C\\u5F02\\u5E38\\u75B2\\u4E4F\\u6216\\u5E94\\u5F31\\uFF0C\\u7126\\u8651\\uFF0C\\u795E\\u7ECF\\u7D27\\u5F20\\uFF0C\\u610F\\u8BC6\\u4E27\\u5931\\uFF0C\\u547C\\u5438\\u548C\\u541E\\u54BD\\u56F0\\u96BE\\uFF0C\\u7619\\u75D2\\uFF08\\u7279\\u522B\\u5728\\u8DB3\\u90E8\\u6216\\u624B\\u638C\\uFF09\\uFF0C\\u4F34\\u968F\\u6216\\u4E0D\\u4F34\\u968F\\u8840\\u7BA1\\u795E\\u7ECF\\u6027\\u6C34\\u80BF\\u7684\\u76AE\\u75B9\\uFF08\\u80BF\\u80C0\\u548C\\u7619\\u75D2\\u7684\\u76AE\\u80A4\\u533A\\u57DF\\u591A\\u89C1\\u4E8E\\u672B\\u7AEF\\u3001\\u5916\\u751F\\u6B96\\u5668\\u548C\\u9762\\u90E8\\uFF0C\\u7279\\u522B\\u662F\\u773C\\u775B\\u548C\\u5634\\u5507\\u5468\\u56F4\\uFF09\\uFF0C\\u76AE\\u75B9\\uFF08\\u7279\\u522B\\u662F\\u8033\\u90E8\\u5468\\u56F4\\uFF09\\uFF0C\\u6076\\u5FC3\\uFF0C\\u5455\\u5410\\uFF0C\\u80C3\\u7EDE\\u75DB\\uFF0C\\u8179\\u6CFB\\uFF0C\\u90FD\\u66FE\\u6709\\u62A5\\u9053\\u3002\\n\\u6781\\u7F55\\u89C1\\uFF08\\uFF1C1/10000\\uFF09</p>\\n\\n\\u8109\\u7BA1\\u708E\\uFF0C\\u4F34\\u6709\\u77ED\\u6682\\u7684\\u80BE\\u635F\\u5BB3\\u3002\\u795E\\u7ECF\\u7CFB\\u7EDF\\u7D0A\\u4E71\\uFF0C\\u5982\\u8111\\u9AD3\\u708E\\u3001\\u795E\\u7ECF\\u708E\\u548C\\u683C\\u6797\\u5DF4\\u5229\\u7EFC\\u5408\\u75C7[\\u5468\\u56F4\\u795E\\u7ECF\\u7CFB\\u7EDF\\u6025\\u6027\\u708E\\u75C7\\uFF08\\u591A\\u53D1\\u795E\\u7ECF\\u708E\\uFF09\\uFF0C\\u4E3B\\u8981\\u9020\\u6210\\u8FD0\\u52A8\\u969C\\u788D\\uFF08\\u9EBB\\u75F9\\uFF09]\\n\\u8FD9\\u4E9B\\u4E0D\\u826F\\u53CD\\u5E94\\u901A\\u5E38\\u662F\\u4E00\\u8FC7\\u6027\\u7684\\uFF0C\\u5728\\u53CD\\u5E94\\u51FA\\u73B0\\u65F6\\u6700\\u597D\\u54A8\\u8BE2\\u533B\\u751F\\u3002\\u5F53\\u53D1\\u751F\\u8BF4\\u660E\\u4E66\\u4E0A\\u672A\\u63D0\\u5230\\u7684\\u4EFB\\u4F55\\u4E0D\\u826F\\u53CD\\u5E94\\u65F6\\uFF0C\\u5E94\\u7ACB\\u5373\\u901A\\u77E5\\u533B\\u751F\\u3002";
//        String s = EncodeUtil.unicodeToString(str);
//        System.out.println(s);  //木

//        String str = "木";
//        String s = EncodeUtil.stringToUnicode(str);
//        System.out.println(s);  //Ox6728

//        String str = "木";
//    	String s = EncodeUtil.convertStringToUTF8(str);
//    	System.out.println(s);  //E69CA8

        String str = "E69CA8";
        String s1 = convertUTF8ToString(str);
        System.out.print(s1);  //木

    }

    /**
     * 返回文本匹配的第一个正则表达式
     * @param context
     * @param regexRule
     * @return
     */
    public static String regexText(String context,String regexRule){

        Pattern p = Pattern.compile(regexRule);
        Matcher m = p.matcher(context);
        if(m.find()){
            return m.group(1);//由于regexRule只有一个组、所以只需要匹配1即可。可以使用m.groupCount()判断组数量多少
        }
        return null;
    }

    /**
     * 获得汉字拼音
     * @param src
     * @return
     */
    public static String getPingYin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else {
                    t4 += java.lang.Character.toString(t1[i]);
                }
            }
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }

    /**
     * 得到中文首字母
     *
     * @param str
     * @return
     */
    public static String getPinYinHeadChar(String str) {

        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    /**
     * 将字符串转移为ASCII码
     *
     * @param cnStr
     * @return
     */
    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }

    /**
     * Unicode转 汉字字符串
     *
     * @param str \u6728
     * @return '木' 26408
     */
    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            //group 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }

    /**
     * 获取字符串的unicode编码
     * 汉字“木”的Unicode 码点为Ox6728
     *
     * @param s 木
     * @return \ufeff\u6728  \ufeff控制字符 用来表示「字节次序标记（Byte Order Mark）」不占用宽度
     * 在java中一个char是采用unicode存储的 占用2个字节 比如 汉字木 就是 Ox6728 4bit+4bit+4bit+4bit=2字节
     */
    public static String stringToUnicode(String s) {
        try {
            StringBuffer out = new StringBuffer("");
            //直接获取字符串的unicode二进制
            byte[] bytes = s.getBytes("unicode");
            //然后将其byte转换成对应的16进制表示即可
            for (int i = 0; i < bytes.length - 1; i += 2) {
                out.append("\\u");
                String str = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str.length(); j < 2; j++) {
                    out.append("0");
                }
                String str1 = Integer.toHexString(bytes[i] & 0xff);
                out.append(str1);
                out.append(str);
            }
            return out.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 汉字 转换为对应的 UTF-8编码
     * @param s 木
     * @return E69CA8
     */
    public static String convertStringToUTF8(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        try {
            char c;
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);
                if (c >= 0 && c <= 255) {
                    sb.append(c);
                } else {
                    byte[] b;
                    b = Character.toString(c).getBytes("utf-8");
                    for (int j = 0; j < b.length; j++) {
                        int k = b[j];
                        //转换为unsigned integer  无符号integer
						/*if (k < 0)
							k += 256;*/
                        k = k < 0? k+256:k;
                        //返回整数参数的字符串表示形式 作为十六进制（base16）中的无符号整数
                        //该值以十六进制（base16）转换为ASCII数字的字符串
                        sb.append(Integer.toHexString(k).toUpperCase());

                        // url转置形式
                        // sb.append("%" +Integer.toHexString(k).toUpperCase());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * UTF-8编码 转换为对应的 汉字
     *
     * @param s E69CA8
     * @return 木
     */
    public static String convertUTF8ToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        try {
            s = s.toUpperCase();
            int total = s.length() / 2;
            //标识字节长度
            int pos = 0;
            byte[] buffer = new byte[total];
            for (int i = 0; i < total; i++) {
                int start = i * 2;
                //将字符串参数解析为第二个参数指定的基数中的有符号整数。
                buffer[i] = (byte) Integer.parseInt(s.substring(start, start + 2), 16);
                pos++;
            }
            //通过使用指定的字符集解码指定的字节子阵列来构造一个新的字符串。
            //新字符串的长度是字符集的函数，因此可能不等于子数组的长度。
            return new String(buffer, 0, pos, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }


}
