package com.cmri.bpt.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmri.bpt.service.user.bo.Province;
import com.cmri.bpt.service.user.bo.Select2Domain;
import com.cmri.bpt.service.user.mapper.ProvinceMapper;


@Service
public class ProvinceServiceImpl implements ProvinceService{
	
	@Autowired
	private ProvinceMapper provinceMapper;

	public List<Select2Domain> queryParentProvinces(){
		return provinceMapper.queryParentProvinces();
	}

	public List<Select2Domain> queryCityByProId(Long id) {
		return provinceMapper.queryCityByProId(id);
	}

	public boolean queryIsEmptyCity(Integer cityId) {
		return provinceMapper.queryIsEmptyCity(cityId)>0;
	}

}
