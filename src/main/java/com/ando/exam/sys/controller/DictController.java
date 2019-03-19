package com.ando.exam.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ando.exam.base.BaseController;
import com.ando.exam.base.BasePage;
import com.ando.exam.sys.config.Constant;
import com.ando.exam.sys.entity.Dict;
import com.ando.exam.sys.service.DictService;

@RestController
@RequestMapping(Constant.APP_SYS_DICT)
public class DictController extends BaseController {

	@Autowired
	private DictService dictService;
	
	@RequestMapping("/save")
	public Object save(Dict dict) {
		try {
			dictService.save(dict);
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	@RequestMapping("/delete")
	public Object delete(String dictIds) {
		try {
			String[] dictIdsArr = dictIds.trim().split(",");
			for(String dictId:dictIdsArr){
				if(!StringUtils.isEmpty(dictId)) {
					dictService.delete(Long.valueOf(dictId));
				}
			}
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	@GetMapping(value = "/getPage")
	public Object getPage(Dict dict,BasePage<Dict> basePage) {
		try {
			basePage.setEntity(dict);
			Page<Dict> dictList = dictService.getPage(basePage);
			this.getMap().put("dicts", dictList.getContent());
			this.getMap().put("total", dictList.getTotalElements());
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	@GetMapping(value = "/getDicts")
	public Object getDictList(String dictType) {
		try {
			List<Dict> dictList = dictService.getDictList(dictType);
			this.getMap().put("dicts", dictList);
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
}