package com.cmri.bpt.service.result.pic_weixin;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.WeiXinPicturePO;
import com.cmri.bpt.common.entity.WeiXinTypePO;


public interface IWeiXinPictureService {
	 public void insertWeiXinPicture(WeiXinPicturePO weixinPicturePO) throws SQLException;
	 public List<WeiXinPicturePO> selectWeiXinPicture(WeiXinTypePO weixinTypePO) throws SQLException;
	 public List<WeiXinPicturePO> insertweixinpicturedata(String path,String case_name,String type) throws Exception;
}
