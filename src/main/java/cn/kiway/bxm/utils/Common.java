package cn.kiway.bxm.utils;

import com.xiaoleilu.hutool.json.JSONObject;

import java.io.*;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * 公共类
 */
public class Common {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 读取本地文件内容
     * @param path
     * @return
     */
    public static String readFile(String path){
        String lastStr = "";
        File file=new File(path);
        BufferedReader reader=null;
        try{
            FileInputStream in = new FileInputStream(file);
            // 读取文件
            reader=new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String tempString=null;
            while((tempString=reader.readLine())!=null){
                lastStr = lastStr + tempString;
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader!=null){
                try{
                    reader.close();
                }catch(IOException el){
                }
            }
        }
        return lastStr;
    }

    /**
     * 读取本地文件内容
     * @param path
     * @return
     */
    public static JSONObject readJsonFile(String path){
        String retStr = readFile(path);
        JSONObject obj = new JSONObject(retStr);
        return obj;
    }

    public static Timestamp createDate(){
        return new Timestamp(System.currentTimeMillis());
    }


    /***
     * 获取第几年后的当前时间
     * @return
     */
    public static Timestamp getNextYearTime(int year){
        if(year == 0){
            year = 1;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextYear = now.plus(year, ChronoUnit.YEARS);
        return Timestamp.valueOf(nextYear);
    }

    /***
     * @description MD5加密
     * @author yimin
     * @date 2016年9月22日
     * @param target 需要加密的字符串
     * @return 加密后的字符串
     */
    public final static String MD5(String target,String charSet) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = target.getBytes(charSet);
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }

            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
