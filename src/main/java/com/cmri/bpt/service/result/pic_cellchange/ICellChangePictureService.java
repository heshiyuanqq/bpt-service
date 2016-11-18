package com.cmri.bpt.service.result.pic_cellchange;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.CellChangePicturePO;


public interface ICellChangePictureService {
	   public void insertCellChangePicture(CellChangePicturePO cellChangePicturePO) throws SQLException;
	   public List<CellChangePicturePO> selectCellChangePicture(String case_name) throws SQLException;
	   public List<CellChangePicturePO> insertcellchangepicturedata(String path,String case_name) throws Exception;
}
