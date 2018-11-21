package com.xinshai.xinshai.controller;

import com.alibaba.fastjson.JSON;
import com.xinshai.xinshai.entiry.LocationParams;
import com.xinshai.xinshai.entiry.SemParams;
import com.xinshai.xinshai.servlet.TokenThread;
import com.xinshai.xinshai.util.WeixinUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/fet")
public class SearchController {

  /*  {
        "query":"查一下明天从北京到上海的南航机票",
            "city":"北京",
            "category": "flight,hotel",
            "appid":"wxaaaaaaaaaaaaaaaa",
            "uid":"123456"
    }
*/

    @ResponseBody
    @RequestMapping("/fet")
    public String searchSemantic(){

        SemParams sem = new SemParams();
        sem.setQuery("查一下明天从北京到上海的南航机票");
        sem.setCity("北京");
        sem.setCategory("flight,hotel");
        sem.setAppid(WeixinUtil.APPID);
        sem.setUid("ofhhXxFRKiAg5NJgPCSe1qlbirS8");
        String msg = JSON.toJSONString(sem);
        return WeixinUtil.searchParam(TokenThread.accessToken.getToken(),msg);

    }

    @ResponseBody
    @RequestMapping("/fet1")
    public String searchSemantic1(){

        LocationParams sem = new LocationParams();
        sem.setLocation("广州");
        sem.setName("仙庙烧鸡");
        sem.setCategory("粤菜");
        sem.setPrice("100");
        sem.setCoupon(2);
        sem.setSort(0);
        sem.setRadius("2000");
        String msg = JSON.toJSONString(sem);
        return WeixinUtil.searchParam(TokenThread.accessToken.getToken(),msg);

    }

}
