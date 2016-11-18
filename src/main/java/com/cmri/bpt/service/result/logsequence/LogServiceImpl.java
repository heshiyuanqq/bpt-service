package com.cmri.bpt.service.result.logsequence;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cmri.bpt.common.util.ZipUtil;

@Service(value = "logServiceImpl")
public class LogServiceImpl implements ILogService {

	static Logger logger = Logger.getLogger(LogServiceImpl.class);

	@Override
	public void receiveLogAndUnzip(MultipartFile file, String filePath, String decompressDir) {
		// 判断路径是否为空
		File parentDir = new File(decompressDir);
		if (parentDir != null && !parentDir.exists()) {
			parentDir.mkdirs();
		}
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				// 转存文件
				file.transferTo(new File(filePath));
				ZipUtil.readByApacheZipFile(filePath, decompressDir);
			} catch (Exception e) {

				logger.error(e.getMessage());
				logger.error(e.getStackTrace());
				
			}
		}
	}
}
