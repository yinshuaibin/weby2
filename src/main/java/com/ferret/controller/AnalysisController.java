package com.ferret.controller;

import com.ferret.service.face.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class AnalysisController extends BaseController {


    @Autowired
    private FaceService faceService;
    /**
     * 判断两张图片的相似度
     * @param requestMap
     * @return
     */
	@RequestMapping(value="/face/rxjs/analys", method=RequestMethod.POST)
    public Float getAnalysis (@RequestBody Map requestMap ) {
        return faceService.faceCompareInterface((String) requestMap.get("img1"), (String) requestMap.get("img2"));
     }
}
