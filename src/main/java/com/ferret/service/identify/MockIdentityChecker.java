package com.ferret.service.identify;

import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class MockIdentityChecker implements IdentityChecker {
    @Override
    public void check(Date startTime, Date endTime) {
        return;
    }

    @Override
    public void check(String personID) {
        System.out.println("mock check ...");
        return;
    }
    @Override
    public void check() {
        System.out.println("mock check ...");
        return;
    }
}
