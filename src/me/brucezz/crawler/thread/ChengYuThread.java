package me.brucezz.crawler.thread;

import java.io.FileWriter;
import java.io.IOException;
import java.security.acl.LastOwnerException;
import java.util.List;

import com.mysql.jdbc.log.Log;

import me.brucezz.crawler.bean.Danmaku;
import me.brucezz.crawler.db.DanmakuDao;
import me.brucezz.crawler.util.LogUtil;
import me.brucezz.crawler.util.Spell;

public class ChengYuThread implements Runnable {

	int interval = 3000;// 30000;//30秒
	int lastInterval = 20;//20秒

	String firstPhrase = "ju";

	private FileWriter fWriter;

	public ChengYuThread() {
		try {
			fWriter = new FileWriter("Phrase.txt", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	String lastUserName = "firstone";

	String firstPinyin = "";
	String LastWord = "";

	@Override
	public void run() {

		LogUtil.i("ChengYuThread");
		while (true) {
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			LogUtil.i("第一个字音："+firstPhrase);
			// check whether user is submit one and write into file;
			List<Danmaku> danmakus = DanmakuDao
					.queryDanmakus("SELECT * FROM danmaku where TIMESTAMPDIFF(SECOND,`date`,NOW())<"+lastInterval+" order by _id desc");
			for (Danmaku danmaku : danmakus) {
				try {
					if (danmaku.getContent().length() != 4) {
						LogUtil.i("length is not 4: " + danmaku.getContent().length());
						continue;
					}
					firstPinyin = Spell.converterToSpell(danmaku.getContent(), ",");
					LogUtil.d("converted pinyin : " + firstPinyin);
					
					if (firstPhrase.equals(firstPinyin.split(",")[0])) {
						
						if(LastWord.equals(danmaku.getContent())) {
							LogUtil.i("同样的词:"+LastWord);
							continue;
						}
						firstPhrase = firstPinyin.split(",")[firstPinyin.split(",").length - 1];
						LogUtil.i("----------------------------:right:: " + danmaku.getContent() + " ,by: "
								+ danmaku.getSnick() + ",first phrase: " + firstPhrase);
						try {
							LastWord = danmaku.getContent();
							fWriter.write(danmaku.getContent() + " ,by: "
									+ danmaku.getSnick() + "\n");
							fWriter.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				} catch (Exception e) {
					// TODO: handle exception
					LogUtil.e(e.getMessage());
					e.printStackTrace();
				}
			}

		}

	}

}
