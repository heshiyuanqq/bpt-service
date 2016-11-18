package com.cmri.bpt.service.result.pic_network;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.NetWorkFlowPicturePO;
import com.cmri.bpt.common.entity.NetWorkPicturePO;

public interface INetWorkPictureService {
   public void insertNetWorkPicture(NetWorkPicturePO netWorkPicturePO) throws SQLException;
   public List<NetWorkPicturePO> selectNetWorkPicture(String case_name) throws SQLException;
   public List<NetWorkPicturePO> insertnetworkpicturedata(String path,String case_name);
   public List<NetWorkFlowPicturePO> insertNetworkFlowdata(String path,String case_name,String name);
   
}
