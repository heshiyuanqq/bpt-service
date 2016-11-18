package com.cmri.bpt.service.result.pic_cellchange.mapper;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.CellChangePicturePO;
import com.framework.layer.dao.ibatis.IbatisMapper;

@IbatisMapper
public interface CellChangePictureMapper {
	public void saveCellChangePicture(CellChangePicturePO cellChangePicturePO) throws SQLException;
    public List<CellChangePicturePO> selectCellChangePicture(String case_name) throws SQLException;
}
