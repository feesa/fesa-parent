package com.fs.commons.portal.repository;

import java.util.*;
import com.fs.commons.app.pojo.UserInfoPojo;

public interface IUserInfoRepository {
    Boolean addUser(UserInfoPojo userinfo);  
    UserInfoPojo getUserInfo(String name,String pswd);
    UserInfoPojo getUserInfoBymark(String name,String pswd);
    UserInfoPojo getUserEmail(String email);
    Boolean changePassword(int uid,String newpwd);
    List<UserInfoPojo> getAllUsers();
}
