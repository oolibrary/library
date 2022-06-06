package study_room.controller;

import study_room.entity.Seat;
import study_room.service.impl.SeatServiceImpl;
import study_room.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Class {@code SeatController} 定义了关于座位相关的操作，包括获取座位信息，修改作为状态
 * @author 蒋若曦
 */
@Controller
public class SeatController {
    @Autowired
    SeatServiceImpl seatService;
    /**
     * {@link #seat(Map)}
     *
     * <p>获取全部座位信息
     * <a href="http://localhost:8080/seat">Apache's Commons Lang</a>
     * @param map 返回体
     * @return "SeatResver"
     */
    @GetMapping("/seat")
    public String seat(Map<String,Object> map){
        List<Seat> seats = seatService.getAll();
        map.put("seats",seats);
        return "SeatResver";
    }
    /**
     * {@link #updateState(Integer)}
     *
     * <p>前端发起请求来修改座位状态，置为0 即取消预约
     * <a href="http://localhost:8080/updateSeatState">Apache's Commons Lang</a>
     * @param seatid 座位编号
     * @return msg
     */
    @PutMapping("/updateSeatState/{seatid}")
    @ResponseBody
    public Msg updateState(@PathVariable("seatid") Integer seatid){
        boolean b = seatService.updateSeatState(seatid);
        if (b){
            return Msg.success().add("va_msg","状态位置0成功！");
        }else {
            return Msg.fail().add("va_msg","状态位置0失败！");
        }
    }
    /**
     * {@link #updateState(Integer)}
     *
     * <p>前端发起请求来修改座位状态 更改为已预约或立即上座
     * <a href="http://localhost:8080/updateSeatStateYY">Apache's Commons Lang</a>
     * @param seatid 座位编号
     * @param status 座位状态
     * @return msg
     */
    //前端发起请求来修改座位状态，置为1
    @PutMapping("/updateSeatStateYY")
    @ResponseBody
    public Msg updateState1(@RequestParam("seatid") Integer seatid,
                            @RequestParam("status") String status){
        boolean b = seatService.updateSeatState1(seatid,status);
        if (b){
            return Msg.success().add("va_msg","预约成功！");
        }else {
            return Msg.fail().add("va_msg","预约失败!");
        }
    }
}
