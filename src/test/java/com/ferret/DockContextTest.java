package com.ferret;

import com.ferret.docker.DockContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DockContextTest {
    @Autowired
    private DockContext dockContext;
    @Test
    public void testRequest() {
        Map<String, String> ps = new HashMap<>(1);
        ps.put("gmsfhm", "11111");
        String data = dockContext.request(ps, "rk");
        System.out.print(data);
    }
}
