package study_room.controller;

import study_room.entity.Statistic;
import study_room.service.impl.StatisticServiceImpl;
import study_room.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Class {@code StatisticController} 定义了关于首页图标信息相关的操作
 * @author 蒋若曦
 */
@Controller
public class StatisticController {
    @Autowired
    StatisticServiceImpl statisticService;
    /**
     * {@link #toChartsPage()}
     *
     * <p>跳转到图表分析界面
     * <a href="http://localhost:8080/charts">Apache's Commons Lang</a>
     * @return "Charts"
     */
    @GetMapping("/charts")
    public String toChartsPage(){
        return "Charts";
    }
    /**
     * {@link #getAllUser(Map)}
     *
     * <p>获取全部信息
     * <a href="http://localhost:8080/user22">Apache's Commons Lang</a>
     * @param map 返回体
     * @return "SeatChart"
     */
    @GetMapping("/user22")
    public String getAllUser(Map<String,Object> map){
        List<Statistic> statistics = statisticService.getAll();
        map.put("statistics",statistics);
        return "SeatChart";
    }
    /**
     * {@link #staInfo()}
     *
     * <p>获取信息展示到页面中
     * <a href="http://localhost:8080/getStaInfo">Apache's Commons Lang</a>
     * @return 相关静态数据
     */
    @GetMapping("/getStaInfo")
    @ResponseBody
    public Msg staInfo(){
        List<Statistic> statistics = statisticService.getAll();
        return Msg.success().add("statistics",statistics);
    }
}
