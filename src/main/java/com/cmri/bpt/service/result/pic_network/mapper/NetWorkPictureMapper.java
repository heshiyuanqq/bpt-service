package com.cmri.bpt.service.result.pic_network.mapper;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.NetWorkPicturePO;
import com.framework.layer.dao.ibatis.IbatisMapper;

@IbatisMapper
public interface NetWorkPictureMapper {
	public void saveNetWorkPicture(NetWorkPicturePO netWorkPicturePO) throws SQLException;
	public List<NetWorkPicturePO> selectNetWorkPicture(String case_name) throws SQLException;

}
