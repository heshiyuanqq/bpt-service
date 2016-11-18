package com.cmri.bpt.service.result.pic_web;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.WebPicturePO;


public interface IWebPictureService {
	   public void insertWebPicture(WebPicturePO webPicturePO) throws SQLException;
	   public List<WebPicturePO> selectWebPicture(String case_name) throws SQLException;
	   public List<WebPicturePO> insertwebpicturedata(String path,String case_name) throws Exception;
}
