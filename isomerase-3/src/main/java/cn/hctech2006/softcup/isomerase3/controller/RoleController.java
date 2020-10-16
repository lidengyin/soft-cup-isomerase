package cn.hctech2006.softcup.isomerase3.controller;

import cn.hctech2006.softcup.isomerase3.bean.SysRole;
import cn.hctech2006.softcup.isomerase3.bean.SysRoleMenu;
import cn.hctech2006.softcup.isomerase3.http.HttpResult;
import cn.hctech2006.softcup.isomerase3.page.PageRequest;
import cn.hctech2006.softcup.isomerase3.page.PageResult;
import cn.hctech2006.softcup.isomerase3.service.SysMenuService;
import cn.hctech2006.softcup.isomerase3.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
    private SysRoleService sysRoleService;
    @ApiOperation(value = "角色注册",notes = "角色注册")
    @ApiImplicitParams({

            @ApiImplicitParam(type = "query",name = "name",value = "角色名",required = true),
            @ApiImplicitParam(type = "query", name = "remark",value = "角色备注",required = true),
    })
    @PostMapping("/register")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult upload(String name, String remark, @RequestBody List<Long> menus){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SysRole sysRole = new SysRole();
            sysRole.setName(name);
            sysRole.setRemark(remark);
            sysRole.setCreateTime(new Date());
            sysRole.setLastUpdateTime(new Date());
            sysRole.setLastUpdateBy(authentication.getName());
            sysRole.setLastUpdateBy(authentication.getName());
            sysRole.setDelFlag((byte)0);
            sysRoleService.save(sysRole);
            //初始化设置关联权限
           for(long sysMenu: menus){
               SysRoleMenu sysRoleMenu = new SysRoleMenu();
               sysRoleMenu.setId(UUID.randomUUID().toString());
               sysRoleMenu.setCreateBy(sysRole.getCreateBy());
               sysRoleMenu.setRoleId(sysRole.getId());
               sysRoleMenu.setMenuId(sysMenu);
               sysRoleMenu.setCreateTime(new Date());
               sysRoleMenu.setLastUpdateTime(new Date());
               sysRoleMenu.setLastUpdateBy(sysRole.getCreateBy());
               sysRoleMenu.setDelFlag((byte)0);
               sysRoleService.saveRoleAndMenu(sysRoleMenu);
           }
            return HttpResult.ok(sysRole);
        }catch (DuplicateKeyException e){
            return HttpResult.error("角色注册重复");
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error("角色注册失败");
        }
    }
    @ApiOperation(value = "角色列表修改",notes = "角色列表修改，修改角色关联属性，那么这个角色只能单一修改,切记:\n" +
            " @ApiImplicitParam(type = \"query\", name = \"id\", value = \"编号\",required = true),\n" +
            "            @ApiImplicitParam(type = \"query\",name = \"name\",value = \"角色名\"),\n" +
            "            @ApiImplicitParam(type = \"query\", name = \"remark\",value = \"角色备注\"),\n" +
            "            @ApiImplicitParam(type = \"query\", name = \"lastUpdateBy\",value = \"修改者\"),\n" +
            "            @ApiImplicitParam(type = \"query\", name = \"delFlag\",value = \"删除标志，-1删除，0正常\")" +
            "实例" +
            "[\n" +
            "  {\n" +
            "    \"delFlag\": 0,\n" +
            "    \"id\": 0,\n" +
            "    \"name\": \"string\",\n" +
            "    \"remark\": \"string\"\n" +
            "  }，\n" +
            "  {\n" +
            "    \"delFlag\": 0,\n" +
            "    \"id\": １,\n" +
            "    \"name\": \"stng\",\n" +
            "    \"remark\": \"string\"\n" +
            "  }\n" +
            "]")
    @ApiImplicitParams({
//            @ApiImplicitParam(type = "query",name = "menus", value = "菜单编号列表",paramType = "body",dataType = "java.util.List")
    })

    @PutMapping("/update/list")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult update(@RequestBody List<SysRole> sysRoles, @RequestParam(value = "菜单编号",required = false) List<Long> menus){
        try{



            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("auth:"+authentication.getName());
            for(SysRole sysRole: sysRoles){
                if(menus != null){
                    int result = sysRoleService.updateRoleAndMenuDelFlag(sysRole.getId());
                    for(Long sysMenu: menus){

                        SysRoleMenu sysRoleMenu = new SysRoleMenu();
                        sysRoleMenu.setId(UUID.randomUUID().toString());
                        sysRoleMenu.setCreateBy(authentication.getName());
                        sysRoleMenu.setRoleId(sysRole.getId());
                        sysRoleMenu.setMenuId(sysMenu);
                        sysRoleMenu.setCreateTime(new Date());
                        sysRoleMenu.setLastUpdateTime(new Date());
                        sysRoleMenu.setLastUpdateBy(authentication.getName());
                        sysRoleMenu.setDelFlag((byte)0);
                        sysRoleService.saveRoleAndMenu(sysRoleMenu);
                    }
                }else{
                    sysRole.setLastUpdateBy(authentication.getName());
                    sysRole.setLastUpdateTime(new Date());
                    sysRoleService.update(sysRole);
                }
            }
            return HttpResult.ok(sysRoles);
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error("角色修改失败");
        }
    }

    @ApiOperation(value = "分页查询角色",notes = "分页查询角色\n" +
            "@ApiImplicitParam(type = \"query\", name = \"id\",value = \"编号\",required = true)\n" +
            "            @ApiImplicitParam(type = \"query\",name = \"name\",value = \"角色名\",required = true),\n"+
            "            @ApiImplicitParam(type = \"query\", name = \"delFlag\",value = \"删除标志，-1删除，0正常\")\n"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页码",required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数",required = true),
            @ApiImplicitParam(type = "query",name = "name",value = "角色名"),
            @ApiImplicitParam(type = "query", name = "delFlag",value = "删除标志，－１时删除，０时正常"),
    })
    @PostMapping("/find/page")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult find(int pageNum, int pageSize, String name, Byte delFlag){
        try{
            SysRole sysRole = new SysRole();
            sysRole.setName(name);
            sysRole.setDelFlag(delFlag);
            Map<String, Object> map = new HashMap<>();
            map.put("sysRole", sysRole);
            PageRequest pageRequest  = new PageRequest(pageNum, pageSize, map);
            PageResult pageResult = sysRoleService.findPage(pageRequest);
            return HttpResult.ok(pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error("分页查询失败");
        }
    }
    @ApiOperation(value = "具体查询某个角色",notes = "具体查询某个角色")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "id", value = "编号", required = true)
    })
    @PostMapping("/find/id")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult findById(Long id){
        try{
            SysRole sysRole = sysRoleService.findById(id);
            return HttpResult.ok(sysRole);

        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error("查询菜单失败");
        }
    }
}
