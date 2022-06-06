package study_room.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import study_room.entity.SeatResver;
import study_room.service.impl.SeatResverServiceImpl;
import study_room.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Class {@code SeatResverController} 定义了关于座位预约相关的操作，包括保存预约信息，检验用户是否预约信息，删除预约信息，获取全部预约信息
 * @author 蒋若曦
 */
@Controller
public class SeatResverController {

    @Autowired
    SeatResverServiceImpl seatResverService;
    /**
     * {@link #insertSeatResver(SeatResver, String)}
     *
     * <p>保存目标预约信息
     * <a href="http://localhost:8080/insertSeatResver">Apache's Commons Lang</a>
     * @param seatResver 预约实体
     * @param status 状态
     * @return msg
     */
    @PostMapping("/insertSeatResver")
    @ResponseBody
    public Msg insertSeatResver(SeatResver seatResver,@RequestParam("status") String status){
        seatResver.setResverid(null);
        seatResverService.insertSeatResver(seatResver,status);
        return Msg.success().add("va_msg","预约座位信息插入成功！");
    }
    /**
     * {@link #checkSeatResverUserID(Integer)}
     *
     * <p>校验该用户是否已经预约
     * <a href="http://localhost:8080/insertSeatResver">Apache's Commons Lang</a>
     * @param userid 用户id
     * @return msg
     */
    @GetMapping("/checkSeatResverUserID/{userid}")
    @ResponseBody
    public Msg checkSeatResverUserID(@PathVariable("userid") Integer userid){
        boolean b = seatResverService.checkUserID(userid);
        if (b){
            return Msg.fail().add("va_msg","该用户已预约或已上座，请重新输入！");
        }else {
            return Msg.success().add("va_msg","");
        }
    }
    /**
     * {@link #deleteSeatResver(Integer)}
     *
     * <p>根据座位号删除预约信息
     * <a href="http://localhost:8080/deleteSeatResver">Apache's Commons Lang</a>
     * @param seatid 座位id
     * @return msg
     */
    @DeleteMapping("/deleteSeatResver/{seatid}")
    @ResponseBody
    public Msg deleteSeatResver(@PathVariable("seatid") Integer seatid){
        boolean b = seatResverService.deleteSeatResver(seatid);
        if (b){
            return Msg.success().add("va_msg","删除预约信息成功！");
        }else {
            return Msg.fail().add("va_msg","删除预约信息失败！");
        }
    }
    /**
     * {@link #getSeatResverInfo(Integer)}
     *
     * <p>获取全部预约信息，返回到前端展示再页面中
     * <a href="http://localhost:8080/getSeatResverInfo">Apache's Commons Lang</a>
     * @param pn 页号
     * @return 预约信息
     */
    @GetMapping("/getSeatResverInfo")
    @ResponseBody
    public Msg getSeatResverInfo(@RequestParam(value = "pn",defaultValue = "1")Integer pn){
        PageHelper.startPage(pn,6);
        List<SeatResver> all = seatResverService.getAll();
        PageInfo<SeatResver> page=new PageInfo<SeatResver>(all,5);
        return Msg.success().add("pageInfo",page);
    }
    /**
     * {@link #getSeatResver(Integer, Integer)}
     *
     * <p>根据座位id模糊查询返回座位预约信息
     * <a href="http://localhost:8080/querySeatlike">Apache's Commons Lang</a>
     * @param pn 页号
     * @param seatid 座位id
     * @return 预约信息
     */
    @GetMapping("/querySeatlike/{seatid}")
    @ResponseBody
    public Msg getSeatResver(@PathVariable("seatid") Integer seatid
            ,@RequestParam(value = "pn",defaultValue = "1")Integer pn){
        PageHelper.startPage(pn,20);
        List<SeatResver> all = seatResverService.getSeatAll(seatid);
        PageInfo<SeatResver> page=new PageInfo<SeatResver>(all,5);
        return Msg.success().add("pageInfo",page);
    }
}
