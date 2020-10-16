package cn.hctech2006.softcup.isouserservice.controller;//package cn.hctech2006.softcup.isomerase.controller;

import cn.hctech2006.softcup.isouserservice.bean.SysRole;
import cn.hctech2006.softcup.isouserservice.bean.SysRoleMenu;
import cn.hctech2006.softcup.isouserservice.common.ServerResponse;
import cn.hctech2006.softcup.isouserservice.http.HttpResult;
import cn.hctech2006.softcup.isouserservice.page.PageRequest;
import cn.hctech2006.softcup.isouserservice.page.PageResult;
import cn.hctech2006.softcup.isouserservice.service.impl.SysRoleServiceImpl;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Api(tags = "角色信息接口")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private SysRoleServiceImpl sysRoleService;

    @ApiOperation(value = "查询角色")
    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页码",required = true),
//            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数",required = true),
//            @ApiImplicitParam(type = "query", name = "delFlag",value = "删除标志，－１时删除，０时正常"),
    })
    @PostMapping("/find/page")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public ServerResponse find(){
        try{

            List<SysRole> roles = sysRoleService.findPage();
            return ServerResponse.createBySuccess(roles);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByError("分页查询失败");
        }
    }
}
