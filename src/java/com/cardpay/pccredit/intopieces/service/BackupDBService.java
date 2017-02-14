package com.cardpay.pccredit.intopieces.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentBatch;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentDetail;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentList;
import com.cardpay.pccredit.intopieces.service.UploadDeamonService.UploadThread;
import com.sunyard.TransEngine.exception.SunTransEngineException;

/**
 * java 程序 导入导出数据库 做到程序定时 ( 结合 quartz,log) 自动备份，防止数据丢失
 * 
 * @author leiwei 2011 - 12 - 1
 * 
 */
@Service
public class BackupDBService {

	public static final Logger logger = Logger.getLogger(UploadDeamonService.class);

	@Autowired
	private ThreadPoolTaskExecutor dmpDB;
	
	Runtime runtime = Runtime.getRuntime();
	Process process = null;
	boolean isSuccess = false;

	/**
	 * 备份、还原 oracle 数据库的方法
	 * 
	 * @param cmdStr
	 *            备份命令 ( 即导出 )
	 */
	public boolean backupORreductionOracleDB(String cmdStr) {
		try {
			logger.info("开始备份");
			process = runtime.exec(cmdStr);
			isSuccess = true;
			logger.info("备份完成");
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		}
		return isSuccess;
	}

	// 上传
	public void doDmp(String cmdStr) {
		// 前一次任务未完跳过本次
		logger.info("getActiveCount():" + dmpDB.getActiveCount());
		if (dmpDB.getActiveCount() > 0) {
			return;
		}
		
		try {
			dmpDB.execute(new dmpDBThread(cmdStr));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("备份任务执行出错:", e);
		}
	}

	// 上传线程
	public class dmpDBThread implements Runnable {
		String cmdStr;

		public dmpDBThread(String cmdStr) {
			this.cmdStr = cmdStr;
		}

		@Override
		public void run() {
			backupORreductionOracleDB(cmdStr);
		}
	}

	/**
	 * 
	 * 测试
	 */
	public static void main(String[] args) {
		// oracle 备份、还原命令
		String backupCmd = "exp scott/tiger@172.18.27.146/lworcl file=d:/javaDB.dmp full=y";
		boolean reSuccess = new BackupDBService().backupORreductionOracleDB(backupCmd);
		System.out.println(reSuccess);
	}
}
