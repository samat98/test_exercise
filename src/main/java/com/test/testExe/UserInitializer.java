package com.test.testExe;

import com.test.testExe.security.TestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingClass({"org.springframework.boot.test.context.SpringBootTest"})
public class UserInitializer implements CommandLineRunner {

    private final TestUser testUserInitService;

    @Autowired
    public UserInitializer(TestUser testUserInitService) {
        this.testUserInitService = testUserInitService;
    }

    @Override
    public void run(String... args) {
        testUserInitService.createUserEntity();
        testUserInitService.createAccountEntity();
    }
}
