package com.cmri.bpt.service.result.pic_sms;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmri.bpt.common.csv.CSVReadToPic;
import com.cmri.bpt.common.entity.LogSequenceVO;
import com.cmri.bpt.common.entity.PicUESmsPO;
import com.cmri.bpt.common.util.PicUESmsPOComparator;
import com.cmri.bpt.service.result.pic_sms.mapper.PicUESmsMapper;

/**
 * @author zzk 手机短信节点信息服务接口实现类
 * */
@Service(value = "picUESmsServiceImpl")
public class PicUESmsServiceImpl implements IPicUESmsService {
	@Autowired
	private PicUESmsMapper picUESmsMapper;

	@Transactional
	@Override
	public void savePicUESms(PicUESmsPO picUESmsPO) throws SQLException {
		this.picUESmsMapper.savePicUESms(picUESmsPO);
	}

	@Override
	public List<PicUESmsPO> selectPicUESmsByCaseName(String case_name)
			throws SQLException {

		return this.picUESmsMapper.selectPicUESmsByCaseName(case_name);
	}

	@Transactional
	@Override
	public List<PicUESmsPO> insertPicUESmsData(String path, String case_name) {

		List<PicUESmsPO> picUESmsList=new ArrayList<PicUESmsPO>();
		try{
			CSVReadToPic.readAllUESmsLogsByCaseName(path, case_name, picUESmsList);
			//按照ue_location排序
			Collections.sort(picUESmsList,new PicUESmsPOComparator());
			//查询最小时间填充
			Iterator<PicUESmsPO> it_mintime = picUESmsList.iterator();
			
			Long min_time = System.currentTimeMillis();
			while(it_mintime.hasNext()){
				
				PicUESmsPO picUESmsPO = it_mintime.next();
				if(min_time > picUESmsPO.getTime_stamp()){
					min_time = picUESmsPO.getTime_stamp();
				}
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String start_time = sdf.format(new Date(min_time));
			
			//处理x,y
			int x = 0;
			String ue_location = "";
			Iterator<PicUESmsPO> it_xy = picUESmsList.iterator();
			while(it_xy.hasNext()){
				PicUESmsPO picUESmsPO = it_xy.next();
				if(!ue_location.equals(picUESmsPO.getUe_location())){
					ue_location = picUESmsPO.getUe_location();
					x++;
				}
				
				Long y = (picUESmsPO.getTime_stamp() - min_time)/1000;
				picUESmsPO.setX_data(x);
				picUESmsPO.setY_data(Integer.parseInt(y.toString()));
				picUESmsPO.setStart_time(start_time);
			}
			
			//存储
			/*for(int i=0;i<picUESmsList.size();i++){
				this.savePicUESms(picUESmsList.get(i));
			}*/
	
		}catch (Exception e){
			e.printStackTrace();
		}
		return picUESmsList;
	}
	
	@Transactional
	@Override
	public List<PicUESmsPO> createPicUESmsData(String path, String case_name, List<LogSequenceVO> logSequenceVOs) {

		List<PicUESmsPO> picUESmsList=new ArrayList<PicUESmsPO>();
		try{
			CSVReadToPic.readAllUESmsLogsByCaseName(path, case_name, picUESmsList, logSequenceVOs);
			//按照ue_location排序
			Collections.sort(picUESmsList,new PicUESmsPOComparator());
			//查询最小时间填充
			Iterator<PicUESmsPO> it_mintime = picUESmsList.iterator();
			
			Long min_time = System.currentTimeMillis();
			while(it_mintime.hasNext()){
				
				PicUESmsPO picUESmsPO = it_mintime.next();
				if(min_time > picUESmsPO.getTime_stamp()){
					min_time = picUESmsPO.getTime_stamp();
				}
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String start_time = sdf.format(new Date(min_time));
			
			//处理x,y
			int x = 0;
			String ue_location = "";
			Iterator<PicUESmsPO> it_xy = picUESmsList.iterator();
			while(it_xy.hasNext()){
				PicUESmsPO picUESmsPO = it_xy.next();
				if(!ue_location.equals(picUESmsPO.getUe_location())){
					ue_location = picUESmsPO.getUe_location();
					x++;
				}
				
				Long y = (picUESmsPO.getTime_stamp() - min_time)/1000;
				picUESmsPO.setX_data(x);
				picUESmsPO.setY_data(Integer.parseInt(y.toString()));
				picUESmsPO.setStart_time(start_time);
			}
			
	
		}catch (Exception e){
			e.printStackTrace();
		}
		return picUESmsList;
	}


}
