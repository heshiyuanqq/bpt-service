package com.cmri.bpt.service.result.pic_ftp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import com.cmri.bpt.common.csv.CSVReadToPic;
import com.cmri.bpt.common.entity.FtpPicturePO;
import com.cmri.bpt.service.result.pic_ftp.mapper.FtpPictureMapper;



@Service(value="ftpPictureServiceImpl")
public class FtpPictureServiceImpl implements IFtpPictureService{
	@Autowired
	private FtpPictureMapper ftpPictureMapper;
	@Override
	public void insertFtpPicture(FtpPicturePO ftpPicturePO) throws SQLException {
		ftpPictureMapper.saveFtpPicture(ftpPicturePO);
		
	}

	@Override
	public List<FtpPicturePO> selectFtpPicture(String case_name)
			throws SQLException {
		// TODO Auto-generated method stub
		return ftpPictureMapper.selectFtpPicture(case_name);
	}

	@Override
	public List<FtpPicturePO> insertftppicturedata(String path,
			String case_name) throws Exception {
		List<FtpPicturePO> retlistftp=new ArrayList<FtpPicturePO>();
		try{
			retlistftp=CSVReadToPic.ReadAllFtpLog(path,case_name);
			/*for(int i=0;i<retlistftp.size();i++){
				this.insertFtpPicture(retlistftp.get(i));
			}*/
		
	
		}catch (Exception e){
			e.printStackTrace();
		}
		return retlistftp;
	}

}
