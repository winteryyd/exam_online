package com.ando.exam.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ando.exam.base.BasePage;
import com.ando.exam.sys.entity.Dict;
import com.ando.exam.sys.repository.DictRepository;
import com.ando.exam.sys.service.DictService;

@Service
public class DictServiceImpl implements DictService {
	@Autowired
	private DictRepository dictRepository;

	public Page<Dict> getPage(final BasePage<Dict> page) {
		Pageable pageable = null;
		if(null!=page.getDirection()&&null!=page.getProperty()) {
			Sort sort = Sort.by(Sort.Direction.fromString(page.getDirection()), page.getProperty());
			pageable = PageRequest.of(page.getPage(), page.getLimit(), sort);
		}else {
			pageable = PageRequest.of(page.getPage(), page.getLimit());
		}
		Specification<Dict> specification = null;
		if (page.getEntity() != null) {
			specification = new Specification<Dict>() {
				private static final long serialVersionUID = -235756344723313266L;

				public Predicate toPredicate(Root<Dict> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
					List<Predicate> list = new ArrayList<Predicate>();
					if (null != page.getEntity().getDictType() && !"".equals(page.getEntity().getDictType())) {
						Predicate p = criteriaBuilder.like(root.get("dictType").as(String.class), "%"+page.getEntity().getDictType()+"%");
						list.add(p);
					}
					if (null != page.getEntity().getDictValue() && !"".equals(page.getEntity().getDictValue())) {
						Predicate p = criteriaBuilder.like(root.get("dictValue").as(String.class), "%"+page.getEntity().getDictValue()+"%");
						list.add(p);
					}
					if (null != page.getEntity().getDictLabel() && !"".equals(page.getEntity().getDictLabel())) {
						Predicate p = criteriaBuilder.like(root.get("dictLabel").as(String.class), "%"+page.getEntity().getDictLabel()+"%");
						list.add(p);
					}
					if(list.size()>0) {
						Predicate[] pre = new Predicate[list.size()];
						Predicate predicate= criteriaBuilder.or(list.toArray(pre));
						query.where(predicate);
					}
					return query.getRestriction();
				}
			};
		}
		return dictRepository.findAll(specification,pageable);
	}

	
	public void save(Dict dict) {
		dictRepository.save(dict);
	}

	
	public void delete(Long dictId) {
		dictRepository.deleteById(dictId);
	}

	
	public List<Dict> getDictList(String dictType) {
		return dictRepository.findByDictTypeOrderByRank(dictType);
	}

	
	public Dict getDict(String dictType) {
		List<Dict> list = this.getDictList(dictType);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}
}
