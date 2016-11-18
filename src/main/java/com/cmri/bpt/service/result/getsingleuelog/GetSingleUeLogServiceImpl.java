package com.cmri.bpt.service.result.getsingleuelog;

import java.io.File;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmri.bpt.common.entity.CaseUeVO;
import com.cmri.bpt.common.entity.LogSequenceVO;
import com.cmri.bpt.common.entity.SingleUeLogPO;
import com.cmri.bpt.common.util.CSVUtils;
import com.cmri.bpt.common.util.ExceptionUtil;
import com.cmri.bpt.common.util.GetFileName;
import com.cmri.bpt.service.result.getsingleuelog.mapper.GetSingleUeLogMapper;
import com.cmri.bpt.service.result.logsequence.ILogSequenceService;

@Service(value = "getSingleUeLogServiceImpl")
public class GetSingleUeLogServiceImpl implements IGetSingleUeLogService {

	static Logger logger = Logger.getLogger(GetSingleUeLogServiceImpl.class);

	@Autowired
	private ILogSequenceService logSequenceService;

	@Autowired
	private GetSingleUeLogMapper getSingleUeLogMapper;

	@Override
	public List<SingleUeLogPO> GetSingleUeLog(List<CaseUeVO> listpo, String path) {

		logger.debug("----------进入方法-GetSingleUeLog------------------");

		Map<String, Integer> logSequenceMap = new HashMap<String, Integer>();
		List<SingleUeLogPO> retlist = new ArrayList<SingleUeLogPO>();
		if (listpo == null || listpo.size() == 0) {
			return null;
		}
		try {

			logSequenceMap = getnewpath(listpo);

			logger.debug(" logSequenceMap " + logSequenceMap.size());

			for (CaseUeVO caseuevo : listpo) {

				SingleUeLogPO singleUeLogPO = new SingleUeLogPO();

				// FTP下载最新速率统计
				if (logSequenceMap.containsKey(caseuevo.getUe_id())) {
					singleUeLogPO.setCasename(caseuevo.getCasename());
					singleUeLogPO.setUser_id(caseuevo.getUser_id());
					singleUeLogPO.setUe_id(caseuevo.getUe_id());

					// FTP下载最新速率统计
					if (GetFileName.IsExistfile(path + "/" + caseuevo.getUser_id() + "/" + caseuevo.getCasename() + "/" + caseuevo.getUe_id() + "/" + logSequenceMap.get(caseuevo.getUe_id()), "FTPDownLog.csv")) {

						File file = new File(path + "/" + caseuevo.getUser_id() + "/" + caseuevo.getCasename() + "/" + caseuevo.getUe_id() + "/" + logSequenceMap.get(caseuevo.getUe_id()) + "/" + "FTPDownLog.csv");
						if (!file.exists()) {
							continue;
						} else {
							List<List<String>> datalist = new ArrayList<List<String>>();
							datalist = CSVUtils.importCsv(file);

							if (datalist.size() > 1)
								datalist.remove(0);

							if (datalist.get(datalist.size() - 1) != null && datalist.get(datalist.size() - 1).size() == 7) {
								singleUeLogPO.setFtpdown_speed(Float.parseFloat((datalist.get(datalist.size() - 1).get(4))) / 1024 / 1024 * 8);
								singleUeLogPO.setFtpup_speed(Float.parseFloat((datalist.get(datalist.size() - 1).get(5))) / 1024 / 1024 * 8);
							} else {
								logger.error("Jar csv 文件格式不正确!");
							}

						}
					}
					// FTP上传最新速率统计
					if (GetFileName.IsExistfile(path + "/" + caseuevo.getUser_id() + "/" + caseuevo.getCasename() + "/" + caseuevo.getUe_id() + "/" + logSequenceMap.get(caseuevo.getUe_id()), "FTPUpLog.csv")) {

						File file = new File(path + "/" + caseuevo.getUser_id() + "/" + caseuevo.getCasename() + "/" + caseuevo.getUe_id() + "/" + logSequenceMap.get(caseuevo.getUe_id()) + "/" + "FTPUpLog.csv");
						if (!file.exists()) {
							continue;
						} else {
							List<List<String>> datalist = new ArrayList<List<String>>();
							datalist = CSVUtils.importCsv(file);
							if (datalist.size() > 1)
								datalist.remove(0);

							if (datalist.get(datalist.size() - 1) != null && datalist.get(datalist.size() - 1).size() == 7) {
								singleUeLogPO.setFtpup_speed(Float.parseFloat((datalist.get(datalist.size() - 1).get(4))) / 1024 / 1024 * 8);
							} else {
								logger.error("Jar csv 文件格式不正确!");
							}
						}
					}
					// 电话流量最新速率统计

					if (GetFileName.IsExistfile(path + "/" + caseuevo.getUser_id() + "/" + caseuevo.getCasename() + "/" + caseuevo.getUe_id() + "/" + logSequenceMap.get(caseuevo.getUe_id()), "networklog.csv")) {

						File file = new File(path + "/" + caseuevo.getUser_id() + "/" + caseuevo.getCasename() + "/" + caseuevo.getUe_id() + "/" + logSequenceMap.get(caseuevo.getUe_id()) + "/" + "networklog.csv");
						if (!file.exists()) {
							continue;
						} else {
							List<List<String>> datalist = new ArrayList<List<String>>();
							datalist = CSVUtils.importCsv(file);
							int count = datalist.size() - 1;
							if (count >= 1 && count <= 4) {
								datalist.remove(0);
								singleUeLogPO.setNetworkup_speed(Float.parseFloat((datalist.get(count - 1).get(12))) * 8 / 1024 / 1024);
								singleUeLogPO.setNetworkdown_speed(Float.parseFloat((datalist.get(count - 1).get(13))) * 8 / 1024 / 1024);
							} else if (count >= 5) {
								datalist.remove(0);
								float sumUpFlow = 0;
								float sumDownFlow = 0;
								for (int i = count; i >= count - 4; i--) {
									sumUpFlow += Float.parseFloat(datalist.get(i - 1).get(12));
									sumDownFlow += Float.parseFloat(datalist.get(i - 1).get(13));
								}
								DecimalFormat df = new DecimalFormat("###.000");
								singleUeLogPO.setNetworkup_speed(Float.parseFloat(df.format(sumUpFlow / 5 / 1024 / 1024 * 8)));
								singleUeLogPO.setNetworkdown_speed(Float.parseFloat(df.format(sumDownFlow / 5 / 1024 / 1024 * 8)));
							}
						}
					}

					// 微信文本统计
					if (GetFileName.IsExistfile(path + "/" + caseuevo.getUser_id() + "/" + caseuevo.getCasename() + "/" + caseuevo.getUe_id() + "/" + logSequenceMap.get(caseuevo.getUe_id()), "WeiXinTextLog.csv")) {
						File file = new File(path + "/" + caseuevo.getUser_id() + "/" + caseuevo.getCasename() + "/" + caseuevo.getUe_id() + "/" + logSequenceMap.get(caseuevo.getUe_id()) + "/" + "WeiXinTextLog.csv");
						if (!file.exists()) {
							continue;
						} else {
							List<List<String>> datalist = new ArrayList<List<String>>();
							datalist = CSVUtils.importCsv(file);
							int success_num = 0;
							int fail_num = 0;
							int num = 0;
							int delay = 0;
							List<Integer> delaylist = new ArrayList<Integer>();
							for (List<String> li : datalist) {
								if (li.size() > 4 && li.get(5).equals("Success")) {
									success_num++;
									delaylist.add(Integer.parseInt(li.get(6)));
								}
								if (li.size() > 4 && li.get(5).equals("Fail")) {
									fail_num++;
								}
							}
							for (int i = delaylist.size() - 1; i >= 0 && num < 5; i--, num++) {
								delay += delaylist.get(i);
							}
							singleUeLogPO.setWeixin_text_success(success_num);
							singleUeLogPO.setWeixin_text_fail(fail_num);
							if (num == 0) {
								singleUeLogPO.setWeixin_text_delay(0);
							} else {
								singleUeLogPO.setWeixin_text_delay(delay / num);
							}
							if (success_num + fail_num == 0) {
								singleUeLogPO.setWeixin_text_rate(0);
							} else {
								float rate = (float) (success_num) / (success_num + fail_num);
								DecimalFormat df = new DecimalFormat("0.00");
								singleUeLogPO.setWeixin_text_rate(Float.parseFloat(df.format(rate)));
							}
						}
					}

					// web统计
					if (GetFileName.IsExistfile(path + "/" + caseuevo.getUser_id() + "/" + caseuevo.getCasename() + "/" + caseuevo.getUe_id() + "/" + logSequenceMap.get(caseuevo.getUe_id()), "WebLog.csv")) {
						File file = new File(path + "/" + caseuevo.getUser_id() + "/" + caseuevo.getCasename() + "/" + caseuevo.getUe_id() + "/" + logSequenceMap.get(caseuevo.getUe_id()) + "/" + "WebLog.csv");
						if (!file.exists()) {
							continue;
						} else {
							List<List<String>> datalist = new ArrayList<List<String>>();
							datalist = CSVUtils.importCsv(file);
							int success_num = 0;
							int fail_num = 0;
							int num = 0;
							int delay = 0;
							List<Integer> delaylist = new ArrayList<Integer>();

							// 删除第一列标题
							if (datalist.size() > 0)
								datalist.remove(0);

							for (List<String> li : datalist) {
								if (li.size() > 4 && li.get(5).equals("Success")) {
									success_num++;
									delaylist.add(Integer.parseInt(li.get(6)));
								}
								if (li.size() > 4 && li.get(5).equals("Fail")) {
									fail_num++;
								}
							}
							for (int i = delaylist.size() - 1; i >= 0 && num < 5; i--, num++) {
								delay += delaylist.get(i);
							}
							singleUeLogPO.setWeb_success(success_num);
							singleUeLogPO.setWeb_fail(fail_num);
							if (num == 0) {
								singleUeLogPO.setWeb_delay(0);
							} else {
								singleUeLogPO.setWeb_delay(delay / num);
							}
							if (success_num + fail_num == 0) {
								singleUeLogPO.setWeb_rate(0);
							} else {
								float rate = (float) (success_num) / (success_num + fail_num);
								DecimalFormat df = new DecimalFormat("0.00");
								singleUeLogPO.setWeb_rate(Float.parseFloat(df.format(rate)));
							}
						}
					}

					this.savesingleuelog(singleUeLogPO);
				} else {
					singleUeLogPO = this.getsingleuelogdate(caseuevo);
				}
				if (singleUeLogPO != null) {
					retlist.add(singleUeLogPO);
				}
			}

		} catch (Exception e) {

			logger.error(ExceptionUtil.extractMsg(e));

		}
		return retlist;

	}

	public Map<String, Integer> getnewpath(List<CaseUeVO> caseVOs) {

		Map<String, Integer> logSequenceMap = new HashMap<String, Integer>();

		List<LogSequenceVO> allLogSVO = new ArrayList<LogSequenceVO>();

		for (CaseUeVO caseVo : caseVOs) {

			LogSequenceVO logSequenceVO = new LogSequenceVO();
			logSequenceVO.setCase_file_name(caseVo.getCasename());
			logSequenceVO.setLocation(caseVo.getUe_id());

			try {
				
				LogSequenceVO logS = this.logSequenceService.selectLogSequenceByCaseNameAndLocation(logSequenceVO);
				
				this.logSequenceService.selectnosolveLogSequenceByCaseFileName(logSequenceVO);
				
				allLogSVO.add(logS);

			} catch (SQLException e) {
				logger.error(ExceptionUtil.extractMsg(e));
			}

		}

		logSequenceMap = GetFileName.getLogSequenceMap(allLogSVO);

		return logSequenceMap;

	}

	@Override
	public void savesingleuelog(SingleUeLogPO singleuelogpo) {
		// TODO Auto-generated method stub
		int num = getSingleUeLogMapper.selectsingleuecount(singleuelogpo);
		if (num == 0) {
			getSingleUeLogMapper.insertSingleUeLog(singleuelogpo);
		} else if (num == 1) {
			getSingleUeLogMapper.updateSingleUeLog(singleuelogpo);
		} else {
			System.out.println("出现重复的singleuelog");
		}

	}

	@Override
	public SingleUeLogPO getsingleuelogdate(CaseUeVO caseuevo) {
		// TODO Auto-generated method stub
		return getSingleUeLogMapper.selectsingleuedata(caseuevo);
	}

}
