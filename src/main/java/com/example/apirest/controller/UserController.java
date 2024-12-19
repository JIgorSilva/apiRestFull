package com.example.apirest.controller;

import static org.mockito.ArgumentMatchers.notNull;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.example.apirest.model.UserApi;
import com.example.apirest.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@Controller
@RequestMapping("/api/user")
@Tag(name = "Usuário Controller", description = "Gerenciamento de usuários")
public class UserController {
    private UserRepository repositoryUser;

    public UserController(UserRepository repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar Usuário", description = "Cria um novo usuário no sistema com os dados fornecidos. Retorna 200OK para requisões com sucesso")
    public ResponseEntity<UserApi> criarUsuario(@RequestBody UserApi user) {
        UserApi usuarioSalvo = this.repositoryUser.save(user);
        return ResponseEntity.ok(usuarioSalvo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Usuário por ID", description = "Buscar usuario usando ID.")
    public ResponseEntity<UserApi> buscarUsuarioId(@PathVariable Integer id) {
        Optional<UserApi> user = repositoryUser.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    @Operation(summary = "Buscar todos os Usuários.", description = "Retorna uma lista com todos os usuarios cadastrados.")
    public ResponseEntity<List<UserApi>> buscarTodos() {
        List<UserApi> iduser = repositoryUser.findAll();
        return ResponseEntity.ok(iduser);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Deletar usuario por id.", description = "Retorna 204 para usuario deletado com sucesso")
    public ResponseEntity<UserApi> deletarUsuarioPorId(@PathVariable Integer id) {
        Optional<UserApi> user = repositoryUser.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            repositoryUser.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("editar/{id}")
    @Operation(summary = "Editar usuario por id.", description = "Retorna 200 quando os campos usuario são alterados com sucesso")
    public ResponseEntity<UserApi> editarUsuarioPorId(@PathVariable Integer id, @RequestBody UserApi userRequest) {
        Optional<UserApi> user = repositoryUser.findById(id);

        // Verificando se o usuário existe
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar o usuário
        }

        // Atualizando o usuário
        UserApi usuarioApi = user.get();

        // Validando e atualizando os campos
        if (userRequest.getTitle() != null && !userRequest.getTitle().trim().isEmpty()) {
            usuarioApi.setTitle(userRequest.getTitle());
        }
        if (!userRequest.getCompleted()) {
            usuarioApi.setCompleted(userRequest.getCompleted());
        }

        // Salvando o usuário atualizado
        repositoryUser.save(usuarioApi);

        // Retornando o usuário atualizado com status 200 OK
        return ResponseEntity.ok(usuarioApi);

    }

}
