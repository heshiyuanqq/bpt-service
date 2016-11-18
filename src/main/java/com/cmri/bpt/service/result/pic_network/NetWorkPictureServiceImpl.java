package com.cmri.bpt.service.result.pic_network;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmri.bpt.common.csv.CSVReadToPic;
import com.cmri.bpt.common.entity.NetWorkFlowPicturePO;
import com.cmri.bpt.common.entity.NetWorkPicturePO;
import com.cmri.bpt.service.result.pic_network.mapper.NetWorkPictureMapper;

import edu.emory.mathcs.backport.java.util.Collections;

@Service(value="netWorkPictureServiceImpl")

public class NetWorkPictureServiceImpl implements INetWorkPictureService{
	@Autowired
	private NetWorkPictureMapper netWorkPictureMapper;
	@Override
	public void insertNetWorkPicture(NetWorkPicturePO netWorkPicturePO)
			throws SQLException {
	       netWorkPictureMapper.saveNetWorkPicture(netWorkPicturePO);
		
	}

	@Override
	public List<NetWorkPicturePO> selectNetWorkPicture(String case_name)
			throws SQLException {
		// TODO Auto-generated method stub
		return netWorkPictureMapper.selectNetWorkPicture(case_name);
	}

	@Override
	public List<NetWorkPicturePO> insertnetworkpicturedata(String path,
			String case_name) {
		List<NetWorkPicturePO> retlistnetwork=new ArrayList<NetWorkPicturePO>();
		try{
			retlistnetwork=CSVReadToPic.ReadAllNetWorkLog(path,case_name);
			/*for(int i=0;i<retlistnetwork.size();i++){
				this.insertNetWorkPicture(retlistnetwork.get(i));
			}*/
	
		}catch (Exception e){
			e.printStackTrace();
		}
		return retlistnetwork;
	}

	@Override
	public List<NetWorkFlowPicturePO> insertNetworkFlowdata(String path, String case_name,String name) {
		List<NetWorkFlowPicturePO> listNetworkFlow= new ArrayList<NetWorkFlowPicturePO>();
		Map<Integer, NetWorkFlowPicturePO> networkMap = new HashMap<Integer, NetWorkFlowPicturePO>();
		NetWorkFlowPicturePO netWorkFlowFirst =  null;
		try{
			List<NetWorkFlowPicturePO> listFlow=CSVReadToPic.readNetWorkFlow(path,case_name,name);
		    if (listFlow!=null&&listFlow.size()>0) {
		    	int countTime = 0;
		    	for (NetWorkFlowPicturePO netWorkFlowSecond : listFlow) {
		    		netWorkFlowFirst = new NetWorkFlowPicturePO();
		    		double countUpFlow = 0;
		    		double countDownFlow = 0;
		    		for (NetWorkFlowPicturePO netWorkFlowThird : listFlow) {
						if (netWorkFlowSecond.getX_data()==netWorkFlowThird.getX_data()) {
							countUpFlow += netWorkFlowThird.getY_updata();
							countDownFlow += netWorkFlowThird.getY_downdata();
						}
					}
		    			netWorkFlowFirst.setX_data(netWorkFlowSecond.getX_data());
		    			netWorkFlowFirst.setY_updata(formateNumber(countUpFlow*8/1024/1024));
		    			netWorkFlowFirst.setY_downdata(formateNumber(countDownFlow*8/1024/1024));
		    			networkMap.put(netWorkFlowSecond.getX_data(), netWorkFlowFirst);
		    	}
		    	for (Map.Entry<Integer, NetWorkFlowPicturePO> entry : networkMap.entrySet()) {
					if (entry.getValue()!=null) {
						listNetworkFlow.add(entry.getValue());
					}
				}
		        Collections.sort(listNetworkFlow,new Comparator<NetWorkFlowPicturePO>(){  
		            @Override  
		            public int compare(NetWorkFlowPicturePO b1, NetWorkFlowPicturePO b2) { 
		            	if(b1.getX_data() > b2.getX_data()){
		            		return 1;
		            	}else{
		            		return -1;
		            	}
		            }  
		              
		        }); 
			}
	
		}catch (Exception e){
			e.printStackTrace();
		}
		return listNetworkFlow;
	}
	
	private Float formateNumber(Double number){
		Float newNumber = null;
		if (number != null) {
			DecimalFormat df = new DecimalFormat("#.000");
			newNumber = Float.parseFloat(df.format(number));
		}
		return newNumber;
	}
	}
	


