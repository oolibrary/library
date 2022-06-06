package study_room.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import study_room.entity.SeatResver;
import study_room.entity.Seatuse;
import study_room.service.impl.SeatuseServiceImpl;
import study_room.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Class {@code SeatuseController} 定义了关于座位使用相关的操作，包括查询用户座位使用信息，插入数据等
 * @author 蒋若曦
 */
@Controller
public class SeatuseController {
    @Autowired
    SeatuseServiceImpl seatuseService;
    /**
     * {@link #queryUserID(Integer, Integer)}
     *
     * <p>通过用户ID进行查询
     * <a href="http://localhost:8080/queryByUserID">Apache's Commons Lang</a>
     * @param pn 页号
     * @param userid 用户id
     * @return 座位使用信息
     */
    @GetMapping("/queryByUserID/{userid}")
    @ResponseBody
    public Msg queryUserID(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
                           @PathVariable("userid") Integer userid){
        PageHelper.startPage(pn,20);
        List<Seatuse> users = seatuseService.queryByUserID(userid);
        PageInfo<Seatuse> page=new PageInfo<Seatuse>(users,5);
        return Msg.success().add("pageInfo",page);
    }
    /**
     * {@link #toSeatUserPage(Map)}
     *
     * <p>跳转到座位统计页面
     * <a href="http://localhost:8080/seatUseCount">Apache's Commons Lang</a>
     * @param map 返回体
     * @return "SeatUse"
     */
    @RequestMapping("/seatUseCount")
    public String toSeatUserPage(Map<String,Object> map){
        List<Seatuse> seatusers = seatuseService.getAll();
        map.put("sus",seatusers);
        return "SeatUse";
    }

    @GetMapping("/seatUsePage")
    @ResponseBody
    public Msg SeatUserInfo(@RequestParam(value = "pn",defaultValue = "1") Integer pn){
        PageHelper.startPage(pn,6);
        List<Seatuse> seatusers = seatuseService.getAll();
        PageInfo<Seatuse> page=new PageInfo<Seatuse>(seatusers,5);
        return Msg.success().add("pageInfo",page);
    }

    /**
     * {@link #insertSUInfo(Seatuse, String)}
     *
     * <p>插入数据
     * <a href="http://localhost:8080/insertSUInfo">Apache's Commons Lang</a>
     * @param seatuse 座位使用实体
     * @param status 座位使用状态
     * @return msg
     */
    @PostMapping("/insertSUInfo")
    @ResponseBody
    public Msg insertSUInfo(Seatuse seatuse, @RequestParam("status") String status){
        boolean b = seatuseService.insertSeatUseInfo(seatuse, status);
        if (b){
            return Msg.success().add("va_msg","插入到座位使用记录表成功！");
        }else {
            return Msg.fail().add("va_msg","插入到座位使用记录表失败！");
        }
    }
    /**
     * {@link #deleteSeatUserInfo(Integer)}
     *
     * <p>删除座位使用记录
     * <a href="http://localhost:8080/deleteSeatUseByID">Apache's Commons Lang</a>
     * @param seatuserid 座位使用编号
     * @return msg
     */
    @DeleteMapping("/deleteSeatUseByID/{seatuserid}")
    @ResponseBody
    public Msg deleteSeatUserInfo(@PathVariable("seatuserid") Integer seatuserid){
        boolean b = seatuseService.deleteSeatUserInfo(seatuserid);
        if(b){
            return Msg.success().add("va_msg","删除成功座位使用记录");
        }else {
            return Msg.fail().add("va_msg","删除失败座位使用记录");
        }
    }
    /**
     * {@link #state1Count(String)}
     *
     * <p>获取使用记录条数
     * <a href="http://localhost:8080/state1Count">Apache's Commons Lang</a>
     * @param state 状态
     * @return msg
     */
    @GetMapping("/state1Count/{state}")
    @ResponseBody
    public Msg state1Count(@PathVariable("state") String state){
        long l = seatuseService.queryStateCount(state);
        return Msg.success().add("state1Count",l);
    }
}
