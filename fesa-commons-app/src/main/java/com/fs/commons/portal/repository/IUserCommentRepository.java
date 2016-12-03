package com.fs.commons.portal.repository;

import java.util.List;
import com.fs.commons.app.pojo.UserCommentPojo;

public interface IUserCommentRepository {
	
    Boolean updateUserComment(UserCommentPojo usercomment);
    
    List<UserCommentPojo> getUserComments(int newsid);
}
