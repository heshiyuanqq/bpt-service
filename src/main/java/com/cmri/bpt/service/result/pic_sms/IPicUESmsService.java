package com.cmri.bpt.service.result.pic_sms;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.LogSequenceVO;
import com.cmri.bpt.common.entity.PicUESmsPO;
/**
 * 手机短信节点信息服务接口
 * @author zzk
 * */
public interface IPicUESmsService {
	/**
	 * 保存手机短信节点信息
	 * @param picUESmsPO
	 * */
	public void savePicUESms(PicUESmsPO  picUESmsPO ) throws SQLException;
	/**
	 * 根据case_name查询该case_name的所有手机短信节点信息。
	 * @param case_name
	 * @return
	 * */
	public List<PicUESmsPO> selectPicUESmsByCaseName(String case_name) throws SQLException;
	/**
	 * 根据path和case_name将该case_name的所有手机短信节点信息保存到数据库并返回数据。
	 * @param path
	 * @param case_name
	 * @return
	 * */
	public List<PicUESmsPO> insertPicUESmsData(String path,String case_name);
	
	/**
	 * 生成PicUESmsPO数据。
	 * @param path
	 * @param case_name
	 * @param logSequenceVOs
	 * */
	public List<PicUESmsPO> createPicUESmsData(String path, String case_name,
			List<LogSequenceVO> logSequenceVOs);
}
