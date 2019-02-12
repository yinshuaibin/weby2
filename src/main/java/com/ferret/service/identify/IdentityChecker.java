package com.ferret.service.identify;

import java.util.Date;

/**
 * 身份落地服务
 */
public interface IdentityChecker {
    void check(Date startTime, Date endTime);
    void check(String personID);
    void check();
}
