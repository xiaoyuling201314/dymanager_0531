package com.dayuan.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

public class BatchInsert {
	      /**
	      * 批量提交数据
	      * @param sqlSessionFactory
	       * @param mybatisSQLId SQL语句在Mapper XML文件中的ID
	      * @param commitCountEveryTime 每次提交的记录数
	       * @param list 要提交的数据列表
	       * @param logger 日志记录器
	       */
	      public  <T> int batchCommit(SqlSessionFactory sqlSessionFactory, String mybatisSQLId, int commitCountEveryTime, List<T> list, Logger logger) {
	        SqlSession session = null;
	        int state=0;
	         try {
	             session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
	             int commitCount = (int) Math.ceil(list.size() / (double) commitCountEveryTime);
	             List<T> tempList = new ArrayList<T>(commitCountEveryTime);
	            int start, stop;
	             Long startTime = System.currentTimeMillis();
	             for (int i = 0; i < commitCount; i++) {
	                 tempList.clear();
	                 start = i * commitCountEveryTime;
	                 stop = Math.min(i * commitCountEveryTime + commitCountEveryTime - 1, list.size() - 1);
	                 for (int j = start; j <= stop; j++) {
	                    tempList.add(list.get(j));
	                }
	                 session.insert(mybatisSQLId, tempList);
	                 session.commit();
	                 session.clearCache();
	            }
	            Long endTime = System.currentTimeMillis();
	            logger.debug("batchCommit耗时：" + (endTime - startTime) + "毫秒");
	         } catch (Exception e) {
	        	 state=-1;
	             logger.error("batchCommit error!", e);
	             e.printStackTrace();
	             session.rollback();
	         } finally {
	             if (session != null) {
	                 session.close();
	             }
	         }
	         return state;
	     }
	 }
