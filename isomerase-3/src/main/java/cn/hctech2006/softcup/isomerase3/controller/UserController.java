package cn.hctech2006.softcup.isomerase3.controller;

import cn.hctech2006.softcup.isomerase3.bean.SysUser;
import cn.hctech2006.softcup.isomerase3.bean.SysUserRole;
import cn.hctech2006.softcup.isomerase3.http.HttpResult;
import cn.hctech2006.softcup.isomerase3.jwt.UserLoginDTO;
import cn.hctech2006.softcup.isomerase3.page.PageRequest;
import cn.hctech2006.softcup.isomerase3.page.PageResult;
import cn.hctech2006.softcup.isomerase3.service.SysUserService;
import cn.hctech2006.softcup.isomerase3.util.OSSUtils;
import cn.hctech2006.softcup.isomerase3.util.PassWordEncoderUtils;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(tags = "用户信息接口")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private SysUserService sysUserService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //private String url = "/usr/local/spring";
    @ApiOperation(value = "用户登录",notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "name", value = "用户名",required = true),
            @ApiImplicitParam(type = "query", name = "password", value = "密码",required = true)
    })
    @PostMapping("/login")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult login(String name, String password) throws LoginException {
        //获取用户信息
        UserLoginDTO result  =sysUserService.login(name,password);
        return HttpResult.ok(result);
    }

    @ApiOperation(value = "用户注册",notes = "用户注册")

    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "name",value = "用户名",required = true),
            @ApiImplicitParam(type = "query", name = "password",value = "密码",required = true),
            @ApiImplicitParam(type = "query", name = "deptId",value = "所属方向ID",required = true),
            @ApiImplicitParam(type = "query", name = "grade",value = "年级，比如2018",required = true),
            @ApiImplicitParam(type = "query", name = "email",value = "邮箱，确保格式正确",required = true),
            @ApiImplicitParam(type = "query", name = "mobile",value = "手机，确保格式正确",required = true),
    })
    @PostMapping("/register")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult register(String name, String password, Long deptId, String grade, String email, String mobile, @RequestParam List<Long> roleList, MultipartFile uploadFile  ) throws FileNotFoundException {
        //MultipartFile uploadFile = null;
        SysUser sysUser = new SysUser();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //新建暂时缓存目录,该目录一定存在
        String url = ResourceUtils.getURL("").getPath()+uploadFile.getOriginalFilename();
        System.out.println(url);
        File folder = new File(url);
        try{
            //转义文件到服务器
            uploadFile.transferTo(folder);
            //从服务器获取文件传递到阿里云OSS.返回下载链接地址
            String avator_url = OSSUtils.upload(folder,sysUser.getName()+".jpg");
            //删除服务器缓存文件
            folder.delete();
            //设置属性
            sysUser.setName(name);
            sysUser.setPassword(password);
            sysUser.setDeptId(deptId);
            sysUser.setEmail(email);
            sysUser.setMobile(mobile);
            sysUser.setGrade(grade);
            sysUser.setCreateBy(authentication.getName());
            //设置用户头像
            sysUser.setAvator(avator_url);
            //设置创建时间
            sysUser.setCreateTime(new Date());
            //设置更新时间
            sysUser.setLastUpdateTime(new Date());
            //设置创建者
            sysUser.setLastUpdateBy(sysUser.getCreateBy());
            //删除标志
            sysUser.setDelFlag((byte)0);
            //密码加密
            sysUser.setPassword(PassWordEncoderUtils.BCryptPassword(sysUser.getPassword()));
            //保存
            System.out.println("time:"+sdf.format(new Date()));
            sysUserService.save(sysUser);
            for(Long role: roleList){
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(sysUser.getId());
                sysUserRole.setRoleId(role);
                sysUserRole.setId(UUID.randomUUID().toString());
                sysUserRole.setCreateBy(authentication.getName());
                sysUserRole.setLastUpdateBy(authentication.getName());
                sysUserRole.setCreateTime(new Date());
                sysUserRole.setLastUpdateTime(new Date());
                sysUserRole.setDelFlag((byte)0);
                sysUserService.saveUserAndRole(sysUserRole);
            }
            return HttpResult.ok(sysUser);
        }catch (DuplicateKeyException e){
            return HttpResult.error("重复注册");
        }catch (IOException e){
            e.printStackTrace();
            return HttpResult.error("注册失败");
        }

    }
    @ApiOperation(value = "用户修改",notes = "用户修改,这里必须说的是，roleList用户角色ID列表的修改，每次都是删除以前的，然后加上新增的")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name="id",value = "用户编号",required = true),
            @ApiImplicitParam(type = "query", name = "name",value = "用户名"),
            @ApiImplicitParam(type = "query", name = "password",value = "密码"),
            @ApiImplicitParam(type = "query", name = "deptId",value = "所属方向ID"),
            @ApiImplicitParam(type = "query", name = "grade",value = "年级，比如2018"),
            @ApiImplicitParam(type = "query", name = "email",value = "邮箱，确保格式正确"),
            @ApiImplicitParam(type = "query", name = "mobile",value = "手机，确保格式正确"),
            @ApiImplicitParam(type = "query", name = "delFlag",value = "删除标志，-1删除，0正常")
    })
    @PutMapping("/update")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult update(Long id, String name, String password, Long deptId, String grade, String email, String mobile,
                             @RequestParam(required = false) List<Long> roleList,  MultipartFile uploadFile) throws IOException,NullPointerException {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SysUser sysUser = new SysUser();
            //设置属性
            sysUser.setId(id);
            sysUser.setName(name);
            sysUser.setPassword(password);
            sysUser.setDeptId(deptId);
            sysUser.setEmail(email);
            sysUser.setMobile(mobile);
            sysUser.setGrade(grade);
            sysUser.setCreateBy(authentication.getName());
            if (uploadFile !=null){
                String url = ResourceUtils.getURL("").getPath()+uploadFile.getOriginalFilename();
                File folder = new File(url);
                uploadFile.transferTo(folder);
                String avator_url = OSSUtils.upload(folder, UUID.randomUUID().toString() +".jpg");
                folder.delete();
                sysUser.setAvator(avator_url);
            }
            sysUser.setLastUpdateTime(new Date());
            sysUserService.update(sysUser);
            sysUserService.deleteUserAndRole(sysUser.getId());
            System.out.println(sysUser.getId());
            if(roleList != null || roleList.size() > 0){
                System.out.println("需要删除的用户ID："+sysUser.getId());
                sysUserService.deleteUserAndRole(sysUser.getId());
                for(Long role: roleList){
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(sysUser.getId());
                    sysUserRole.setRoleId(role);
                    sysUserRole.setId(UUID.randomUUID().toString());
                    sysUserRole.setCreateBy(authentication.getName());
                    sysUserRole.setLastUpdateBy(authentication.getName());
                    sysUserRole.setCreateTime(new Date());
                    sysUserRole.setLastUpdateTime(new Date());
                    sysUserRole.setDelFlag((byte)0);
                    sysUserService.saveUserAndRole(sysUserRole);
                }
            }
            return HttpResult.ok(sysUser);
        }catch (Exception e) {
            e.printStackTrace();
            return HttpResult.error("用户修改失败");
        }
    }
    @ApiOperation(value = "分页查看用户列表",notes = "分页查看用户列表:可选参数列表，以and的形式，随机组合，不加参数就是全选")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页码",required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数",required = true),
            @ApiImplicitParam(type = "query", name="id",value = "用户编号"),
            @ApiImplicitParam(type = "query", name = "name",value = "用户名"),
            @ApiImplicitParam(type = "query", name = "deptId",value = "所属方向ID"),
            @ApiImplicitParam(type = "query", name = "grade",value = "年级，比如2018"),
            @ApiImplicitParam(type = "query", name = "delFlag",value = "删除标志，-1删除，0正常")
    })
   // @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/find/page")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult find(int pageNum, int pageSize, Long id, String name, String grade, Long deptId, Byte delFlag){
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setGrade(grade);
        sysUser.setName(name);
        sysUser.setDeptId(deptId);
        sysUser.setDelFlag(delFlag);
        Map<String, Object> map = new HashMap<>();
        map.put("sysUser",sysUser);
        PageRequest pageRequest = new PageRequest(pageNum, pageSize, map);
        PageResult pageResult = sysUserService.findPage(pageRequest);
        return HttpResult.ok(JSON.toJSON(pageResult));
    }
    @ApiOperation(value = "具体查看某一个用户信息",notes = "具体查看某一个用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "id", value = "用户编号", required = true)
    })
    @PostMapping("/find/id")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult findById(Long id){
        try{
            SysUser sysUser = sysUserService.findById(id);
            return HttpResult.ok(sysUser);
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error("查询用户失败");
        }
    }
}
