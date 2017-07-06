package com.blog.common.base.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目String相关工具类，继续apache StringUtils工具类
 */
public class TextUtil extends StringUtils {

    static String[] NUM = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成数据数字序列
     *
     * @param size
     *            序列长度
     * @return
     */
    public static String genNumVerification(int size) {
        StringBuffer vNum = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            vNum.append(NUM[random.nextInt(9)]);
        }
        return vNum.toString();
    }

    /**
            * 去掉字符串中的html标记
     *
             * @param s           源字符串
     * @param replacement
     * @return
             */
    public static String cleanHTML(String s, String replacement) {
        if (s == null) {
            return s;
        }
        if (replacement == null) {
            replacement = "";
        }
        String html = new String(s);
        String[] htmlTag = {""};
        String regex = "";

        html = Pattern.compile("<!--((?!<!--).)*-->", Pattern.DOTALL).matcher(html).replaceAll(replacement);
        html = Pattern.compile("<script((?!</script).)*</script>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE).matcher(html).replaceAll(replacement);
        html = Pattern.compile("<style((?!</style).)*</style>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE).matcher(html).replaceAll(replacement);

        for (int i = 0; i < htmlTag.length; i++) {
            regex = "<" + htmlTag[i] + "[^<]*>";
            html = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(html).replaceAll(replacement);
            regex = "</" + htmlTag[i] + ">";
            html = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(html).replaceAll(replacement);
        }
        //html = html.replaceAll("\\s+", replacement);
        html = html.replaceAll("&nbsp;", "");
        html = html.replaceAll("\r\n", "");
        html = html.replaceAll("\n", "");
        html = html.replaceAll("\t", "");
        html = html.replaceAll("\\s*", "");
        html = html.replaceAll("　", "");
        html = HtmlUtils.htmlEscape(html);
        html = HtmlUtils.htmlUnescape(html);
        return html;
    }

    public static String getJavascriptFromHtml(String html) {
        //1.将所有的回车变成\n
        html = html.replace("\\", "\\\\");
        html = html.replace("'", "\\'");
        html = html.replace("\r\n", "\\n");
        html = html.replace("\n", "\\n");
        html = "document.write('" + html + "');";
        return html;
    }

    /**
     * 根据传入的长度截取字符串
     *
     * @param src  String
     * @param len  int
     * @param plac String 如果字符串超过，将以该字符串作为后缀
     * @return String
     */
    public static String cutString(String src, int len, String plac) {
        if (src == null) {
            return "";
        }
        if (plac == null || "".equals(plac.trim())) {
            plac = "";
        }

        try {
            if (src.getBytes("UTF-8").length <= len) {
                return src;
            }
            byte[] bytes = src.getBytes("UTF-8");
            int idx = len - plac.getBytes("UTF-8").length;
            if (idx < 0) {
                return "";
            }
            String result = new String(bytes, 0, idx, "UTF-8");
            String test = src.substring(0, result.length());
            if (test.equals(result)) {
                return result + plac;
            }
            idx--;
            result = new String(bytes, 0, idx, "UTF-8");
            return result + plac;

        } catch (Exception e) {
            e.printStackTrace();
            return src;
        }
    }


    public static String cutStringOnCN(String s, int n, String last) {
        String result = "";
        Pattern p = Pattern.compile("^[\\u4e00-\\u9fa5]$");
        int i = 0, j = 0;
        for (char c : s.toCharArray()) {
            Matcher m = p.matcher(String.valueOf(c));
            i += m.find() ? 2 : 1;
            ++j;
            if (i == n) break;
            if (i > n) {
                --j;
                break;
            }
        }
        if (last != null) {
            result = s.substring(0, j) + last;
        }
        return result;
    }


    /**
     * 转换传入的字符串转义字符(例如: &yen; 返回 ¥)
     *
     * @param str String
     * @return String
     */
    public static String parseHtml(String str) {
        if (org.apache.commons.lang.StringUtils.isBlank(str)) return null;

        String[] strArray = str.split("&");
        int strLength = strArray.length;
        if (strLength == 1) return str;

        List resList = new ArrayList();
        for (int i = 0; i < strLength; i++) {
            try {
                String strValue = strArray[i];
                int end = strValue.indexOf(";") + 1;
                if (end < 2) continue;

                String targetValue = "&" + strValue.substring(0, end);
                String resultValue = StringEscapeUtils.unescapeHtml(targetValue);

                if (resultValue.equals(targetValue)) continue;
                if (strValue.length() == end) {
                    strArray[i] = resultValue;
                } else {
                    strArray[i] = resultValue + strValue.substring(end);
                }
                resList.add(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        StringBuilder finalValue = new StringBuilder();
        for (int k = 0; k < strLength; k++) {
            String strValue = "";
            if (resList.contains(k) || k == 0) {
                strValue = strArray[k];
            } else {
                strValue = "&" + strArray[k];
            }
            finalValue.append(strValue);
        }

        return str.endsWith("&") ? finalValue.append("&").toString() : finalValue.toString();
    }

    /**
     * api调用请求流水号生成
     * @return
     */
    public static String getRequstsn(){
            String dateString = DateFormatUtils.format(new java.util.Date(), "yyyyMMddHHmmss");
            Random r = new Random(System.currentTimeMillis());
            String rr = Long.toString(r.nextLong());
            String rNum = rr.substring(5, 10);
            String no = dateString + rNum;
            return no;
    }


}
