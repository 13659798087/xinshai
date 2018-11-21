package com.xinshai.xinshai.util;

import com.google.gson.Gson;
import com.xinshai.xinshai.entiry.WeatherData;
import com.xinshai.xinshai.entiry.WeatherResp;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

public class WeatherUtil {

    public static String getWeatherResult(String cityName) throws Exception{

        String requestUrl="http://api.map.baidu.com/telematics/v3/weather?location={LOCATION}&output=json&ak={KEY}";
        String requestWeatherUrl=requestUrl.replace("{LOCATION}", URLEncoder.encode(cityName, "UTF-8")).replace("{KEY}", "E4805d16520de693a3fe707cdc962045");

        String result = CommonUtil.httpRequest(requestWeatherUrl, "POST", null);
        //System.out.println(result);
        Gson gson=new Gson();
        WeatherResp wresp=gson.fromJson(result, WeatherResp.class);

        StringBuffer buf=new StringBuffer();
        buf.append(cityName+"天气预报"+"\n\n");

        List<WeatherData> wdata=wresp.getResults().get(0).getWeather_data();
        Iterator<WeatherData> it=wdata.iterator();
        while(it.hasNext()){
            WeatherData w=it.next();
            buf.append(w.getDate()+","+w.getWeather()+","+w.getTemperature()+","+w.getWind());
            buf.append("\n\n");
        }
        return buf.toString();
    }

    public static void main(String args[]) throws Exception{
        System.out.println(getWeatherResult("广州"));
    }

}
