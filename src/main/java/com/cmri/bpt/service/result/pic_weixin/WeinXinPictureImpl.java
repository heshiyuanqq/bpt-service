package com.cmri.bpt.service.result.pic_weixin;

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
import com.cmri.bpt.common.entity.WeiXinPicturePO;
import com.cmri.bpt.common.entity.WeiXinTypePO;
import com.cmri.bpt.common.util.sortMapbykey;
import com.cmri.bpt.service.result.pic_weixin.mapper.WeiXinPictureMapper;



@Service(value="weixinPictureServiceImpl")
public class WeinXinPictureImpl implements IWeiXinPictureService{
	@Autowired
	private WeiXinPictureMapper weixinpicturemapper;
	@Override
	public void insertWeiXinPicture(WeiXinPicturePO weixinPicturePO)
			throws SQLException {
	  weixinpicturemapper.saveWeiXinPicture(weixinPicturePO);
		
	}



	@Override
	public List<WeiXinPicturePO> insertweixinpicturedata(String path,
			String case_name, String type) throws Exception {
		// TODO Auto-generated method stub
		List<WeiXinPicturePO> retlistweixin=new ArrayList<WeiXinPicturePO>();
		List<List<WeiXinPicturePO>> listweixinlog=new ArrayList<List<WeiXinPicturePO>>();
		Map<Integer,WeiXinPicturePO> retlist=new TreeMap<Integer,WeiXinPicturePO>();
		Float delay_time=(float)0;
		try{
			listweixinlog=CSVReadToPic.ReadAllWeiXinLog(path,type);
			for(int i=0;listweixinlog!=null&&i<listweixinlog.size();i++){
				for(int j=0;j<listweixinlog.get(i).size();j++){
					WeiXinPicturePO weixinlog=new WeiXinPicturePO();
					weixinlog.setX_data(listweixinlog.get(i).get(j).getX_data());
					weixinlog.setType(listweixinlog.get(i).get(j).getType());
				    if(retlist!=null && retlist.containsKey(listweixinlog.get(i).get(j).getX_data())){				    	
				    	weixinlog.setY1_data(retlist.get(listweixinlog.get(i).get(j).getX_data()).getY1_data()+listweixinlog.get(i).get(j).getY1_data());
				    	weixinlog.setSucess_num( retlist.get(listweixinlog.get(i).get(j).getX_data()).getSucess_num()+listweixinlog.get(i).get(j).getSucess_num());
				    	weixinlog.setY3_data( retlist.get(listweixinlog.get(i).get(j).getX_data()).getY3_data()+listweixinlog.get(i).get(j).getY3_data());
				    }
				    else{				   
				    	weixinlog.setY1_data(listweixinlog.get(i).get(j).getY1_data());
				    	weixinlog.setSucess_num(listweixinlog.get(i).get(j).getSucess_num());
				    	weixinlog.setY3_data(listweixinlog.get(i).get(j).getY3_data());
				    }
				    retlist.put(listweixinlog.get(i).get(j).getX_data(),weixinlog);
				    
				}
			}
			 if(retlist!=null&&!retlist.isEmpty()){
				 retlist= sortMapbykey.sortWeiXinMapByKey(retlist);
				 WeiXinPicturePO weixinpo=null;
				 Long min=(long) 0;
				 min=CSVReadToPic.Getmintimefromlog(path,type);
				 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				 String start_time = sdf.format(new Date(min));
				 for(Map.Entry<Integer,WeiXinPicturePO> entry:retlist.entrySet()){    
					 weixinpo=new WeiXinPicturePO();
					 weixinpo.setX_data(entry.getValue().getX_data());
					 weixinpo.setCase_name(case_name);
					 float rate=(float)entry.getValue().getSucess_num()/(entry.getValue().getSucess_num()+entry.getValue().getY1_data());
					 DecimalFormat df=new DecimalFormat("0.00");
					 weixinpo.setY2_data(Float.parseFloat(df.format(rate))*100);
					 weixinpo.setY1_data(entry.getValue().getY1_data());
					 weixinpo.setStart_time(start_time);
					 weixinpo.setType(type);
					 if(entry.getValue().getSucess_num()==0){
						 weixinpo.setY3_data(-1);
					 }
					 else{
						// DecimalFormat df1=new DecimalFormat("0.000");
						 delay_time=(((Float)entry.getValue().getY3_data())/entry.getValue().getSucess_num())/1000;
						// weixinpo.setY3_data(Float.parseFloat(df1.format(delay_time)));
						 weixinpo.setY3_data(delay_time);
					 }
					  //this.insertWeiXinPicture(weixinpo);
					   retlistweixin.add(weixinpo);
					   
					}   
			 }
			 else{
				 retlistweixin=null; 
			 }
			 
		}catch (Exception e){
			e.printStackTrace();
		}
		return retlistweixin;
	}



	@Override
	public List<WeiXinPicturePO> selectWeiXinPicture(WeiXinTypePO weixinTypePO)
			throws SQLException {
		// TODO Auto-generated method stub
		return weixinpicturemapper.selectWeiXinPicture(weixinTypePO);
	}

}
