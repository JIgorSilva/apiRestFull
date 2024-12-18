package com.example.apirest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apirest.model.UserApi;
import com.example.apirest.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@Controller
@RequestMapping("/api/users")
public class UserController {
    private UserRepository repositoryUser;
    
    public UserController(UserRepository repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    @PostMapping("/criar")
    public ResponseEntity<UserApi> criarUsuario(@RequestBody UserApi user){
        UserApi usuarioSalvo = this.repositoryUser.save(user);
        return ResponseEntity.ok(usuarioSalvo);
    }
}
