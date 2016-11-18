package com.cmri.bpt.service.result.pic_weixin.mapper;
import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.WeiXinPicturePO;
import com.cmri.bpt.common.entity.WeiXinTypePO;
import com.framework.layer.dao.ibatis.IbatisMapper;
@IbatisMapper
public interface WeiXinPictureMapper {
	public void saveWeiXinPicture(WeiXinPicturePO weixinPicturePO) throws SQLException;
    public List<WeiXinPicturePO> selectWeiXinPicture(WeiXinTypePO weixinTypePO) throws SQLException;
}

