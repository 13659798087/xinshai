package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.Hospital;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface HospitalDao {

    @Select("SELECT * FROM Hospital ")
    List<Hospital> getLocation();

    @Select("SELECT COUNT(1) FROM location")
    long getLocationCount();

    /*@Insert(" insert into location (id,hospital,name,address,latitude,longitude,scale,infoUrl) values " +
            " (#{id},#{hospital},#{name},#{address},#{latitude},#{longitude},#{scale},#{infoUrl} ) ")
    void createLocation(@Param(value = "id") String id, @Param(value = "hospital")String hospital,
                        @Param(value = "name") String name, @Param(value = "address") String address,
                        @Param(value = "latitude") String latitude, @Param(value = "longitude") String longitude,
                        @Param(value = "scale") String scale,@Param(value = "infoUrl")  String infoUrl);*/


    @Update("update hospital set h_name=#{h_name},h_address_name=#{h_address_name},h_address=#{h_address}," +
            " h_latitude=#{h_latitude},h_longitude=#{h_longitude},h_scale=#{h_scale},h_infoUrl=#{h_infoUrl}," +
            " weixinName=#{weixinName},appid=#{appid},appsecret=#{appsecret} ")
    void updateLocation(@Param(value = "h_name") String h_name, @Param(value = "h_address_name")String h_address_name,
                        @Param(value = "h_address") String h_address, @Param(value = "h_latitude") String h_latitude,
                        @Param(value = "h_longitude") String h_longitude, @Param(value = "h_scale") String h_scale,
                        @Param(value = "h_infoUrl") String h_infoUrl,@Param(value = "weixinName")String weixinName,
                        @Param(value = "appid")String appid,@Param(value = "appsecret")String appsecret );


    @Delete("DELETE FROM location where id = #{id} ")
    void deleteById(String id);

    @Select("SELECT * FROM Hospital ")
    Hospital getLocationById();

    @Select("SELECT APPID FROM Hospital ")
    String getAppid();

    @Select("SELECT APPSECRET FROM Hospital ")
    String getAppsecret();

    @Select("SELECT domainUrl FROM Hospital ")
    String getDomainUrl();
}
