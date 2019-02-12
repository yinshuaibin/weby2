package com.ferret.controller;

import com.ferret.bean.pagebean.HistoryParam;
import com.ferret.dto.HistoryPassDTO;
import com.ferret.service.common.impl.HistoryPassServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator;
 * @since 2018/1/18;
 */
@Slf4j
@RestController
public class HistoryPassController extends BaseController {

    @Autowired
    private HistoryPassServiceImpl historyPassService;

    @RequestMapping("/historyPass")
    public Map getHistoryPass(@RequestBody HistoryParam param) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(simpleDateFormat.parse(param.getEndDateTime()).getTime() - simpleDateFormat.parse(param.getStartDateTime()).getTime()> 604800000){
            System.err.println("所选时间不能超过一周");
            return null;
        }
        Map map = historyPassService.selectHistoryPass(param);
        return map;
    }

    /**
     * 根据特征id集合查询对应的通行记录;
     *
     * @param featureIds 特征id数组
     * @return
     */
    @RequestMapping(value = "/historypass/search", method = RequestMethod.GET)
    public ResponseEntity listHistoryPassDTOByFeautreIds(Integer[] featureIds) {
        List<HistoryPassDTO> list = historyPassService.listHistoryPassDTO(featureIds);
        return ResponseEntity.ok(list);
    }


}
