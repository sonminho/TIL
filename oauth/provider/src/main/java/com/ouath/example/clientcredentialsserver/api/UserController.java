package com.ouath.example.clientcredentialsserver.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class UserController {

    @RequestMapping("/users")
    public ResponseEntity<List<UserProfile>> profile() {
        return ResponseEntity.ok(getUsers());
    }

    private List<UserProfile> getUsers() {
        List<UserProfile> users = new ArrayList<>();
        users.add(new UserProfile("mino", "mino@naver.com"));
        users.add(new UserProfile("mino2", "mino2@naver.com"));
        users.add(new UserProfile("mino3", "mino3@naver.com"));

        return users;
    }

}
