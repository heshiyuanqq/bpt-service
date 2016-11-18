package com.cmri.bpt.service.result.pic_ftp;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.FtpPicturePO;
public interface IFtpPictureService {
	   public void insertFtpPicture(FtpPicturePO ftpPicturePO) throws SQLException;
	   public List<FtpPicturePO> selectFtpPicture(String case_name) throws SQLException;
	   public List<FtpPicturePO> insertftppicturedata(String path,String case_name) throws Exception;
}
