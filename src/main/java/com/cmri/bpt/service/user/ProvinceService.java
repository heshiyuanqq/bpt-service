package com.cmri.bpt.service.user;

import java.util.List;

import com.cmri.bpt.service.user.bo.Province;
import com.cmri.bpt.service.user.bo.Select2Domain;

public interface ProvinceService {

	public List<Select2Domain> queryParentProvinces();

	public List<Select2Domain> queryCityByProId(Long id);

	public boolean queryIsEmptyCity(Integer cityId);
}
