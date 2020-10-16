package cn.hctech2006.softcup.isomerase3.controller;

import cn.hctech2006.softcup.isomerase3.bean.SysMenu;
import cn.hctech2006.softcup.isomerase3.http.HttpResult;
import cn.hctech2006.softcup.isomerase3.page.PageRequest;
import cn.hctech2006.softcup.isomerase3.page.PageResult;
import cn.hctech2006.softcup.isomerase3.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "菜单信息接口")
@RestController
@RequestMapping("/menu")

public class MenuController  {
    @Autowired
    SysMenuService sysMenuService;
    @ApiOperation(value = "菜单注册",notes = "菜单注册，这个你注册也没什么用，菜单的授权项已经写死")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "name",value = "菜单名",required = true),
            @ApiImplicitParam(type = "query", name = "parentId",value = "父菜单ID，一级菜单为-1",required = true),
            @ApiImplicitParam(type = "query", name = "perms",value = "菜单授权",required = true),


    })
    @PostMapping("/register")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult upload(String name, Long parentId, String perms){
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            SysMenu sysMenu = new SysMenu();
            sysMenu.setPerms(perms);
            sysMenu.setName(name);
            sysMenu.setParentId(parentId);
            sysMenu.setCreateBy(authentication.getName());
            sysMenu.setCreateTime(new Date());
            sysMenu.setLastUpdateTime(new Date());
            sysMenu.setLastUpdateBy(authentication.getName());
            sysMenu.setLastUpdateBy(authentication.getName());
            sysMenu.setDelFlag((byte)0);
            sysMenuService.save(sysMenu);
            return HttpResult.ok(sysMenu);
        }catch (DuplicateKeyException e){
            return HttpResult.error("菜单注册重复");
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error("菜单注册失败");
        }
    }
    @ApiOperation(value = "批量菜单修改",notes = "批量菜单修改\n" +
            "这是基本实例，如果字段没有修改与原来保持一致" +
            "[\n" +
            "  {\n" +
            "    \"delFlag\": 0,\n" +
            "    \"id\": 0,\n" +
            "    \"name\": \"string\",\n" +
            "    \"parentId\": 0,\n" +
            "    \"perms\": \"string\"\n" +
            "  },\n" +
            "{\n" +
            "    \"delFlag\": 0,\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"ing\",\n" +
            "    \"parentId\": 0,\n" +
            "    \"perms\": \"string\"\n" +
            "  }\n" +
            "]"
            )
    @ApiImplicitParams({
    })
    @PutMapping("/update/list")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult update(@RequestBody List<SysMenu> sysMenus) throws IOException {
        try{
            for (SysMenu sysMenu: sysMenus){
                sysMenu.setLastUpdateTime(new Date());
                sysMenuService.update(sysMenu);
            }
            return HttpResult.ok(sysMenus);
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error("菜单修改失败");
        }
    }
    @ApiOperation(value = "分页菜单查询",notes = "分页菜单查询\n" +
            "可查询参数：@ApiImplicitParams({\n" +
            "            @ApiImplicitParam(type = \"query\", name = \"id\", value = \"菜单编号\",required = true),\n" +
            "            @ApiImplicitParam(type = \"query\", name = \"name\",value = \"菜单名\"),\n" +
            "            @ApiImplicitParam(type = \"query\", name = \"parentId\",value = \"父菜单ID，一级菜单为0\"),\n" +
            "            @ApiImplicitParam(type = \"query\", name = \"perms\",value = \"授权\"),\n" +
            "            @ApiImplicitParam(type = \"query\", name = \"delFlag\",value = \"删除标志，-1删除，0正常\")\n[\n"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页码",required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数",required = true),
            @ApiImplicitParam(type = "query", name = "id", value = "菜单编号"),
            @ApiImplicitParam(type = "query", name = "name", value = "菜单名"),
            @ApiImplicitParam(type = "query", name = "parentId", value = "父菜单ID ，－１为顶级菜单的父ID"),
            @ApiImplicitParam(type = "query", name = "perms", value = "授权"),
            @ApiImplicitParam(type = "query", name = "delFlag", value = "删除标志,-1删除０正常"),
    })
    @PostMapping("/find/page")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult find(int pageNum, int pageSize, Long id , String name, Long parentId, String perms, Byte delFlag){
        try{

            SysMenu sysMenu = new SysMenu();
            sysMenu.setName(name);
            sysMenu.setParentId(parentId);
            sysMenu.setDelFlag(delFlag);
            sysMenu.setPerms(perms);
            sysMenu.setId(id);
            Map<String, Object> map = new HashMap<>();
            map.put("sysMenu",sysMenu);
            PageRequest pageRequest = new PageRequest(pageNum, pageSize, map);
            PageResult pageResult = sysMenuService.findPage(pageRequest);
            return HttpResult.ok(pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error("菜单查询失败");
        }
    }

//    @Override
//    public cn.hcnet2006.core.http.HttpResult find(int pageNum, int pageSize, cn.hcnet2006.admin.bean.SysMenu sysMenu) {
//        return super.find(pageNum, pageSize, sysMenu);
//    }
}
