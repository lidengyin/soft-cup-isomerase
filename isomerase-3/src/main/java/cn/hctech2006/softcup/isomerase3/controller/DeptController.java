package cn.hctech2006.softcup.isomerase3.controller;


import cn.hctech2006.softcup.isomerase3.bean.SysDept;
import cn.hctech2006.softcup.isomerase3.http.HttpResult;
import cn.hctech2006.softcup.isomerase3.page.PageRequest;
import cn.hctech2006.softcup.isomerase3.page.PageResult;
import cn.hctech2006.softcup.isomerase3.service.SysDeptService;
import cn.hctech2006.softcup.isomerase3.util.OSSUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Api(tags = "机构信息接口")
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private SysDeptService sysDeptService;
    @ApiOperation(value = "上传机构信息",notes = "上传机构信息")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "name",value = "机构名",required = true),
            @ApiImplicitParam(type = "query",name = "parentId",value = "上级机构ID，一级机构父ID为-1",required = true),
    })
    @PostMapping("/register")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult upload(String name, Long parentId, @ApiParam MultipartFile uploadFile) throws FileNotFoundException {
        String url = ResourceUtils.getURL("").getPath()+uploadFile.getOriginalFilename();
        File folder = new File(url);
        try{
            SysDept sysDept = new SysDept();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            uploadFile.transferTo(folder);
            String logo_url = OSSUtils.upload(folder, UUID.randomUUID().toString()+".jpg");
            sysDept.setParentId(parentId);
            sysDept.setName(name);
            sysDept.setCreateBy(authentication.getName());
            sysDept.setDeptLogo(logo_url);
            sysDept.setCreateTime(new Date());
            sysDept.setLastUpdateTime(new Date());
            sysDept.setLastUpdateBy(authentication.getName());
            sysDept.setDelFlag((byte)0);
            sysDeptService.save(sysDept);
            return HttpResult.ok(sysDept);
        }catch (DuplicateKeyException | IOException e){
            return HttpResult.error("该机构注册");
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error("机构注册失败，请重新注册");
        }
    }
    @ApiOperation(value = "修改机构信息",notes = "修改机构信息")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "id",value = "机构编号",required = true),
            @ApiImplicitParam(type = "query",name = "name",value = "机构名"),
            @ApiImplicitParam(type = "query",name = "parentId",value = "上级机构ID，一级机构父ID为-1"),
            @ApiImplicitParam(type = "query", name = "delFlag",value = "删除标志，-1删除，0正常")
    })
    @PutMapping("/update")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult update(String name, Long id, Long parentId, Byte delFlag , @ApiParam MultipartFile uploadFile) throws FileNotFoundException {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SysDept sysDept = new SysDept();
            sysDept.setName(name);
            sysDept.setId(id);
            sysDept.setParentId(parentId);
            sysDept.setDelFlag(delFlag);
            if(uploadFile != null){
                String url = ResourceUtils.getURL("").getPath()+uploadFile.getOriginalFilename();
                File folder = new File(url);
                String deptLogo = OSSUtils.upload(folder, UUID.randomUUID().toString()+".jpg");
                folder.delete();
                System.out.println("deptLogo:"+deptLogo);
                sysDept.setDeptLogo(deptLogo);
            }
            sysDept.setLastUpdateTime(new Date());
            sysDept.setLastUpdateBy(authentication.getName());
            sysDeptService.update(sysDept);
            return HttpResult.ok(sysDept);
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error("机构修改失败");
        }
    }
    @ApiOperation(value = "分页查询机构信息",notes = "分页显示机构信息\n" +
            "可选参数进行and组合，全部为空则为查询全部\n" +
            "@ApiImplicitParam(type = \"query\",name = \"id\",value = \"机构编号\",required = true),\n" +
            "            @ApiImplicitParam(type = \"query\",name = \"name\",value = \"机构名\"),\n" +
            "            @ApiImplicitParam(type = \"query\",name = \"parentId\",value = \"上级机构ID，一级机构为0\"),\n" +
            "            @ApiImplicitParam(type = \"query\", name = \"delFlag\",value = \"删除标志，-1删除，0正常\")")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页码",required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数",required = true),
            @ApiImplicitParam(paramType = "query",name = "id", value = "机构编号"),
            @ApiImplicitParam(paramType = "query",name = "name", value = "机构名称", required = false),
            @ApiImplicitParam(paramType = "query",name = "parentId", value = "父机构ID，顶级机构父ID为－１", required = false),
            @ApiImplicitParam(paramType = "query",name = "delFlag", value = "删除标志，－１删除状态，０正常", required = false),

    })
    @PostMapping("/find/page")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult find(int pageNum, int pageSize, Long id,String name,Long parentId,  Byte delFlag){
        try{
            SysDept sysDept = new SysDept();
            sysDept.setId(id);
            sysDept.setName(name);
            sysDept.setParentId(parentId);
            sysDept.setDelFlag(delFlag);
            Map<String, Object> map = new HashMap<>();
            map.put("sysDept",sysDept);
            PageRequest pageRequest = new PageRequest(pageNum, pageSize, map);
            PageResult pageResult = sysDeptService.findPage(pageRequest);
            return HttpResult.ok(pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error("查询失败");
        }
    }
    @ApiOperation(value = "具体查看某个机构信息")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "id", value = "机构编号",required = true)
    })
    @PostMapping("/find/id")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult findById(Long id){
        try{
            SysDept sysDept = sysDeptService.findById(id);
            return HttpResult.ok(sysDept);
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error("查询机构失败");
        }

    }
    @ApiOperation(value = "查询机构树",notes = "查询机构树")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "parentId", value = "规定根目录的父ID为-1，",required = true)

    })
    @PostMapping("/find/tree")
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public HttpResult findDeptNodes(Long parentId){
        try{
            System.out.println("parentId:"+parentId);
            List<SysDept> sysDepts = sysDeptService.findByParentId(parentId);
            //SysDept sysDept = sysDeptService.findById(id);
            List<SysDept> rootList = sysDeptService.findRootTree();
            for(SysDept sysDept: sysDepts){
                List<SysDept> depts = getChildNodes(sysDept.getParentId(), rootList);
                sysDept.setDeptList(depts);
            }
            return HttpResult.ok(sysDepts);
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error("查询失败");
        }
    }

    private List<SysDept> getChildNodes(Long id, List<SysDept> rootList){
        //新建字节点列表
        List<SysDept> chileList = new ArrayList<>();
        //根据父节点ID填充对应的字节点
        for(SysDept sysDept : rootList){
            if(sysDept.getParentId() != null){
                if(sysDept.getParentId() == id){
                    chileList.add(sysDept);
                }
            }
        }
        if(chileList.size() == 0){
            return null;
        }
        //便利子节点并且进行对应的填充
        for(SysDept sysDept: chileList){
            sysDept.setDeptList(getChildNodes(sysDept.getId(), rootList));
        }
        return chileList;
    }
}
