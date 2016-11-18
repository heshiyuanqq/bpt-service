package com.cmri.bpt.service.result.logsequence.mapper;

import java.util.List;

import com.cmri.bpt.common.entity.LogSequenceVO;
import com.framework.layer.dao.ibatis.IbatisMapper;
/**
 * LogSequenceMapper,case_file_name与location为联合主键。
 * @author zzk
 * 
 * */
@IbatisMapper
public interface LogSequenceMapper {
	
	/**
	 * 根据case_file_name和location查询LogSequenceVO。
	 * @param logSequenceVO
	 * @return
	 * */
	public LogSequenceVO selectLogSequenceByCaseNameAndLocation(LogSequenceVO logSequenceVO);
	/**
	 * 根据case_file_name查询LogSequenceVOs。
	 * @param logSequenceVO
	 * @return
	 * */
	public List<LogSequenceVO> selectLogSequenceByCaseFileName(LogSequenceVO logSequenceVO);
	
	/**
	 * 根据case_file_name查询未处理过的LogSequenceVOs。
	 * @param logSequenceVO
	 * @return
	 * */
	public List<LogSequenceVO> selectnosolveLogSequenceByCaseFileName(LogSequenceVO logSequenceVO);
	/**
	 * 将已处理的sequence 标志位置为1。
	 * @param logSequenceVO
	 * @return
	 * */
	
	public void updatesolvedLogSequence(LogSequenceVO logSequenceVO);
	/**
	 * 插入logSequenceVO。
	 * @param logSequenceVO
	 * @return
	 * */
	public int insertLogSequence(LogSequenceVO logSequenceVO);
	/**
	 * 根据case_file_name和location更新logSequenceVO
	 * @param logSequenceVO
	 * @return
	 * */
	public int updateLogSequence(LogSequenceVO logSequenceVO);

}
