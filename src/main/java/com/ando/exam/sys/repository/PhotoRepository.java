package com.ando.exam.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ando.exam.sys.entity.Photo;


public interface PhotoRepository extends JpaRepository<Photo, String>, JpaSpecificationExecutor<Photo> {
}
