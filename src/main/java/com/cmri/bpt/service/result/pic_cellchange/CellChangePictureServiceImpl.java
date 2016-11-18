package com.cmri.bpt.service.result.pic_cellchange;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmri.bpt.common.csv.CSVReadToPic;
import com.cmri.bpt.common.entity.CellChangePicturePO;
import com.cmri.bpt.common.util.sortMapbykey;
import com.cmri.bpt.service.result.pic_cellchange.mapper.CellChangePictureMapper;
@Service(value = "cellchangePictureServiceImpl")
public class CellChangePictureServiceImpl implements ICellChangePictureService{
	@Autowired
	private CellChangePictureMapper cellchangepicturemapper;
	@Override
	public void insertCellChangePicture(CellChangePicturePO cellChangePicturePO)
			throws SQLException {
		cellchangepicturemapper.saveCellChangePicture(cellChangePicturePO);
		
	}

	@Override
	public List<CellChangePicturePO> selectCellChangePicture(String case_name)
			throws SQLException {
		// TODO Auto-generated method stub
		return cellchangepicturemapper.selectCellChangePicture(case_name);
	}

	@Override
	public List<CellChangePicturePO> insertcellchangepicturedata(String path,
			String case_name) throws Exception {
		List<CellChangePicturePO> retlistcellchange=new ArrayList<CellChangePicturePO>();
		List<List<CellChangePicturePO>> listcellchangelog=new ArrayList<List<CellChangePicturePO>>();
		Map<Integer,Integer> failnum=new HashMap<Integer,Integer>();
		Map<Integer,CellChangePicturePO> retlist=new TreeMap<Integer,CellChangePicturePO>();
		try{
			listcellchangelog=CSVReadToPic.ReadAllCellChangeLog(path,case_name);
			for(int i=0;listcellchangelog!=null&&i<listcellchangelog.size();i++){
				for(int j=0;j<listcellchangelog.get(i).size();j++){
					CellChangePicturePO cellchangelog=new CellChangePicturePO();
					cellchangelog.setX_data(listcellchangelog.get(i).get(j).getX_data());
					if(listcellchangelog.get(i).get(j).getY2_data()!=0){
						if(failnum!=null&& failnum.containsKey(listcellchangelog.get(i).get(j).getX_data())){
							failnum.put(listcellchangelog.get(i).get(j).getX_data(), failnum.get(listcellchangelog.get(i).get(j).getX_data())+1);
						}
						else{
							failnum.put(listcellchangelog.get(i).get(j).getX_data(), 1);
						}
					
					}
				    if(retlist!=null && retlist.containsKey(listcellchangelog.get(i).get(j).getX_data())){				    	
				    	cellchangelog.setY1_data(retlist.get(listcellchangelog.get(i).get(j).getX_data()).getY1_data()+listcellchangelog.get(i).get(j).getY1_data());
				    	cellchangelog.setY2_data(retlist.get(listcellchangelog.get(i).get(j).getX_data()).getY2_data()>listcellchangelog.get(i).get(j).getY2_data()?retlist.get(listcellchangelog.get(i).get(j).getX_data()).getY2_data():listcellchangelog.get(i).get(j).getY2_data());
				        cellchangelog.setY3_data(retlist.get(listcellchangelog.get(i).get(j).getX_data()).getY3_data()<listcellchangelog.get(i).get(j).getY3_data()?retlist.get(listcellchangelog.get(i).get(j).getX_data()).getY3_data():listcellchangelog.get(i).get(j).getY3_data());
				        cellchangelog.setY4_data(retlist.get(listcellchangelog.get(i).get(j).getX_data()).getY4_data()+listcellchangelog.get(i).get(j).getY4_data());	
				    }
				    else{				   
				    	cellchangelog.setY1_data(listcellchangelog.get(i).get(j).getY1_data());
				    	cellchangelog.setY2_data(listcellchangelog.get(i).get(j).getY2_data());
				    	cellchangelog.setY3_data(listcellchangelog.get(i).get(j).getY3_data());
				    	cellchangelog.setY4_data(listcellchangelog.get(i).get(j).getY4_data());
				    }
				    retlist.put(listcellchangelog.get(i).get(j).getX_data(),cellchangelog);
				    
				}
			}
			 if(retlist!=null&&!retlist.isEmpty()){
				 retlist= sortMapbykey.sortCellChangeMapByKey(retlist);
				 CellChangePicturePO cellchangepo=null;
				 Long min=(long) 0;
				 min=CSVReadToPic.Getmintimefromlog(path,"networklog.csv");
				 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				 String start_time = sdf.format(new Date(min));
				 for(Map.Entry<Integer,CellChangePicturePO> entry:retlist.entrySet()){    
					 cellchangepo=new CellChangePicturePO();
					 cellchangepo.setX_data(entry.getValue().getX_data());
					 cellchangepo.setCase_name(case_name);
					 cellchangepo.setStart_time(start_time);
					 cellchangepo.setY1_data(entry.getValue().getY1_data());
                     DecimalFormat df = new DecimalFormat("###.0");

					 if(failnum!=null&& failnum.containsKey(cellchangepo.getX_data())){
						 cellchangepo.setY4_data(Float.parseFloat(df.format(entry.getValue().getY3_data()/failnum.get(cellchangepo.getX_data())/1000)));
	                     cellchangepo.setY2_data(Float.parseFloat(df.format(entry.getValue().getY2_data()/1000)));
	                     cellchangepo.setY3_data(Float.parseFloat(df.format(entry.getValue().getY3_data()/1000))); 
						
					 }
					 else{
						 cellchangepo.setY2_data(-1);
						 cellchangepo.setY3_data(-1);
						 cellchangepo.setY4_data(-1);
					 }
					  //this.insertCellChangePicture(cellchangepo);
					  retlistcellchange.add(cellchangepo);
					   
					}   
			 }
			 else{
				 retlistcellchange=null; 
			 }
			 
		}catch (Exception e){
			e.printStackTrace();
		}
		return retlistcellchange;
	}


}
