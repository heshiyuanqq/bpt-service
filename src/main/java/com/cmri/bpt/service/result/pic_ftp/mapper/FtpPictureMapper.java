package com.cmri.bpt.service.result.pic_ftp.mapper;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.FtpPicturePO;
import com.framework.layer.dao.ibatis.IbatisMapper;

/**
 * 
 * @author 范晓文
 * FTP上传和下载
 * @日期 2015年11月6日
 */
@IbatisMapper
public interface FtpPictureMapper {
	public void saveFtpPicture(FtpPicturePO ftpPicturePO) throws SQLException;
    public List<FtpPicturePO> selectFtpPicture(String case_name) throws SQLException;
}
