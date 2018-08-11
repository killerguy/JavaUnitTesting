package com.mukul.mvc.controller;

import com.mukul.mvc.domain.User;
import com.mukul.mvc.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    @Test
    public void shouldReturnUserDetailsBasedOnUserId() throws Exception{
        User expectedUser = buildExpectedUser();
        when(userService.findUserByUserId(1)).thenReturn(expectedUser);

        User actualUser = userController.getUserDetailsById(1);

        assertEquals(expectedUser,actualUser);
        assertThat(actualUser instanceof User, is(true));
    }

    @Test
    public void shouldReturnUserDetailsBasedOnUserName() throws Exception{
        User expectedUser = buildExpectedUser();
        when(userService.findUserByUserName("anyName")).thenReturn(expectedUser);

        User actualUser = userController.getUserDetails("anyName");

        assertEquals(expectedUser,actualUser);
        assertThat(actualUser instanceof User, is(true));
    }

    @Test
    public void shouldReturnEmptyUserDetailsBasedOnUserNameIfUserNotFound() throws Exception{
        User expectedUser = new User();
        when(userService.findUserByUserName("anyName")).thenReturn(expectedUser);

        User actualUser = userController.getUserDetails("anyName");

        assertEquals(expectedUser,actualUser);
        assertThat(actualUser instanceof User, is(true));
    }

    @Test
    public void shouldReturnEmptyUserDetailsBasedOnUserIdIfUserNotFound() throws Exception{
        User expectedUser = new User();
        when(userService.findUserByUserId(5)).thenReturn(expectedUser);

        User actualUser = userController.getUserDetailsById(5);

        assertEquals(expectedUser,actualUser);
        assertThat(actualUser instanceof User, is(true));
    }


    private User buildExpectedUser() {
        User expectedUser = new User();
        expectedUser.setFirstName("anyFirstName");
        expectedUser.setLastName("anyLastName");
        expectedUser.setAddressLine1("anyAddressText1");
        expectedUser.setAddressLine2("anyAddressText2");
        expectedUser.setCity("anyCity");
        expectedUser.setState("anyState");
        expectedUser.setCountry("anyCountry");
        expectedUser.setEmailAddress("anyEmailAddress");
        return expectedUser;
    }
}