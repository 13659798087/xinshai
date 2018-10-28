package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.entiry.AccessToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccessTokenDao {

    @Update("UPDATE accesstoken SET AccessToken=#{token},LastTime = #{lastTime} where id = 1 ")
    void updateToken(@Param(value = "token") String token, @Param(value = "lastTime") long lastTime);

    @Select("SELECT * FROM accesstoken ")
    AccessToken getAccessToken();


}
