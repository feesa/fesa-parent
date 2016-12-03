package com.fs.commons.portal.repository;

import com.fs.commons.app.pojo.UserPasswordPojo;

public interface IUserPasswordRepository {
    Boolean addPassword(UserPasswordPojo userpswd);  
    UserPasswordPojo getUserPassword(String mail);
    UserPasswordPojo getUserPasswordByUrl(String sendurl);
    Boolean updatePwd(Integer uid,String newPwd);
}
