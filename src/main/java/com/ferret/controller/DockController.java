package com.ferret.controller;

import com.ferret.docker.DockContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DockController extends BaseController {
    @Autowired
    private DockContext dockContext;
    @GetMapping("/dock/getPersonInfo/{idNum}/{type}")
    public ResponseEntity<?> requestStPersonInfoByIdCard(@PathVariable("idNum")  String idNum, @PathVariable("type")  String type) {
        Map<String, String> ps = new HashMap<>(1);
        ps.put("gmsfhm", idNum);
        String data = dockContext.request(ps, type);
        return ResponseEntity.ok(data);
    }
}
