//package cn.hctech2006.softcup.isomerase.controller;
//
//import cn.hctech2006.softcup.isomerase.dto.DatabaseDTO;
//import com.alibaba.fastjson.JSONObject;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import java.net.URI;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//public class TestNewDynamicController {
//
//    private static Logger logger = LoggerFactory.getLogger(TestNewDynamicController.class);
//    @Autowired
//    private MockMvc mvc;
//
//    @Test
//    public void testStorageDatasource() throws Exception {
//        DatabaseDTO databaseDTO = new DatabaseDTO();
//        databaseDTO.setHost("localhost");
//        databaseDTO.setDbPassword("123456");
//        databaseDTO.setDbUser("root");
//        databaseDTO.setDbTable("soft_cup_1");
//        databaseDTO.setDbType("mysql");
//        String databaseDTOJsonStr = JSONObject.toJSONString(databaseDTO);
//        ResultActions ra = mvc.perform(MockMvcRequestBuilders
//                .post(new URI("/dynamic")
//                ).content(databaseDTOJsonStr)
//                .contentType("application/json;charset=UTF-8")
//        );
//        MvcResult result = ra.andReturn();
//        logger.info("result: "+result.getResponse().getContentAsString());
//        //dynamicService.storage(databaseDTO);
//        Thread.sleep(10000);
//    }
//}
