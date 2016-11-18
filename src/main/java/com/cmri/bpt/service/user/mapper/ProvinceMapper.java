package com.cmri.bpt.service.user.mapper;

import java.util.List;

import com.cmri.bpt.service.user.bo.Select2Domain;
import com.framework.layer.dao.ibatis.IbatisMapper;

@IbatisMapper
public interface ProvinceMapper {

	public List<Select2Domain> queryParentProvinces();

	public List<Select2Domain> queryCityByProId(Long id);

	public int queryIsEmptyCity(Integer cityId);
}
