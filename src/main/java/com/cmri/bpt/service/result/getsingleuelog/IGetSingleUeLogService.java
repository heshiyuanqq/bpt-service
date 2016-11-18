package com.cmri.bpt.service.result.getsingleuelog;

import java.util.List;

import com.cmri.bpt.common.entity.CaseUeVO;
import com.cmri.bpt.common.entity.SingleUeLogPO;

public interface IGetSingleUeLogService {
  public List<SingleUeLogPO> GetSingleUeLog(List<CaseUeVO> listpo,String path);
  public void savesingleuelog(SingleUeLogPO singleuelogpo);
  public SingleUeLogPO getsingleuelogdate(CaseUeVO caseuevo);
  
}
