/*
package com.xinshai.xinshai.util;

import com.xinshai.xinshai.entiry.AccessToken;
import com.xinshai.xinshai.entiry.groupMessage.*;
import com.xinshai.xinshai.servlet.TokenThread;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUpload {

    */
/**
     * 模拟form表单的形式 ，上传文件 以输出流的形式把文件写入到url中，然后用输入流来获取url的响应
     * @param url
     *            请求地址 form表单url地址
     * @param filePath
     *            文件在服务器保存路径
     * @return String url的响应信息返回值
     * @throws IOException
     *//*

    public String send(String url, String filePath) throws IOException {
        String result = null;
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }
        */
/**
         * 第一部分
         *//*

        URL urlObj = new URL(url);
        // 连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        */
/**
         * 设置关键值
         *//*

        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "---------------------------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
                + BOUNDARY);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
                + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件以 流文件 的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 最数据分隔线
        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
            throw new IOException("数据读取异常");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }

   */
/* public String upload(){
        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=NkwaEYtrewLfkgMphjwUmnZ0l6vguB794RBhstvHUT58nLk2HMrQ3Ji9EJTD8aOyViteTyV6H1xrVLx2Myee1MUdwz0xEcw0gmlBpf6VkFoMQRfAAAQUV";//ACCESS_TOKEN是获取到的access_token
        //上传的图文消息数据，其中thumb_media_id是文件上传图片上传的id
        String data = "{\"articles\": [{\"thumb_media_id\":\"BW4eDIdYSvO7AFjfsZKsQ9ujNma_TkCj3VSo3JNTQkYmk_iPuhpUKm48oZ4umHED\",\"author\":\"xxx\",\"title\":\"Happy Day\",\"content_source_url\":\"www.qq.com\",\"content\":\"content\",\"digest\":\"digest\",\"show_cover_pic\":\"0\"}]}";
        String data1 = "{\"articles\":[{\"author\":\"王传清|毕强|Wang Chuanqing|Bi Qiang\",\"content\":\"基于关联关系维度的数字资源聚合是数字资源知识发现的重要基础和工具。超网络是由多个类型的同质和异质子网络组成的网络，通过多种关联维度聚合的数字资源即形成了拥有相同以及不同性质的结点和关系的数字资源超网络，这些不同性质的关联与链接是知识关联、挖掘、发现与创新的脉络线索。结合超网络理论，构建和描述数字资源超网络，并分析超网络中不同性质的关系类型，如引用关系、共现关系、耦合关系等，从关联维度探讨数字资源深度聚合的模式，进而分析利用数字资源超网络进行知识发现的具体应用方法，最后构建数字资源超网络聚合系统模型。\",\"content_source_url\":\"http://d.g.wanfangdata.com.cn/Periodical_qbxb201501002.aspx\",\"digest\":\"测试\",\"show_cover_pic\":1,\"thumb_media_id\":\"BW4eDIdYSvO7AFjfsZKsQ9ujNma_TkCj3VSo3JNTQkYmk_iPuhpUKm48oZ4umHED\",\"title\":\"超网络视域下的数字资源深度聚合研究\"}]}";
        JSONObject json = CommUtil.httpRequest(url, "POST", data1);
        return json.toString();
    }*//*

   */
/* public static void main(String[] args) {
        System.out.println(new ImageArticleUpload().upload());
    }*//*


    public static void main(String[] args) throws IOException {
        String filePath = "D:/13.jpg";//本地或服务器文件路径

        AccessToken token = WeixinUtil.getAccessToken();

        String sendUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=image".replace("ACCESS_TOKEN",token.getToken());//ACCESS_TOKEN是获取到的access_token
        String result = null;
        FileUpload fileUpload = new FileUpload();
        result = fileUpload.send(sendUrl, filePath);
        System.out.println(result);

        JSONObject jsonObj = JSONObject.fromObject(result);
        System.out.println(jsonObj);
        String typeName = "media_id";

        String mediaId = jsonObj.getString(typeName);


        ImageTextMessage imageText = new ImageTextMessage();

        List<String> touser =  new ArrayList<String>();
        touser.add("ofhhXxFRKiAg5NJgPCSe1qlbirS8");
        touser.add("ofhhXxOY8b05I4uZ9UazShDaaDsM");

        Map<String,Object> mpnews = new HashMap<String,Object>();
        mpnews.put("thumb_media_id",mediaId);


        imageText.setMpnews(mpnews);
        imageText.setTouser(touser);
        imageText.setMsgtype("mpnews");
        imageText.setSend_ignore_reprint(0);

        String data1 = JSONObject.fromObject(imageText).toString();

        int a = WeixinUtil.uploadNews(token.getToken(), data1);

    }
}*/
