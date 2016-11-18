package com.cmri.bpt.service.result.pic_web;

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
import com.cmri.bpt.common.entity.WebPicturePO;
import com.cmri.bpt.common.util.sortMapbykey;
import com.cmri.bpt.service.result.pic_web.mapper.WebPictureMapper;


@Service(value="webPictureServiceImpl")
public class WebPictureServiceImpl implements IWebPictureService{
	@Autowired
	private WebPictureMapper webpicturemapper;
	@Override
	public void insertWebPicture(WebPicturePO webPicturePO) throws SQLException {
		 webpicturemapper.saveWebPicture(webPicturePO);
		
	}

	@Override
	public List<WebPicturePO> selectWebPicture(String case_name)
			throws SQLException {
		// TODO Auto-generated method stub
		return webpicturemapper.selectWebPicture(case_name);
	}

	@Override
	public List<WebPicturePO> insertwebpicturedata(String path, String case_name)
			throws Exception {
		List<WebPicturePO> retlistweb=new ArrayList<WebPicturePO>();
		List<List<WebPicturePO>> listweblog=new ArrayList<List<WebPicturePO>>();
		Map<Integer,WebPicturePO> retlist=new TreeMap<Integer,WebPicturePO>();
		Float delay_time=(float) 0;
		try{
			listweblog=CSVReadToPic.ReadAllWebLog(path);
			for(int i=0;listweblog!=null&&i<listweblog.size();i++){
				for(int j=0;j<listweblog.get(i).size();j++){
					WebPicturePO weblog=new WebPicturePO();
				    weblog.setX_data(listweblog.get(i).get(j).getX_data());
				    if(retlist!=null && retlist.containsKey(listweblog.get(i).get(j).getX_data())){				    	
				    	weblog.setY1_data(retlist.get(listweblog.get(i).get(j).getX_data()).getY1_data()+listweblog.get(i).get(j).getY1_data());
				    	weblog.setSucess_num( retlist.get(listweblog.get(i).get(j).getX_data()).getSucess_num()+listweblog.get(i).get(j).getSucess_num());
				    	weblog.setY3_data( retlist.get(listweblog.get(i).get(j).getX_data()).getY3_data()+listweblog.get(i).get(j).getY3_data());
				    }
				    else{				   
				    	weblog.setY1_data(listweblog.get(i).get(j).getY1_data());
				    	weblog.setSucess_num(listweblog.get(i).get(j).getSucess_num());
				    	weblog.setY3_data(listweblog.get(i).get(j).getY3_data());
				    }
				    retlist.put(listweblog.get(i).get(j).getX_data(),weblog);
				    
				}
			}
			 if(retlist!=null&&!retlist.isEmpty()){
				 retlist= sortMapbykey.sortWebMapByKey(retlist);
				 WebPicturePO webpo=null;
				 Long min=(long) 0;
				 min=CSVReadToPic.Getmintimefromlog(path,"WebLog.csv");
				 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				 String start_time = sdf.format(new Date(min));
				 for(Map.Entry<Integer,WebPicturePO> entry:retlist.entrySet()){    
					 webpo=new WebPicturePO();
					 webpo.setX_data(entry.getValue().getX_data());
					 webpo.setCase_name(case_name);
					 float rate=(float)entry.getValue().getSucess_num()/(entry.getValue().getSucess_num()+entry.getValue().getY1_data());
					 DecimalFormat df=new DecimalFormat("0.00");
					 webpo.setY2_data(Float.parseFloat(df.format(rate))*100);
					 webpo.setY1_data(entry.getValue().getY1_data());
					 webpo.setStart_time(start_time);
					 if(entry.getValue().getSucess_num()==0){
						 webpo.setY3_data(-1);
					 }
					 else{
						/* DecimalFormat df1=new DecimalFormat("0.000");
						 delay_time=(((Float)entry.getValue().getY3_data())/entry.getValue().getSucess_num())/1000;
						 webpo.setY3_data(Float.parseFloat(df1.format(delay_time)));*/
						 delay_time=(((Float)entry.getValue().getY3_data())/entry.getValue().getSucess_num())/1000;
						 webpo.setY3_data(delay_time);
					 }
					 // this.insertWebPicture(webpo);
					   retlistweb.add(webpo);
					   
					}   
			 }
			 else{
				 retlistweb=null; 
			 }
			 
		}catch (Exception e){
			e.printStackTrace();
		}
		return retlistweb;
	
	}

}
