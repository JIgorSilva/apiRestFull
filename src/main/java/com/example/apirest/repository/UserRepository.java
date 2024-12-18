package com.example.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.apirest.model.UserApi;

public interface UserRepository extends JpaRepository<UserApi,Integer>{

}
