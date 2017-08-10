package me.brucezz.crawler.thread;

import java.io.FileWriter;
import java.io.IOException;

import me.brucezz.crawler.bean.Danmaku;
import me.brucezz.crawler.db.DanmakuDao;
import me.brucezz.crawler.util.LogUtil;

public class FindOneThread implements Runnable{

	
	int interval = 30000;//30000;//30秒
	
	private FileWriter fWriter;
	public FindOneThread() {
		 try {
             fWriter = new FileWriter("noble.txt", true);
         } catch (IOException e) {
             e.printStackTrace();
         }
	}

	String lastUserName = null;
	
	@Override
	public void run() {
		
		LogUtil.i("find one to talk");
		while(true) {
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// check whether user is submit one and write into file;
			Danmaku danmaku = DanmakuDao.queryDanmaku("SELECT * FROM danmaku where snick='"+lastUserName+ "' order by _id desc limit 1");
			lastUserName = danmaku.getSnick();
			if(lastUserName ==null || lastUserName.isEmpty()) {
				LogUtil.i("user say nothing");
				
			}else {
				try {
					fWriter.write(danmaku.getContent());
					fWriter.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				LogUtil.i("user : " + lastUserName +", said "+ danmaku.getContent()); 
			}
			//find another one to show;
			Danmaku dan = DanmakuDao.queryDanmaku("SELECT * FROM danmaku order by _id desc limit 1");
			if(dan!=null) {
				lastUserName  = dan.getSnick();
				LogUtil.i("请【"+lastUserName+"】在30秒内发送一条小说续写的弹幕");				
			}
		}
		
	}
	
	
	
}
