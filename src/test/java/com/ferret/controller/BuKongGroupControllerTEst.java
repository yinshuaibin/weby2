package com.ferret.controller;

import com.ferret.bean.InterfaceBean.BuKongContorlType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class BuKongGroupControllerTEst {

    @Autowired
    private BuKongGroupController buKongGroupController;

    @Test
    public void testCheckName(){
        String 测试1 = buKongGroupController.checkBuKongGroupName("1");
        System.out.println(测试1);
    }

    @Test
    public void testAddGroup(){
        BuKongContorlType b = new BuKongContorlType();
        b.setRemark("12312312312");
        b.setTypename("ceshi2222");
        String s = buKongGroupController.addBuKongGroup(b);
        System.out.println(s);
    }

    @Test
    public void testUpdate(){
        BuKongContorlType b = new BuKongContorlType();
        b.setRemark("444444");
        b.setTypename("44444");
        //b.setId(103);
        String s = buKongGroupController.updateBuKongGroup(b);
        System.out.println(s);
    }

    @Test
    public void testDel(){
        String s = buKongGroupController.delBuKongGroup("103");
        System.out.println(s);
    }
}
