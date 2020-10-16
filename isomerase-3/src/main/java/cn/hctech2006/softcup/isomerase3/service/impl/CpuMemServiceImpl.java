package cn.hctech2006.softcup.isomerase3.service.impl;

import cn.hctech2006.softcup.isomerase3.bean.NlCminfo;
import cn.hctech2006.softcup.isomerase3.config.CpuMemUsage;
import cn.hctech2006.softcup.isomerase3.config.MemUsage;
import cn.hctech2006.softcup.isomerase3.mapper.NlCminfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class CpuMemServiceImpl {
    @Autowired
    private NlCminfoMapper cminfoMapper;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    @Scheduled(fixedRate = 1200)
    @Async
    public void renew(){
        //System.out.println(sdf.format(new Date())+" CPU利用率: "+CpuMemUsage.get()+" 内存利用率: "+MemUsage.get());
        NlCminfo cminfo = new NlCminfo();
        cminfo.setCmId(UUID.randomUUID().toString());
        cminfo.setCpuRate(String.valueOf(CpuMemUsage.get()));
        cminfo.setMemRate(String.valueOf(MemUsage.get()));
        cminfo.setTime(new Date());
        cminfoMapper.insert(cminfo);
    }
}
