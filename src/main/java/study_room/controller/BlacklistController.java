package study_room.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import study_room.entity.Blacklist;
import study_room.service.impl.BlacklistServiceImpl;
import study_room.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Class {@code BlacklistController} 定义了关于黑名单相关的操作，包括返回黑名单数据，解除名单人员，冻结人员
 * @author 杨宇涵
 */
@Controller
public class BlacklistController {
    @Autowired
    BlacklistServiceImpl blacklistService;
    /**
     * {@link #toBlacklistPage(Map)}
     *
     * <p>跳转到黑名单管理页面，并且返回黑名单数据
     * <a href="http://localhost:8080/toBlacklist">Apache's Commons Lang</a>
     * @return 黑名单列表
     */
    @GetMapping("/toBlacklist")
    public String toBlacklistPage(Map<String ,Object> map){
        List<Blacklist> blacklists = blacklistService.getAll();
        map.put("bls",blacklists);
        return "Blacklist";
    }
    /**
     * {@link #getBlacklist(Integer)}
     *
     * <p>前端请求，返回黑名单数据
     * <a href="http://localhost:8080/getBlacklist">Apache's Commons Lang</a>
     * @param pn page_number 页号
     * @return 每一页的信息
     */
    @GetMapping("/getBlacklist")
    @ResponseBody
    public Msg getBlacklist(@RequestParam(value = "pn",defaultValue = "1")Integer pn){
        PageHelper.startPage(pn,6);
        List<Blacklist> blacklists = blacklistService.getAll();
        PageInfo<Blacklist> page=new PageInfo<Blacklist>(blacklists,5);
        return Msg.success().add("pageInfo",page);
    }
    /**
     * {@link #updateStatusJC(Integer)}
     *
     * <p>前端ajax请求，解除冻结的黑名单
     * <a href="http://localhost:8080/updateStatusJC">Apache's Commons Lang</a>
     * @param blackid 黑名单编号
     * @return 解除成功/解除失败
     */
    @PutMapping("/updateStatusJC")
    @ResponseBody
    public Msg updateStatusJC(Integer blackid){
        boolean b = blacklistService.updateStatusJC(blackid);
        if (b){
            return Msg.success().add("va_msg","解除成功") ;
        }else {
            return Msg.fail().add("va_msg","解除失败");
        }
    }
    /**
     * {@link #updateStatusJC(Integer)}
     *
     * <p>冻结
     * <a href="http://localhost:8080/updateStatusDC">Apache's Commons Lang</a>
     * @param blackid 黑名单编号
     * @return 冻结成功/冻结失败
     */
    @PutMapping("/updateStatusDC")
    @ResponseBody
    public Msg updateStatusDC(Integer blackid){
        boolean b = blacklistService.updateStatusDJ(blackid);
        if (b){
            return Msg.success().add("va_msg","冻结成功") ;
        }else {
            return Msg.fail().add("va_msg","冻结失败");
        }
    }
}
