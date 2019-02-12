package com.ferret.docker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class DockContext implements Request{
    @Autowired
    private Request request;
    @Autowired
    private Parser parser;
    @Override
    public String request(Map<String, String> params, String type) {
        return parser.parse(request.request(params, type), type);
    }
}
