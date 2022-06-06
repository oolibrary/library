package study_room.controller;

import study_room.entity.FeedBack;
import study_room.service.impl.FeedBackServiceImpl;
import study_room.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * Class {@code FeedBackController}
 * 定义了关于反馈的内容，包括跳转反馈页面，插入反馈内容
 * @author 杨宇涵
 */
@Controller
public class FeedBackController {

    @Autowired
    FeedBackServiceImpl feedBackService;
    /**
     * {@link #toFeedbackPage(Map)}
     *
     * <p> 跳转到投诉反馈页面并且获取数据库中feedback数据
     * <a href="http://localhost:8080/toFeedbackPage">Apache's Commons Lang</a>
     * @return "Feedback"
     */
    @GetMapping("/toFeedback")
    public String toFeedbackPage(Map<String ,Object> map){
        List<FeedBack> feedBacks = feedBackService.getAll();
        map.put("fds",feedBacks);
        return "Feedback";
    }
    /**
     * {@link #toFeedbackPage(Map)}
     *
     * <p> 向投诉数据中插入数据
     * <a href="http://localhost:8080/insertCpText">Apache's Commons Lang</a>
     * @param feedBack 反馈信息
     * @return 反馈内容插入成功/失败
     */
    @PostMapping("/insertCpText")
    @ResponseBody
    public Msg insertCpText(FeedBack feedBack){
        feedBack.setFdid(null);
        Date date = new Date();
        feedBack.setFdtime(date);
        boolean b = feedBackService.insertCpText(feedBack);
        if(b){
            return Msg.success().add("va_msg","反馈内容插入成功！");
        }else {
            return Msg.fail().add("va_msg","反馈内容插入失败！");
        }
    }


}
