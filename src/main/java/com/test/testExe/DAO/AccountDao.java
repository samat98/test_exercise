package com.test.testExe.DAO;

import com.test.testExe.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDao extends JpaRepository<Account, Long> {
    Optional<Account> findByNum(String num);
}
