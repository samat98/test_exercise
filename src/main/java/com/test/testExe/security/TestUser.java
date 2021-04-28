package com.test.testExe.security;

import com.test.testExe.DAO.AccountDao;
import com.test.testExe.DAO.TransactionDao;
import com.test.testExe.DAO.UserDao;
import com.test.testExe.models.Account;
import com.test.testExe.models.Transaction;
import com.test.testExe.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestUser {
    private UserDao userDao;
    private AccountDao accountDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public TestUser(UserDao userDao, AccountDao accountDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUserEntity() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("12345"));
        userDao.save(user);
    }
    @Transactional
    public void createAccountEntity() {
        Account account = new Account();
        account.setNum("999777111222");
        Account account2 = new Account();
        account.setNum("999777222333");

        accountDao.save(account);
        accountDao.save(account2);
    }
}
