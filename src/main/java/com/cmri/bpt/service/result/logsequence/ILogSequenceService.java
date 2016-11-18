package com.cmri.bpt.service.result.logsequence;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.LogSequenceVO;
/**
 * 日志序列号服务接口
 * @author zzk
 * */
public interface ILogSequenceService {
	
	/**
	 * 根据case_file_name与Location查询logsequence。
	 * @param logSequenceVO
	 * @return
	 * */
	public LogSequenceVO selectLogSequenceByCaseNameAndLocation(LogSequenceVO logSequenceVO) throws SQLException;
	
	/**
	 * 根据case_file_name查询该logsequence。
	 * @param logSequenceVO
	 * @return
	 * */
	public List<LogSequenceVO> selectLogSequenceByCaseFileName(LogSequenceVO logSequenceVO) throws SQLException;
	/**
	 * 根据case_file_name和flag查询该未处理过的logsequence。
	 * @param logSequenceVO
	 * @return
	 * */
	public List<LogSequenceVO> selectnosolveLogSequenceByCaseFileName(LogSequenceVO logSequenceVO) throws SQLException;
//	/**
//	 * 根据case_file_name和location更新LogSequenceVO。
//	 * @param logSequenceVO
//	 * */
//	public void insertLogSequence(LogSequenceVO logSequenceVO);
//	/**
//	 * 更新LogSequenceVO
//	 * @param logSequenceVO
//	 * */
//	public void updateLogSequence(LogSequenceVO  logSequenceVO ) throws SQLException;
	/**
	 * 根据case_file_name和location查询logSequenceVO，如果不存在则新增，如果存在则保存。
	 * @param logSequenceVO
	 * */
	public void saveLogSequence(LogSequenceVO logSequenceVO);
}
