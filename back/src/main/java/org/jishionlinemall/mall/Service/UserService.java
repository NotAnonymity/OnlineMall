package org.jishionlinemall.mall.Service;

import org.jishionlinemall.mall.DTO.FeedbackDTO;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    boolean isNewUser(String openId);//根据OpenId查询数据库，判断是否是新用户
    void insertNewUser(String openId);//添加新用户的OpenId
    @Transactional
    void feedback(FeedbackDTO feedbackDTO);//添加反馈
}
