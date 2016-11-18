package com.cmri.bpt.service.result.logsequence;

import org.springframework.web.multipart.MultipartFile;

public interface ILogService {
	/**
	 * 接收log并解压
	 * @param file 接收文件
	 * @param filePath 转存文件路径
	 * @param decompressDir 解压路径
	 */
    public void receiveLogAndUnzip(MultipartFile file,String filePath,String decompressDir);
}
