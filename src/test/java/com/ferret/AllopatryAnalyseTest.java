package com.ferret;

import com.ferret.dao.AllopatryAnalyseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 区域碰撞测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AllopatryAnalyseTest {
    @Autowired
    private AllopatryAnalyseMapper allopatryAnalyseMapper;
    @Test
    public void testAllopatryAnalyese() {
        String startTimeA = "2018-10-01";
        String endTimeA = "2018-10-17";
        String[] cameraIpsA = {"192.168.0.66", "192.168.0.64"};
        String startTimeB = "2018-10-18";
        String endTimeB = "2018-11-18";
        String[] cameraIpsB = {"192.168.0.66", "192.168.0.64"};
        List<Map<String, Integer>> result = allopatryAnalyseMapper.findAllopatryAnalyse(startTimeA,endTimeA, Arrays.asList(cameraIpsA),
                                            startTimeB,endTimeB,Arrays.asList(cameraIpsB));
        System.out.println(result.size());
    }
}
