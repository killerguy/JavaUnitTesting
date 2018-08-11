package com.mukul.mvc.controller;

import com.mukul.mvc.domain.User;
import com.mukul.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{userName}", produces = APPLICATION_JSON_VALUE, method = GET)
    public User getUserDetails(@PathVariable("userName") String userName) throws Exception {
        return userService.findUserByUserName(userName);
    }

    @RequestMapping(value = "/id/{id}", produces = APPLICATION_JSON_VALUE, method = GET)
    public User getUserDetailsById(@PathVariable("id") int userId) throws Exception {
        return userService.findUserByUserId(userId);
    }

}
