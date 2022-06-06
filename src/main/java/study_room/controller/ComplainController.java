package study_room.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import study_room.entity.Complain;
import study_room.service.impl.ComplainServiceImpl;
import study_room.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Class {@code ComplainController}
 * 定义了关于投诉的相关内容，包括跳转投诉页面，返回投诉列表，更新投诉处理状态，删除投诉信息，查找用户信息
 * @author 杨宇涵
 */
@Controller
public class ComplainController {

    @Autowired
    ComplainServiceImpl complainService;
    /**
     * {@link #toComplainPage(Map)}
     *
     * <p> 跳转到投诉页面
     * <a href="http://localhost:8080/toComplain">Apache's Commons Lang</a>
     * @return "Complain"
     */
    @RequestMapping("/toComplain")
    public String toComplainPage(Map<String,Object> map){
        List<Complain> complains = complainService.getAll();
        map.put("cps",complains);
        return "Complain";
    }
    /**
     * {@link #ComplainData(Integer)}
     *
     * <p> 向前端返回投诉信息
     * <a href="http://localhost:8080/complainList">Apache's Commons Lang</a>
     * @param pn 页号
     * @return comlains表中全部信息
     */
    @GetMapping("/complainList")
    @ResponseBody
    public Msg ComplainData(@RequestParam(value = "pn",defaultValue = "1")Integer pn){
        PageHelper.startPage(pn,6);
        List<Complain> complains = complainService.getAll();
        PageInfo<Complain> page= new PageInfo<>(complains, 5);
        return Msg.success().add("pageInfo",page);
    }
    /**
     * {@link #updatePss(Integer)}
     *
     * <p> 更新投诉信息
     * <a href="http://localhost:8080/updatePs">Apache's Commons Lang</a>
     * @param cpid 投诉编号
     * @return 状态更新成功/失败
     */
    @PutMapping("/updatePs")
    @ResponseBody
    public Msg updatePss(Integer cpid){
        boolean b = complainService.updatePs(cpid);
        if (b){
            return Msg.success().add("va_msg","ps状态更新成功");
        }else{
            return Msg.fail().add("va_msg","ps状态更新失败");
        }
    }
    /**
     * {@link #deleteCp2(int)}
     *
     * <p> 根据cpid删除投诉数据
     * <a href="http://localhost:8080/deleteCp">Apache's Commons Lang</a>
     * @param cpid 投诉编号
     * @return 删除成功/失败
     */
    @DeleteMapping("/deleteCp/{cpid}")
    @ResponseBody
    public Msg deleteCp2(@PathVariable("cpid") int cpid){
        boolean b = complainService.deleteCp(cpid);
        if (b){
            return Msg.success().add("va_msg","cp删除成功");
        }else{
            return Msg.fail().add("va_msg","cp删除成功");
        }
    }
    /**
     * {@link #queryUserID(Integer)}
     *
     * <p> 点击编辑按键传入userid查询用户，返回用户信息
     * <a href="http://localhost:8080/queryCpByID">Apache's Commons Lang</a>
     * @param cpid 投诉编号
     * @return 目标用户的投诉信息
     */
    @GetMapping("/queryCpByID/{cpid}")
    @ResponseBody
    public Msg queryUserID(@PathVariable("cpid") Integer cpid){
        Complain cpByID = complainService.getCpByID(cpid);
        return Msg.success().add("cp",cpByID);
    }
}
