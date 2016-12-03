package com.fs.commons.portal.repository;

import java.util.List;
import com.fs.commons.app.pojo.UserReadPojo;

public interface IUserReadRepository {
	
	Boolean updateUserRead(UserReadPojo userred);
	
	List<UserReadPojo> getUserReads(int newsid,int type);
}
