package org.example.LiveSystem.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.example.LiveSystem.common.util.ResultUtil;
import org.example.LiveSystem.dao.OnlinestateDao;
import org.example.LiveSystem.dto.ChatHistoryDTO;
import org.example.LiveSystem.dto.OnlinestateDTO;
import org.example.LiveSystem.dto.Result;
import org.example.LiveSystem.entity.Onlinestate;
import org.example.LiveSystem.param.LiveStateParam;
import org.example.LiveSystem.util.EstBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Api(tags = "直播API")
@RequestMapping("/api/live")
@RestController
public class LiveController {

    @Autowired
    private OnlinestateDao onlinestateDao;

    @ApiOperation(value = "获取群直播信息", notes = "根据群ID获取群直播信息")
    @PostMapping("/getOnlineState")
    public Result<OnlinestateDTO> getOnlineState(@RequestParam(value = "gid") Integer gid) {
        Onlinestate onlinestate = onlinestateDao.findByGid(gid);
        OnlinestateDTO onlinestateDTO;
        if(onlinestate != null) {
            onlinestateDTO = EstBeanUtil.convertTo(onlinestate, OnlinestateDTO.class);
            return ResultUtil.success(onlinestateDTO);
        } else {
            onlinestate = new Onlinestate();
            onlinestate.setOUserid(gid);
            onlinestate.setOState(0);
            onlinestateDao.insert(onlinestate);
            return ResultUtil.success(EstBeanUtil.convertTo(onlinestate, OnlinestateDTO.class));
        }
    }

    @ApiOperation(value = "更新直播状态", notes = "根据群ID指定修改直播的状态")
    @PostMapping("/updateState")
    public Result<Void> updateState(@Valid LiveStateParam liveStateParam) {
        Onlinestate onlinestate = new Onlinestate();
        onlinestate.setOState(liveStateParam.getState());
        onlinestate.setOClient(liveStateParam.getClient());
        onlinestate.setOUserid(liveStateParam.getGid());
        onlinestate.setODatatime(new Date());
        if(onlinestateDao.updateState(onlinestate) > 0) {
            return ResultUtil.success();
        } else {
            return ResultUtil.fail();
        }
    }
}
