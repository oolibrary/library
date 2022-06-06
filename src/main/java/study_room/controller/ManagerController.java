package study_room.controller;

import study_room.entity.Manager;
import study_room.service.impl.ManagerServiceImpl;
import study_room.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;

/**
 * Class {@code ManagerController} 定义了关于管理员相关内容，包括登录，注册，修改密码，并定义了注册用户相关参数的要求
 * @author 杨宇涵
 */
@Controller
public class ManagerController {

    @Autowired
    ManagerServiceImpl managerService;
    /**
     * {@link #toForgotPage()}
     *
     * <p>进入忘记密码页面
     * <a href="http://localhost:8080/forgotPwd">Apache's Commons Lang</a>
     * @return "ForgotPassword"
     */
    @RequestMapping("/forgotPwd")
    public String toForgotPage(){
        return "ForgotPassword";
    }
    /**
     * {@link #toForgotPage2()}
     *
     * <p>进入忘记密码页面
     * <a href="http://localhost:8080/forgotPwd2">Apache's Commons Lang</a>
     * @return "ForgotPassword"
     */
    @RequestMapping("/forgotPwd2")
    public String toForgotPage2(){
        return "ForgotPassword2";
    }
    /**
     * {@link #toLoginPage(HttpSession)}
     *
     * <p>进入登录页面
     * <a href="http://localhost:8080/logout">Apache's Commons Lang</a>
     * @param session 服务器端对象
     * @return "Login"
     */
    @RequestMapping("/logout")
    public String toLoginPage(HttpSession session){
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            session.removeAttribute(attributeNames.nextElement());
        }
        return "Login";
    }
    /**
     * {@link #toLoginPage(HttpSession)}
     *
     * <p>进入重置密码页面
     * <a href="http://localhost:8080/register">Apache's Commons Lang</a>
     * @return "Register"
     */
    @RequestMapping("/register")
    public String toRegisterPage(){
        return "Register";
    }
    /**
     * {@link #checkLoginManagerName(String)}
     *
     * <p>登陆和忘记密码==》检查用户名是否存在
     * <a href="http://localhost:8080/checkLoginManagerName">Apache's Commons Lang</a>
     * @param managername1 管理员名称
     * @return Msg
     */
    @GetMapping("/checkLoginManagerName")
    @ResponseBody
    public Msg checkLoginManagerName(@RequestParam("managername") String managername1){
        String managername = managername1.trim();
        boolean b = managerService.checkManagerName(managername);
        if (b){
            return Msg.fail().add("va_msg","用户名不存在！");
        }else {
            return Msg.success().add("va_msg","");
        }
    }
    /**
     * {@link #checkManagerName(String)}
     *
     * <p>注册时检查用户名是否存在
     * <a href="http://localhost:8080/checkManagerName">Apache's Commons Lang</a>
     * @param managername1 管理员名称
     * @return Msg
     */
    @GetMapping("/checkManagerName")
    @ResponseBody
    public Msg checkManagerName(@RequestParam("managername") String managername1){
        String managername = managername1.trim();
        String regex = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]+$)";
        boolean c = managername.matches(regex);
        if(!c) {
            return Msg.fail().add("va_msg", "用户名可以是2-5位中文，或者6-16位英文和数字的组合");
        }
        boolean b = managerService.checkManagerName(managername);
        if (b){
            return Msg.success().add("va_msg","用户名可用！");
        }else {
            return Msg.fail().add("va_msg","用户名已存在！");
        }
    }
    /**
     * {@link #checkManagerTel(String)}
     *
     * <p>注册时检查手机号是否为11位，且1开头
     * <a href="http://localhost:8080/checkManagerTel">Apache's Commons Lang</a>
     * @param managertel1 管理员手机号
     * @return Msg
     */
    @GetMapping("/checkManagerTel")
    @ResponseBody
    public Msg checkManagerTel(@RequestParam("managertel") String managertel1){
        String managertel = managertel1.trim();
        String regex = "(^1\\d{10}$)";
        boolean matches = managertel.matches(regex);
        if(matches){
            return Msg.success().add("va_msg"," ");
        }else{
            return Msg.fail().add("va_msg","手机号必须以1开头，11位的数字！");
        }
    }
    /**
     * {@link #checkLoginManagerPwd(String)}
     *
     * <p>注册时检查登陆密码
     * <a href="http://localhost:8080/checkLoginManagerPwd">Apache's Commons Lang</a>
     * @param managerpwd1 注册时登陆密码
     * @return Msg
     */
    @GetMapping("/checkLoginManagerPwd")
    @ResponseBody
    public Msg checkLoginManagerPwd(@RequestParam("managerpwd") String managerpwd1){
        String managerpwd = managerpwd1.trim();
        String regex = "(^[a-zA-Z0-9_-]{6,16}$)";
        boolean matches = managerpwd.matches(regex);
        if(matches){
            return Msg.success().add("va_msg"," ");
        }else{
            return Msg.fail().add("va_msg","密码格式不正确：必须超过6位，小于16位！");
        }
    }
    /**
     * {@link #checkManagerPwd(String)}
     *
     * <p>注册时检查登陆密码
     * <a href="http://localhost:8080/checkLoginManagerPwd">Apache's Commons Lang</a>
     * @param managerpwd1 注册时登陆密码
     * @return Msg
     */
    @GetMapping("/checkManagerPwd")
    @ResponseBody
    public Msg checkManagerPwd(@RequestParam("managerpwd") String managerpwd1){
        String managerpwd = managerpwd1.trim();
        String regex = "(^[a-zA-Z0-9_-]{6,16}$)";
        boolean matches = managerpwd.matches(regex);
        if(matches){
            return Msg.success().add("va_msg"," ");
        }else{
            return Msg.fail().add("va_msg","密码格式不正确：必须超过6位，小于16位！");
        }
    }

    /**
     * {@link #registerManger(Manager, Map)}
     *
     * <p>新增管理员
     * <a href="http://localhost:8080/checkLoginManagerPwd">Apache's Commons Lang</a>
     * @param manager 新增管理员实体
     * @param map 返回信息
     * @return Msg
     */
    @PostMapping("/registerManager")
    public String registerManger(Manager manager,Map<String,Object> map){
        manager.setManagerid(null);
        boolean flag = managerService.createManager(manager);
        if(flag){
            map.put("msg","插入成功！");
            return "redirect:share.html";
        }else{
            map.put("msg","插入失败！");
            return "Register";
        }
    }
    /**
     * {@link #loginDashboard(String, String, Map, HttpSession)}
     *
     * <p>登陆时检查用户名以及密码
     * <a href="http://localhost:8080/loginD">Apache's Commons Lang</a>
     * @param managername1 管理员姓名
     * @param managerpwd1 管理员密码
     * @param session 服务器对象
     * @param map 返回信息
     * @return Msg
     */
    @PostMapping("/loginD")
    public String loginDashboard(@RequestParam("managername") String managername1,
                                 @RequestParam("managerpwd") String managerpwd1,
                                 Map<String,Object> map, HttpSession session){
        String managername = managername1.trim();
        String managerpwd = managerpwd1.trim();
        if(!managername.equals("") && !managerpwd.equals("")){
            boolean name = managerService.checkManagerName(managername);
            boolean pwd = managerService.checkManagerPwd(managerpwd);
            if(!name && !pwd){
                session.setAttribute("username",managername);
                return "redirect:/user22";
            }else{
                map.put("msg","账号密码错误，请重新输入账号和密码！");
                return "Login";
            }
        }else {
            map.put("msg","请输入账号or密码！");
            return "Login";
        }
    }
    /**
     * {@link #backLogin(String, String, Map)}
     *
     * <p>点击确认修改密码并且返回登陆页面
     * <a href="http://localhost:8080/resetpwd">Apache's Commons Lang</a>
     * @param managername1 管理员姓名
     * @param managerpwd1 管理员密码
     * @param map 返回信息
     * @return Msg
     */
    @PutMapping("/resetpwd")
    public String backLogin(@RequestParam("managername") String managername1,
                            @RequestParam("managerpwd") String managerpwd1,
                            Map<String,Object> map){
        String managername = managername1.trim();
        String managerpwd = managerpwd1.trim();
        if (!managername.equals("") && !managerpwd.equals("")){
            boolean tel = managerService.checkManagerName(managername);
            if(tel){
                map.put("msg","账号不存在，请重新输入！");
                return "ForgotPassword";
            }else {
                int i = managerService.updateUserPwd(managername,managerpwd);
                map.put("msg","reset success!");
                if(i>0){
                    return "redirect:/share.html";
                }else {
                    return "ForgotPassword";
                }
            }
        }else {
            map.put("msg","账号或新密码为空，请输入！");
            return "ForgotPassword";
        }
    }
    /**
     * {@link #backLogin2(String, String, Map)}
     *
     * <p>点击确认修改密码并且返回登陆页面
     * <a href="http://localhost:8080/resetpwd2">Apache's Commons Lang</a>
     * @param managername1 管理员姓名
     * @param managerpwd1 管理员密码
     * @param map 返回信息
     * @return Msg
     */
    @PutMapping("/resetpwd2")
    public String backLogin2(@RequestParam("managername") String managername1,
                            @RequestParam("managerpwd") String managerpwd1,
                            Map<String,Object> map){
        String managername = managername1.trim();
        String managerpwd = managerpwd1.trim();
        if (!managername.equals("") && !managerpwd.equals("")){
            boolean tel = managerService.checkManagerName(managername);
            if(tel){
                map.put("msg","账号不存在，请重新输入！");
                return "ForgotPassword2";
            }else {
                int i = managerService.updateUserPwd(managername,managerpwd);
                map.put("msg","reset success!");
                if(i>0){
                    return "redirect:/forgotPwd2";
                }else {
                    return "ForgotPassword2";
                }
            }
        }else {
            map.put("msg","账号或新密码为空，请输入！");
            return "ForgotPassword2";
        }
    }

}
