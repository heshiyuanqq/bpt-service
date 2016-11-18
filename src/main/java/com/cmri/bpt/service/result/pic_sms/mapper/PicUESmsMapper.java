package com.cmri.bpt.service.result.pic_sms.mapper;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.PicUESmsPO;
import com.framework.layer.dao.ibatis.IbatisMapper;
/**
 * @author zzk
 * 手机短信统计图Mapper
 * */
@IbatisMapper
public interface PicUESmsMapper {
	/**
	 * 保存手机短信节点PO
	 * @param picUESmsPO
	 * */
    public void savePicUESms(PicUESmsPO picUESmsPO) throws SQLException;
    /**
     * 根据case_name查询手机短信节点数据。
     * @param case_name
     * @return
     * */
    public List<PicUESmsPO> selectPicUESmsByCaseName(String case_name) throws SQLException;
    
}
