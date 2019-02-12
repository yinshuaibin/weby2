package com.ferret.controller;

import com.ferret.service.identify.IdentityTask;
import com.ferret.task.TaskEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ferret.service.identify.IdentityFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 身份落地服务
 */
@Controller
@RequestMapping("/identity")
public class IdentityController {
    @Autowired
    IdentityFactory identityFactory;
    @RequestMapping("/checkByPersonId/{id}")
    public ResponseEntity<?> checkByPersonID(@PathVariable String id) {
        this.identityFactory.getIdentityChecker().check(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/checkByTime/{startDate}/{endDate}")
    public ResponseEntity<?> checkByPersonID(@PathVariable String startDate, @PathVariable String endDate) {
        String dateFmt = "yyyyMMdd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFmt);
        try {
            TaskEngine.getInstance().addTask(new IdentityTask(this.identityFactory.getIdentityChecker(),simpleDateFormat.parse(startDate), simpleDateFormat.parse(endDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }
}
