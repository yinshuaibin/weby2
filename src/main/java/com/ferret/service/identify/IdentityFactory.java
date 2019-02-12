package com.ferret.service.identify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IdentityFactory {
    @Autowired
    private SjIdentityChecker sjIdentityChecker;

    @Autowired
    private StIdentityChecker stIdentityChecker;

    @Autowired
    private MockIdentityChecker mockIdentityChecker;

    @Value("${identityCheck.checkerType}")
    private String chekerType = "";

    public IdentityChecker getIdentityChecker(){
        if(chekerType.equals("st")) {
            return stIdentityChecker;
        }
        if(chekerType.equals("sj")) {
            return sjIdentityChecker;
        }
        return mockIdentityChecker;
    }
}
