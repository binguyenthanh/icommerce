package com.icommerce.audit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuditApplicationTests {

    @Test
    void contextLoads() {
    }
    
    @Test
    public void applicationStarts() {
    	AuditApplication.main(new String[] {});
    }

}
