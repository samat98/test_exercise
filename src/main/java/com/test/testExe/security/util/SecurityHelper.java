package com.test.testExe.security.util;

import com.test.testExe.DAO.UserDao;
import com.test.testExe.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityHelper implements UserDetailsService {

    @Autowired
    private UserDao userService;

    @Autowired
    AuthenticationManager authenticationManager;

    public UserDetails loadUserByUsername(String username) {
        return userService.findOneByUsername(username).get();
    }

    public User getPrincipal () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findOneByUsername(authentication.getName()).get();
        if (user == null) {
            throw new SecurityException("User is not logged in");
        }
    return user;
    }

    public Authentication getAuthentication(String username, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
    }
}
