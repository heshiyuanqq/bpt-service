package com.cmri.bpt.service.result.pic_ping;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.PingPicturePO;

public interface IPingPictureService {
   public void insertPingPicture(PingPicturePO pingPicturePO) throws SQLException;
   public List<PingPicturePO> selectPingPicture(String case_name) throws SQLException;
   public List<PingPicturePO> insertpingpicturedata(String path,String case_name) throws Exception;
}
