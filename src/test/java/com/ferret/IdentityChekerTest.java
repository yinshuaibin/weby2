package com.ferret;

import com.ferret.service.identify.IdentityChecker;
import com.ferret.service.identify.IdentityFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IdentityChekerTest {
    @Autowired
    IdentityFactory identityFactory;

    @Autowired
    IdentityFactory identityFactory1;
    @Test
    public void testTheadSave() {
        IdentityChecker identityChecker1 = identityFactory.getIdentityChecker();
        System.out.println(identityChecker1.hashCode());
        IdentityChecker identityChecker2 = identityFactory1.getIdentityChecker();
        System.out.println(identityChecker2.hashCode());
    }
}
