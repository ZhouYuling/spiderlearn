package com.gzhc365.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import com.gzhc365.vo.Doctor;
import com.gzhc365.vo.MyException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 14:11 2019/7/10 0010
 * @Modified by:
 */
public class ImagesUtils {

    //这个可以自己去百度上申请、每天都有免费次数的
    private static final String APP_ID = "16438814";
    private static final String API_KEY = "7zWsfy40f2hImD2Cm22FIn1R";
    private static final String SECRET_KEY = "7xfzDAyCOZmk4d9ovGrr1H33DFh2S1d1";

    public static void main(String[] args) {

        /**上传单张照片**/
//        String s = null;
//        try {
//            s = uploadPic("c:/images/8e1b503d-23de-45a0-a52b-1b5fd7b6e43e20170605115306030.jpg");
//        } catch (MyException e) {
//            System.out.println(e.getUrl());
//            System.out.println(e.getMessage());
//        }
//        System.out.println(s);

        /**上传mysql中doctor表中图片到dfs上并更新字段**/
        int his_id = 187;
		upMysqlPic(his_id);

        /**将图片转换为文字**/
//        String textContent = getPictureWordByBaiduOrc(System.getProperty("user.dir") + "\\src\\main\\resources\\pic\\test.png");
//        System.out.println(textContent);

    }

    public static String getPictureWordByBaiduOrc(String picLocation){

        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        System.setProperty("aip.log4j.conf", System.getProperty("user.dir") + "src\\main\\resources\\log4j.properties");
        org.json.JSONObject res = client.basicGeneral(picLocation, new HashMap<String, String>());
        return res.toString(2);

    }

    private static void upMysqlPic(int his_id) {
        String hcPicURL = "";
        ArrayList<String> errorList = new ArrayList<String>();

        try {
            List<Doctor> doctorInfo = MysqlUtil.getDoctorInfoByHisId(his_id);
            HashMap<String, String> map = new HashMap<String, String>();//用于去重，避免图片的二次上传。key为本地图片地址，value为dfs上的图片地址
            for (Doctor doctor : doctorInfo) {
                hcPicURL = doctor.getHead_img();
                String s = map.get(hcPicURL);
                try {
                    if(null == s){//如果为空、则表示map没有图片记录
                        s = uploadPic(hcPicURL);//上传图片
                        map.put(hcPicURL,s);//同时把图片本地路径和上传dfs上的地址存入map中
                    }
                } catch (MyException e) {
                    errorList.add(e.getUrl());
                    System.out.println(e.getUrl() + "  " + e.getMessage());
                }
                doctor.setHead_img(s);
                if(s != null && !"".equals(s)){
                    int i = MysqlUtil.updateDocHeadImg(doctor);
                    System.out.println("上传成功：" + doctor.getDoctor_name() + "  " +  doctor.getHead_img());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("上传失败的列表：" + errorList);
    }

    public static String downImages(String filePath,String imageUrl) throws MyException {
        // 截取图片的名称
        String picName = "";
        try{
            picName = imageUrl.substring(imageUrl.lastIndexOf("/"));
        }catch (Exception e){
            throw new MyException(imageUrl,"imgUrl截取lastIndexof('/')失败");
        }

        String localPath = filePath + picName;
        //创建文件的目录结构
        File files = new File(filePath);
        if(!files.exists()){// 判断文件夹是否存在，如果不存在就创建一个文件夹
            files.mkdirs();
        }
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            // 创建文件
            File file = new File(localPath);
            FileOutputStream out = new FileOutputStream(file);
            int i = 0;
            while((i = is.read()) != -1){
                out.write(i);
            }
            is.close();
            out.close();
            return localPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(imageUrl,"图片下载失败");
        }
    }

    public static String uploadPic(String filePath) throws MyException {
        try{
            //DFS上传路径、login_access_token
            HttpPost httpPost = new HttpPost("https://eh.med.gzhc365.com/api/ehis/health/api/file/upload?login_access_token=1112323565624-0F403DAA9FC7AA78C62A89");
            //MultipartEntity
            MultipartEntity mutiEntity = new MultipartEntity();
            File file = new File(filePath);
            mutiEntity.addPart("file", new FileBody(file));
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            httpPost.setEntity(mutiEntity);//设置MultipartEntity
            HttpResponse httpResponse = httpClient.execute(httpPost);//发送请求
            HttpEntity httpEntity =  httpResponse.getEntity();
            String content = EntityUtils.toString(httpEntity);//获取返回结果json
            JSONObject jsonObject = JSON.parseObject(content);//进行解析
            JSONObject data = jsonObject.getJSONObject("data");
            return data.getString("url");
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(filePath,"图片上传失败");
        }
    }

}
