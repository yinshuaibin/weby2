package com.ferret.service.analyse;

import com.ferret.bean.AggregationAnalyse;

import java.util.ArrayList;
import java.util.List;

public interface AggregationAnalyseService {

	List<AggregationAnalyse> aggregationAnalyse(ArrayList<String> cameraIds, String beginTime, String endTime);
}
