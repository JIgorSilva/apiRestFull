package com.example.apirest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apirest.model.UserApi;
import com.example.apirest.repository.UserRepository;


@RestController
@Controller
@RequestMapping("/api/users")
public class UserController {
    private UserRepository repositoryUser;

    public UserController(UserRepository repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    @PostMapping("/criar")
    public ResponseEntity<UserApi> criarUsuario(@RequestBody UserApi user) {
        UserApi usuarioSalvo = this.repositoryUser.save(user);
        return ResponseEntity.ok(usuarioSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserApi> buscarUsuarioId(@PathVariable Integer id) {
        Optional<UserApi> user = repositoryUser.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
}
