package study_room.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import study_room.entity.User;
import study_room.service.impl.UserServiceImpl;
import study_room.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class {@code UserController} 定义了关于用户相关的操作，包括查找用户，删除用户，新增用户，更新用户，检查相关格式等
 * @author 杨宇涵
 */
@Controller
public class UserController {

    @Autowired
    UserServiceImpl userService;
    /**
     * {@link #queryUserlikeId(Integer, String)}
     *
     * <p>通过用户姓名进行模糊查询
     * <a href="http://localhost:8080/queryUserlike">Apache's Commons Lang</a>
     * @param pn 页号
     * @param username 用户名
     * @return Msg
     */
    @GetMapping("/queryUserlike/{username}")
    @ResponseBody
    public Msg queryUserlikeId(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
                               @PathVariable("username") String username){
        PageHelper.startPage(pn,20);
        List<User> users = userService.queryByUserName(username);
        PageInfo<User> page= new PageInfo<>(users, 5);
        return Msg.success().add("pageInfo",page);
    }
    /**
     * {@link #userTables(Integer)}
     *
     * <p>前端发送请求后端分页数据
     * <a href="http://localhost:8080/userTables">Apache's Commons Lang</a>
     * @param pn 页号
     * @return Msg
     */
    @GetMapping("/userTables")
    @ResponseBody
    public Msg userTables(@RequestParam(value = "pn",defaultValue = "1") Integer pn){
        PageHelper.startPage(pn,7);
        List<User> users = userService.getAll();
        PageInfo<User> page= new PageInfo<>(users, 5);
        return Msg.success().add("pageInfo",page);
    }
    /**
     * {@link #toUserTables(Map)}
     *
     * <p>跳转到tables页面
     * <a href="http://localhost:8080/userTables">Apache's Commons Lang</a>
     * @return Msg
     */
    @RequestMapping("/usertables")
    public String toUserTables(Map<String,Object> map){
        List<User> users = userService.getAll();
        map.put("users",users);
        return "Tables";
    }
    /**
     * {@link #queryUserID(Integer, Map)}
     *
     * <p>通过userid获得用户信息
     * <a href="http://localhost:8080/queryUserByID">Apache's Commons Lang</a>
     * @return Msg（users）
     */
    @GetMapping("/queryUserByID/{userid}")
    @ResponseBody
    public Msg queryUserID(@PathVariable("userid") Integer userid,Map<String,Object> map){
        User users = userService.getUserByID(userid);
        return Msg.success().add("users",users);
    }
    /**
     * {@link #delEmp(String)}
     *
     * <p>根据 ids删除对应用户
     * <a href="http://localhost:8080/user">Apache's Commons Lang</a>
     * @param ids 删除用户们的id
     * @return Msg
     */
    @DeleteMapping(value = "/user/{ids}")
    @ResponseBody
    public Msg delEmp(@PathVariable("ids") String ids) {
        if(ids.contains("-")) {
            List<Integer> delids = new ArrayList<>();
            String[] split = ids.split("-");
            for (String string : split) {
                delids.add(Integer.parseInt(string));
            }
            userService.batchUser(delids);
        }else {
            int id = Integer.parseInt(ids);
            userService.deleteUser(id);
        }
        return Msg.success();
    }
    /**
     * {@link #saveEmp(User)}
     *
     * <p>更新用户
     * <a href="http://localhost:8080/user">Apache's Commons Lang</a>
     * @param user 更新用户实体
     * @return Msg
     */
    @PutMapping(value = "/user/{userID}")
    @ResponseBody
    public Msg saveEmp(User user) {
        System.out.println(user);
        userService.updateUser(user);
        return Msg.success();
    }
    /**
     * {@link #getEmp(Integer)}
     *
     * <p>根据用户信息返回用户实体
     * <a href="http://localhost:8080/user">Apache's Commons Lang</a>
     * @param userID 用户id
     * @return 用户实体
     */
    @GetMapping(value = "user/{id}")
    @ResponseBody
    public Msg getEmp(@PathVariable(value = "id")Integer userID) {
        User user = userService.getUserByID(userID);
        return Msg.success().add("user", user);
    }
    /**
     * {@link #checkAddUser(String)}
     *
     * <p>检查用户名是否可用，先判断用户名是否合法的表达式，再判断数据库用户名重复校验
     * <a href="http://localhost:8080/checkAddUser">Apache's Commons Lang</a>
     * @param userName 待检验的用户名
     * @return msg
     */
    @RequestMapping(value = "/checkAddUser")
    @ResponseBody
    public Msg checkAddUser(@RequestParam("username")String userName) {
        String regex = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]+$)";
        boolean c = userName.matches(regex);
        if(!c) {
            return Msg.fail().add("va_msg", "用户名可以是2-5位中文，或者6-16位英文和数字的组合");
        }
        boolean b = userService.checkUserName(userName);
        if(b) {
            return Msg.success().add("va_msg", "用户名可用");
        }else {
            return Msg.fail().add("va_msg",	"用户名已存在");
        }
    }
    /**
     * {@link #checkAddTel(String)}
     *
     * <p>检查手机号码格式
     * <a href="http://localhost:8080/checkAddTel">Apache's Commons Lang</a>
     * @param userTel1 待检验的手机号
     * @return msg
     */
    @RequestMapping("/checkAddTel")
    @ResponseBody
    public Msg checkAddTel(@RequestParam(value = "usertel")String userTel1) {
        String userTel = userTel1.trim();
        String regex = "(^1\\d{10}$)";
        boolean matches = userTel.matches(regex);
        if(matches){
            return Msg.success().add("va_msg","");
        }else{
            return Msg.fail().add("va_msg","手机号必须以1开头，11位的数字！");
        }
    }
    /**
     * {@link #checkAddAge(Integer)}
     *
     * <p>检查年龄格式
     * <a href="http://localhost:8080/checkAddAge">Apache's Commons Lang</a>
     * @param userAge1 待检验的年龄
     * @return msg
     */
    @RequestMapping("/checkAddAge")
    @ResponseBody
    public Msg checkAddAge(@RequestParam(value = "userage")Integer userAge1) {
        String s = userAge1.toString();
        String userTel = s.trim();
        String regex = "(^\\d{1,3}$)";
        boolean matches = userTel.matches(regex);
        if(matches){
            return Msg.success().add("va_msg","");
        }else{
            return Msg.fail().add("va_msg","请输入数字");
        }
    }
    /**
     * {@link #checkAddPwd(String)}
     *
     * <p>检查密码格式
     * <a href="http://localhost:8080/checkAddPwd">Apache's Commons Lang</a>
     * @param userPwd1 待检验的密码
     * @return msg
     */
    @RequestMapping("/checkAddPwd")
    @ResponseBody
    public Msg checkAddPwd(@RequestParam(value = "userpwd")String userPwd1) {
        String userPwd = userPwd1.trim();
        String regex = "(^[a-zA-Z0-9_-]{6,16}$)";
        boolean matches = userPwd.matches(regex);
        if(matches){
            return Msg.success().add("va_msg","");
        }else{
            return Msg.fail().add("va_msg","密码格式不正确：必须超过6位，小于16位的英文和数字！");
        }
    }
    /**
     * {@link #insertUser(User)}
     *
     * <p>新增用户
     * <a href="http://localhost:8080/insertUser">Apache's Commons Lang</a>
     * @param user 新增用户实体
     * @return msg
     */
    @PostMapping("/insertUser")
    @ResponseBody
    public Msg insertUser(User user){
        user.setUserid(null);
        userService.insertUser(user);
        return Msg.success().add("va_msg","插入成功！");
    }
    /**
     * {@link #updateUser(User)}
     *
     * <p>更新用户数据
     * <a href="http://localhost:8080/updateUser">Apache's Commons Lang</a>
     * @param user 更新用户实体
     * @return msg
     */
    @PutMapping("/updateUser")
    @ResponseBody
    public Msg updateUser(User user){
        userService.updateUser(user);
        return Msg.success().add("va_msg","插入成功！");
    }
    /**
     * {@link #updateUser(User)}
     *
     * <p>删除用户
     * <a href="http://localhost:8080/deleteUser">Apache's Commons Lang</a>
     * @param userid 待删除的用户id
     * @return msg
     */
    @DeleteMapping("/deleteUser/{userid}")
    @ResponseBody
    public Msg deleteEmployee(@PathVariable("userid") Integer userid){
        boolean b = userService.deleteUser(userid);
        if (b){
            return Msg.success().add("va_msg","用户删除成功!");
        }else {
            return Msg.fail().add("va_msg","用户删除失败！");
        }
    }
    /**
     * {@link #updateUser(User)}
     *
     * <p> 前端ajax请求，查询userid是否在表中存在
     * <a href="http://localhost:8080/checkUserID">Apache's Commons Lang</a>
     * @param userid 用户id
     * @return msg
     */
    @GetMapping("/checkUserID")
    @ResponseBody
    public Msg checkUserID(@RequestParam("userid") Integer userid){
        boolean b = userService.checkUserID(userid);
        if(b){
            return Msg.success().add("va_msg","用户名存在!");
        }else {
            return Msg.fail().add("va_msg","用户名不存在，重新输入！");
        }
    }
}
