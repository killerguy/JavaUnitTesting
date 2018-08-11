package com.mukul.mvc.service;

import com.mukul.mvc.domain.User;
import com.mukul.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User findUserByUserId(int userId) {
        User user = new User();
        if (userId > 0) {
            return userRepository.findbyUserId(userId);
        }
        return user;
    }

    public User findUserByUserName(String userName) throws Exception {
        User user = new User();
        if (isNotEmpty(userName)) {
            return userRepository.findByUsername(userName);
        }
        return user;
    }

}
