package com.cmri.bpt.service.result.pic_web.mapper;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.WebPicturePO;
import com.framework.layer.dao.ibatis.IbatisMapper;

@IbatisMapper
public interface WebPictureMapper {
	public void saveWebPicture(WebPicturePO webPicturePO) throws SQLException;
    public List<WebPicturePO> selectWebPicture(String case_name) throws SQLException;
}
