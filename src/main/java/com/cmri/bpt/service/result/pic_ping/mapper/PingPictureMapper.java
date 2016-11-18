package com.cmri.bpt.service.result.pic_ping.mapper;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.PingPicturePO;
import com.framework.layer.dao.ibatis.IbatisMapper;
@IbatisMapper
public interface PingPictureMapper {
    public void savePingPicture(PingPicturePO pingPicturePO) throws SQLException;
    public List<PingPicturePO> selectPingPicture(String case_name) throws SQLException;
    
}
