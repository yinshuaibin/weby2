package com.ferret.service.analyse.imp;

import com.ferret.bean.AggregationAnalyse;
import com.ferret.dao.AggregationAnalyseMapper;
import com.ferret.service.analyse.AggregationAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AggregationAnalyseServiceImpl implements AggregationAnalyseService {

	@Autowired
	private AggregationAnalyseMapper aggregationAnalyseMapper;

	/**
	 *	需求更改，通过相机id和时间段 分析报警表数据
	 */
	@Override
	public List<AggregationAnalyse> aggregationAnalyse(ArrayList<String> cameraIds, String beginTime, String endTime) {
		if (cameraIds != null && cameraIds.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (String cameraId:cameraIds ) {
				sb.append(cameraId).append(",");
			}
			String string = sb.deleteCharAt(sb.length() - 1).toString();
			return aggregationAnalyseMapper.aggregationAnalyseResult_new(string, beginTime, endTime);
		}
		return null;
	}
}
