package com.ando.exam.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ando.exam.sys.entity.User;


public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
}
