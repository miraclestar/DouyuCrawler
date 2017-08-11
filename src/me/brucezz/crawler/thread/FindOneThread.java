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

	String lastUserName = "firstone";
	
	@Override
	public void run() {
		
		LogUtil.i("find one to talk");
		Boolean getOne =false;
		while(true) {
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// check whether user is submit one and write into file;
			Danmaku danmaku = DanmakuDao.queryDanmaku("SELECT * FROM danmaku where snick='"+lastUserName+ "' and TIMESTAMPDIFF(SECOND,`date`,NOW())<"+(interval/1000)+" order by _id desc limit 1");
			
			if(danmaku.getSnick() == null || danmaku.getSnick().isEmpty()) {
				if(getOne) {
					LogUtil.i("--用户["+lastUserName+"] 30秒内没有发续写\n");
					lastUserName = danmaku.getSnick();
				}
				
			}else {
				try {
					fWriter.write(danmaku.getContent()+"\n");
					fWriter.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				LogUtil.i("用户 : " + lastUserName +", 说: "+ danmaku.getContent()); 
			}
			//find another one to show;
			Danmaku dan = DanmakuDao.queryDanmaku("SELECT * FROM danmaku where TIMESTAMPDIFF(SECOND,`date`,NOW())<"+(interval/1000)+" limit 1");
			if(dan!=null) {
				lastUserName  = dan.getSnick();
				if(lastUserName==null || lastUserName.equals("")) {
					LogUtil.i("没有抽取到观众\n");
					getOne = false;
				}else {
					LogUtil.i("请【"+lastUserName+"】在10秒内发言\n");
					getOne = true;
				}				
			}
		}
		
	}
	
	
	
}
