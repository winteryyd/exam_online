package com.ando.exam.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ando.exam.sys.entity.Dict;


public interface DictRepository extends JpaRepository<Dict, Long>, JpaSpecificationExecutor<Dict> {
	List<Dict> findByDictTypeOrderByRank(String dictType);
}
