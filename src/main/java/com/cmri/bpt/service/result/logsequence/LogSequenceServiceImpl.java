package com.cmri.bpt.service.result.logsequence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmri.bpt.common.entity.LogSequenceVO;
import com.cmri.bpt.service.result.logsequence.mapper.LogSequenceMapper;

/**
 * 日志序列号服务接口实现类
 * @author zzk
 * */
@Service(value = "logSequenceServiceImpl")
public class LogSequenceServiceImpl implements ILogSequenceService {
	@Autowired
	private LogSequenceMapper logSequenceMapper;

	@Override
	public LogSequenceVO selectLogSequenceByCaseNameAndLocation(LogSequenceVO logSequenceVO)
			throws SQLException {
		return this.logSequenceMapper.selectLogSequenceByCaseNameAndLocation(logSequenceVO);
	}

	@Transactional
	@Override
	public void saveLogSequence(LogSequenceVO logSequenceVO) {
		
		LogSequenceVO selectVO = this.logSequenceMapper.
				selectLogSequenceByCaseNameAndLocation(logSequenceVO);
		if(selectVO == null){
			this.logSequenceMapper.insertLogSequence(logSequenceVO);
		} else {
			//防止序号倒存
			if(logSequenceVO.getSequence_no() > selectVO.getSequence_no()){
				this.logSequenceMapper.updateLogSequence(logSequenceVO);
			}
			
		}
	}

	@Override
	public List<LogSequenceVO> selectLogSequenceByCaseFileName(
			LogSequenceVO logSequenceVO) throws SQLException {
		return this.logSequenceMapper.selectLogSequenceByCaseFileName(logSequenceVO);
	}

	@Override
	public List<LogSequenceVO> selectnosolveLogSequenceByCaseFileName(
			LogSequenceVO logSequenceVO) throws SQLException {
		// TODO Auto-generated method stub
		List<LogSequenceVO> listlog=new ArrayList<LogSequenceVO>();
		listlog= this.logSequenceMapper.selectnosolveLogSequenceByCaseFileName(logSequenceVO);
		for(LogSequenceVO log:listlog){
			this.logSequenceMapper.updatesolvedLogSequence(log);
		}
		return listlog;
	}


//	@Transactional
//	@Override
//	public void insertLogSequence(LogSequenceVO logSequenceVO) {
//		this.logSequenceMapper.insertLogSequence(logSequenceVO);
//	}
//	
//	@Transactional
//	@Override
//	public void updateLogSequence(LogSequenceVO logSequenceVO)
//			throws SQLException {
//		this.logSequenceMapper.updateLogSequence(logSequenceVO);
//	}
	
}
