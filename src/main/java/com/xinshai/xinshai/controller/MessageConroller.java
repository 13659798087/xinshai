package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.Report;
import com.xinshai.xinshai.model.Combine;
import com.xinshai.xinshai.services.CombineServices;
import com.xinshai.xinshai.util.JasperUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/message")
public class MessageConroller {

    @Resource
    private CombineServices combineServices;

    @Resource
    private JasperUtil jasperUtil;
    private String view = "message/";


    @RequestMapping("/getMessage")
    public String getMessage(){
        return view + "getMessage";

    }

    @RequestMapping("/photo")
    public String photo(){
        return view + "photo";

    }

    @RequestMapping("/printReport")
    public void myprint(HttpServletResponse response,String c_sid,String c_combine,String rptno) throws Exception {
        String fileName = "";

        List<Combine> combine = combineServices.getCombine();
        for(Combine c : combine){
            if(c_combine.equals(c.getC_code())){
                // fileName = c.getC_rpt()+".jasper";//拼接文件后缀pdf,此处是下载pdf文件
                switch (rptno){
                    case "1":
                        fileName = c.getC_rpt()+".jasper";//拼接文件后缀pdf,此处是下载pdf文件
                        break;
                    case "2":
                        fileName = c.getC_rpt2()+".jasper";
                        break;
                    case "3":
                        fileName = c.getC_rpt3()+".jasper";
                        break;
                    case "4":
                        fileName = c.getC_rpt4()+".jasper";
                        break;
                }

            }
        }

        response.setContentType("multipart/form-data");
        BufferedImage bufferedImage = genPdf(c_sid,c_combine,fileName);

        ServletOutputStream out = response.getOutputStream();//新建流。

        //ImageIO.write(bufferedImage, "JPEG", new File("d:/aa.jpg"));
        ImageIO.write(bufferedImage, "jpg",out );

        out.flush();
        out.close();
    }


    public BufferedImage genPdf(String c_sid,String c_combine,String fileName) {
        List<Report> userLs = new ArrayList<Report>();
        Map<String, Object> map = new HashMap<String, Object>();

        /*map.put("cCombine", "ct"); map.put("cSid", "C1700530");*//*map.put("c_combine", "cl"); map.put("c_sid", "20170967");*///map.put("c_id", "ts180529001");//map.put("c_id", " wc20180529002");// map.put("c_id", "dp20180529001");
        map.put("c_sid", c_sid);
        map.put("c_combine", c_combine);


        return jasperUtil.exportPdfDir1(fileName, map, userLs);
    }

}
