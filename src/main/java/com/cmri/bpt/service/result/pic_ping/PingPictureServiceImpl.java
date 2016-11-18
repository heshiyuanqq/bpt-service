package com.cmri.bpt.service.result.pic_ping;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmri.bpt.common.csv.CSVReadToPic;
import com.cmri.bpt.common.entity.CallLogPO;
import com.cmri.bpt.common.entity.PingPicturePO;
import com.cmri.bpt.common.util.sortMapbykey;
import com.cmri.bpt.service.result.pic_ping.mapper.PingPictureMapper;
@Service(value="pingPictureServiceImpl")
public class PingPictureServiceImpl implements IPingPictureService{
	@Autowired
	private PingPictureMapper pingPictureMapper;
	@Override
	public void insertPingPicture(PingPicturePO pingPicturePO)
			throws SQLException {
		 pingPictureMapper.savePingPicture(pingPicturePO);
		
	}



	@Override
	public List<PingPicturePO> selectPingPicture(String case_name)
			throws SQLException {
		// TODO Auto-generated method stub
		return pingPictureMapper.selectPingPicture(case_name);
	}



	@Override
	public List<PingPicturePO> insertpingpicturedata(String path,
			String case_name) throws Exception {

		List<PingPicturePO> retlistping=new ArrayList<PingPicturePO>();
		List<List<CallLogPO>> listpinglog=new ArrayList<List<CallLogPO>>();
		Map<Integer,CallLogPO> retlist=new TreeMap<Integer,CallLogPO>();
		try{
			listpinglog=CSVReadToPic.ReadAllPingLog(path);
			for(int i=0;listpinglog!=null&&i<listpinglog.size();i++){
				for(int j=0;j<listpinglog.get(i).size();j++){
					CallLogPO calllog=new CallLogPO();
				    calllog.setTime(listpinglog.get(i).get(j).getTime());
				    if(retlist!=null && retlist.containsKey(listpinglog.get(i).get(j).getTime())){				    	
				    	calllog.setFail_num(retlist.get(listpinglog.get(i).get(j).getTime()).getFail_num()+listpinglog.get(i).get(j).getFail_num());
				    	calllog.setSuccess_num( retlist.get(listpinglog.get(i).get(j).getTime()).getSuccess_num()+listpinglog.get(i).get(j).getSuccess_num());
				    	calllog.setDelay_time( retlist.get(listpinglog.get(i).get(j).getTime()).getDelay_time()+listpinglog.get(i).get(j).getDelay_time());
				    }
				    else{				   
				    	calllog.setFail_num(listpinglog.get(i).get(j).getFail_num());
				    	calllog.setSuccess_num(listpinglog.get(i).get(j).getSuccess_num());
				    	calllog.setDelay_time(listpinglog.get(i).get(j).getDelay_time());
				    }
				    retlist.put(listpinglog.get(i).get(j).getTime(),calllog);
				    
				}
			}
			 if(retlist!=null&&!retlist.isEmpty()){
				 retlist= sortMapbykey.sortMapByKey(retlist);
				 PingPicturePO pingpo=null;
				 Long min=(long) 0;
				 min=CSVReadToPic.Getmintimefromlog(path,"ping.csv");
				 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				 String start_time = sdf.format(new Date(min));
				 for(Map.Entry<Integer,CallLogPO> entry:retlist.entrySet()){    
					 pingpo=new PingPicturePO();
					 pingpo.setX_data(entry.getValue().getTime());
					 pingpo.setCase_name(case_name);
					 float rate=(float)entry.getValue().getSuccess_num()/(entry.getValue().getSuccess_num()+entry.getValue().getFail_num());
					 DecimalFormat df=new DecimalFormat("0.00");
					 pingpo.setY2_data(Float.parseFloat(df.format(rate))*100);
					 pingpo.setY1_data(entry.getValue().getFail_num());
					 pingpo.setStart_time(start_time);
					 if(entry.getValue().getSuccess_num()==0){
						 pingpo.setY3_data(-1);
					 }
					 else{
					    pingpo.setY3_data(entry.getValue().getDelay_time()/entry.getValue().getSuccess_num());
					 }
					 // this.insertPingPicture(pingpo);
					   retlistping.add(pingpo);
					   
					}   
			 }
			 else{
				 retlistping=null; 
			 }
		}catch (Exception e){
			e.printStackTrace();
		}
		return retlistping;
	}
	
	
	

}
