package com.ferret.service.common;

import com.ferret.bean.pagebean.HistoryParam;
import com.ferret.dto.HistoryPassDTO;

import java.util.List;
import java.util.Map;

/**
 * @author cc;
 * @since 2018/1/18;
 */
public interface IHistoryPassService {

    List<HistoryPassDTO> listHistoryPassDTO(Integer[] featureIds);
    Map selectHistoryPass(HistoryParam param);
}
