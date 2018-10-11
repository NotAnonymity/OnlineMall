package org.jishionlinemall.mall.Service.serviceImpl;

import org.jishionlinemall.mall.DTO.FeedbackDTO;
import org.jishionlinemall.mall.Entity.Feedback;
import org.jishionlinemall.mall.Entity.FeedbackRepository;
import org.jishionlinemall.mall.Entity.User;
import org.jishionlinemall.mall.Entity.UserRepository;
import org.jishionlinemall.mall.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private FeedbackRepository feedbackRepository;

    @Autowired // 自动在容器中寻找对应接口的实现并装载，不需要显式调用该方法
    public void setRepository(UserRepository userRepository, FeedbackRepository feedbackRepository){
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public boolean isNewUser(String openId){
        if(userRepository.findByOpenId(openId) != null)
            return false;
        else return true;
    }

    @Override
    public void insertNewUser(String openId){
        User user = new User();
        user.setOpenId(openId);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void feedback(FeedbackDTO feedbackDTO){
        Feedback feedback = new Feedback();
        feedback.setDescription(feedbackDTO.getDescription());
        feedback.setUserId(userRepository.findByOpenId(feedbackDTO.getOpenId()).getId());
        feedbackRepository.saveAndFlush(feedback);
    }
}
