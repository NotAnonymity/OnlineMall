package org.jishionlinemall.mall.Controller;

import org.jishionlinemall.mall.DTO.FeedbackDTO;
import org.jishionlinemall.mall.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserActionController {
    private Logger log = LoggerFactory.getLogger(VIPActionController.class);
    private UserService userService;

    @Autowired
    public void registService(UserService userService){
        this.userService = userService;
    }

    //用户信息反馈
    @RequestMapping("/feedback")
    public FeedbackDTO feedback(@RequestBody FeedbackDTO feedbackDTO){
        try{
            userService.feedback(feedbackDTO);
            log.info("添加反馈成功");
            feedbackDTO.setStatus("success");
        }catch (Exception e){
            feedbackDTO.setStatus("fail");
            feedbackDTO.setErrMsg("数据库连接失败");
        }
        return feedbackDTO;
    }

}
