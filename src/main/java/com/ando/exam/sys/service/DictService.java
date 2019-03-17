package com.ando.exam.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ando.exam.base.BasePage;
import com.ando.exam.sys.entity.Dict;

public interface DictService {
	Page<Dict> getPage(BasePage<Dict> page);

	void save(Dict dict);

	void delete(Long dictId);

	List<Dict> getDictList(String dictType);
	
	Dict getDict(String dictType);
}
