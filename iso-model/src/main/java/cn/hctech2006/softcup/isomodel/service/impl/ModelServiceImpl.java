package cn.hctech2006.softcup.isomodel.service.impl;

import cn.hctech2006.softcup.isomodel.bean.NlModel;
import cn.hctech2006.softcup.isomodel.bean.NlModelSmall;
import cn.hctech2006.softcup.isomodel.common.ServerResponse;
import cn.hctech2006.softcup.isomodel.dto.ModelDTO;
import cn.hctech2006.softcup.isomodel.dto.ModelPutDTO;
import cn.hctech2006.softcup.isomodel.dto.ModelSmallDTO;
import cn.hctech2006.softcup.isomodel.dto.ModelSmallPutDTO;
import cn.hctech2006.softcup.isomodel.mapper.NlModelMapper;
import cn.hctech2006.softcup.isomodel.mapper.NlModelSmallMapper;
import cn.hctech2006.softcup.isomodel.vo.ModelDetailsVO;
import cn.hctech2006.softcup.isomodel.vo.ModelListVO;
import cn.hctech2006.softcup.isomodel.vo.ModelPageInfoVO;
import cn.hctech2006.softcup.isomodel.vo.ModelSmallVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ModelServiceImpl {
    @Autowired
    private NlModelMapper modelMapper;
    @Autowired
    private NlModelSmallMapper modelSmallMapper;

    /**
     * 模型上传
     * @param modelDTO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ServerResponse upload(ModelDTO modelDTO){
        String mlName = modelDTO.getMlName();
        String mlIntro = modelDTO.getMlIntro();
        String mlAlgo = modelDTO.getMlAlgo();
        String mlResult = modelDTO.getMlResult();
        if (StringUtils.isEmpty(mlName) || StringUtils.isEmpty(mlAlgo)
        || StringUtils.isEmpty(mlIntro) || StringUtils.isEmpty(mlResult))
            return ServerResponse.createByError("参数不全");
        NlModel param = modelMapper.selectModelByMlName(mlName);
        if (param != null) return ServerResponse.createByError("该模型名已经被使用, 请重新更换");
        String mlId = UUID.randomUUID().toString();
        List<ModelSmallDTO> msDTOS = modelDTO.getModelSmallDTOS();
        for (ModelSmallDTO msDTO : msDTOS){
            String msName = msDTO.getMsName();
            String msIntro = msDTO.getMsIntro();
            if (StringUtils.isEmpty(msName) || StringUtils.isEmpty(msIntro)){
                throw new RuntimeException("参数不能为空");
            }
            String msId = UUID.randomUUID().toString();
            NlModelSmall ms = new NlModelSmall();
            ms.setMsId(msId);
            ms.setMsName(msName);
            ms.setMsIntro(msIntro);
            ms.setMlId(mlId);
            ms.setShowFlag("1");
            int result = modelSmallMapper.insert(ms);
            if (result < 0) throw new RuntimeException("sql存储失败");
        }
        NlModel model = new NlModel();
        model.setMlId(mlId);
        model.setMlAlgo(mlAlgo);
        model.setMlIntro(mlIntro);
        model.setMlName(mlName);
        model.setMlResult(mlResult);
        model.setShowFlag("1");
        model.setMlTime(new Date());
        int result = modelMapper.insert(model);
        if (result > 0) return ServerResponse.createBySuccess(modelDTO);
        return ServerResponse.createByError("存储失败");
    }

    /**
     * 模型列表展示
     * @param pageNum
     * @param pageSize
     * @param mlAlgo
     * @param mlResult
     * @param showFlag
     * @return
     */
    public ServerResponse list(int pageNum, int pageSize,
                               String mlAlgo,  String mlResult, String showFlag){

        PageHelper.startPage(pageNum, pageSize);
        List<NlModel> models = modelMapper.selectByMlAlgoAndMlResultAndShowFlag(mlAlgo, mlResult, showFlag);
        List<ModelListVO> modelListVOS = new ArrayList<>();
        for (NlModel model : models){
            ModelListVO modelListVO = modelPOJOTOModelListVO(model);
            modelListVOS.add(modelListVO);
        }
        PageInfo pageInfo = new PageInfo(modelListVOS);
        ModelPageInfoVO modelPageInfoVO = pageInfoToModelPageInfoVO(pageInfo);

        return ServerResponse.createBySuccess(modelPageInfoVO);
    }
    private ModelPageInfoVO pageInfoToModelPageInfoVO(PageInfo pageInfo){
        ModelPageInfoVO modelPageInfoVO = new ModelPageInfoVO();
        List<ModelListVO> modelListVOS = pageInfo.getList();
        modelPageInfoVO.setModelListVOS(modelListVOS);
        modelPageInfoVO.setPageNum(pageInfo.getPageNum());
        modelPageInfoVO.setPageSize(pageInfo.getPageSize());
        modelPageInfoVO.setPages(pageInfo.getPages());
        modelPageInfoVO.setTotal(pageInfo.getTotal());
        modelPageInfoVO.setNavigateFirstPage(pageInfo.getNavigateFirstPage());
        modelPageInfoVO.setNavigateLastPage(pageInfo.getNavigateLastPage());
        modelPageInfoVO.setNavigatepageNums(pageInfo.getNavigatepageNums());
        return modelPageInfoVO;
    }

    /**
     * 获取一个模型的详细信息
     * @param mlId
     * @return
     */
    public ServerResponse getOne(String mlId){
        if (StringUtils.isEmpty(mlId)) return ServerResponse.createByError("参数不全");
        NlModel model = modelMapper.selectByMlId(mlId);
        if (model == null) return ServerResponse.createByError("模型不存在");
        List<NlModelSmall> modelSmalls = modelSmallMapper.selectByMlId(mlId);
        ModelDetailsVO modelDetailsVO = modelPPJOTOModelDetailsVO(model, modelSmalls);
        return ServerResponse.createBySuccess(modelDetailsVO);

    }

    /**
     * 模型更新
     * @param mlId
     * @param modelPutDTO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ServerResponse update(String mlId, ModelPutDTO modelPutDTO){
        String mlName = modelPutDTO.getMlName();
        String mlIntro = modelPutDTO.getMlIntro();
        String mlAlgo = modelPutDTO.getMlAlgo();
        String mlResult = modelPutDTO.getMlResult();
        String showFlag = modelPutDTO.getShowFlag();
        if (StringUtils.isEmpty(mlId)) return ServerResponse.createByError("参数不全");
        NlModel param = modelMapper.selectByMlId(mlId);
        if (param == null) return ServerResponse.createByError("需要修改的对象不存在");
        List<ModelSmallPutDTO> modelSmallPutDTOS = modelPutDTO.getModelSmallPutDTOS();

        int deleteResult = modelSmallMapper.deleteByMlId(mlId);
        if (deleteResult < 0) return ServerResponse.createByError("修改失败");
        for (ModelSmallPutDTO msDTO : modelSmallPutDTOS){
            String msName = msDTO.getMsName();
            String msIntro = msDTO.getMsIntro();
            if (StringUtils.isEmpty(msName) || StringUtils.isEmpty(msIntro)){
                throw new RuntimeException("参数不能为空");
            }
            String msId = UUID.randomUUID().toString();
            NlModelSmall ms = new NlModelSmall();
            ms.setMsId(msId);
            ms.setMsName(msName);
            ms.setMsIntro(msIntro);
            ms.setMlId(mlId);
            ms.setShowFlag(showFlag);
            int result = modelSmallMapper.insert(ms);
            if (result < 0) throw new RuntimeException("sql存储失败");
        }
        NlModel model = new NlModel();
        model.setMlId(mlId);
        model.setMlAlgo(mlAlgo);
        model.setMlIntro(mlIntro);
        model.setMlName(mlName);
        model.setMlResult(mlResult);
        model.setShowFlag(showFlag);

        int result = modelMapper.updateByMlId(model);
        if (result > 0) return ServerResponse.createBySuccess(modelPutDTO);
        return ServerResponse.createByError("修改失败");

    }

    /**
     * 根据模型编号获取模型参数信息
     * @param mlId
     * @return
     */
    public ServerResponse getModelSmall(String mlId){
        List<NlModelSmall> modelSmalls = modelSmallMapper.selectByMlId(mlId);
        List<ModelSmallVO> modelSmallVOS = new ArrayList<>();
        for (NlModelSmall modelSmall : modelSmalls){
            ModelSmallVO modelSmallVO = modelSmallPOJOTOModelSmallVO(modelSmall);
            modelSmallVOS.add(modelSmallVO);
        }
        return ServerResponse.createBySuccess(modelSmallVOS);
    }

    /**
     * 服务间数据传输, 获取模型名
     * @param mlId
     * @return
     */
    public ServerResponse getModelName(String mlId){
        if (StringUtils.isEmpty(mlId)) return ServerResponse.createByError("参数不全");
        NlModel model = modelMapper.selectByMlId(mlId);
        if (model == null) {
            System.out.println("调用失败, mlID: "+mlId);
            return ServerResponse.createByError("模型不存在");
        }
        Map<String, String> result = new HashMap<>();
        result.put("mlName", model.getMlName());
        result.put("mlAlgo", model.getMlAlgo());
        return ServerResponse.createBySuccess(result);
    }
    private ModelDetailsVO modelPPJOTOModelDetailsVO(NlModel model, List<NlModelSmall> modelSmalls){
        ModelDetailsVO modelDetailsVO = new ModelDetailsVO();
        List<ModelSmallVO> modelSmallVOS = new ArrayList<>();
        for (NlModelSmall modelSmall : modelSmalls){
            ModelSmallVO modelSmallVO = modelSmallPOJOTOModelSmallVO(modelSmall);
            modelSmallVOS.add(modelSmallVO);
        }

        modelDetailsVO.setMlAlgo(model.getMlAlgo());
        modelDetailsVO.setMlIntro(model.getMlIntro());
        modelDetailsVO.setMlResult(model.getMlResult());
        modelDetailsVO.setMlName(model.getMlName());
        modelDetailsVO.setModelSmallVOS(modelSmallVOS);
        return modelDetailsVO;
    }

    /**
     * 模型参数POJO转模型参数数据展示层对象
     * @param modelSmall
     * @return
     */
    private ModelSmallVO modelSmallPOJOTOModelSmallVO(NlModelSmall modelSmall){
        ModelSmallVO modelSmallVO = new ModelSmallVO();
        modelSmallVO.setMsName(modelSmall.getMsName());
        modelSmallVO.setMsIntro(modelSmall.getMsIntro());
        modelSmallVO.setMsId(modelSmall.getMsId());
        return modelSmallVO;
    }
    /**
     * 模型POJO转换模型列表显示层对象
     * @param model
     * @return
     */
    private ModelListVO modelPOJOTOModelListVO(NlModel model){
        ModelListVO modelListVO = new ModelListVO();
        modelListVO.setMlAlgo(model.getMlAlgo());
        modelListVO.setMlIntro(model.getMlIntro());
        modelListVO.setMlId(model.getMlId());
        modelListVO.setMlName(model.getMlName());
        modelListVO.setMlResult(model.getMlResult());
        return modelListVO;
    }


}
