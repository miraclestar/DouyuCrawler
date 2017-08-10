package me.brucezz.crawler.db;

import me.brucezz.crawler.bean.Danmaku;
import me.brucezz.crawler.util.DBUtil;
import me.brucezz.crawler.util.DateUtil;
import me.brucezz.crawler.util.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brucezz on 2016/01/06.
 * DouyuCrawler
 */
public class DanmakuDao {

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS Danmaku(_id INT PRIMARY KEY AUTO_INCREMENT,uid INT NOT NULL,snick VARCHAR(64) NOT NULL,content VARCHAR(256) NOT NULL,date DATETIME NOT NULL, rid INT NOT NULL );";

    private static final String SQL_INSERT_DANMAKU = "INSERT INTO Danmaku(uid,snick, content, date,rid) VALUES(%d, '%s', '%s', '%s', %d) ";

    public static void createTable() {
    	LogUtil.i("create table");
        DBUtil.execSQL(SQL_CREATE_TABLE);
    }

    /**
     * 保存弹幕数据到数据库
     */
    public static boolean saveDanmaku(List<Danmaku> danmakuList) {
        List<String> sqlList = new ArrayList<>();
        for (Danmaku danmaku : danmakuList) {
            sqlList.add(String.format(
                    SQL_INSERT_DANMAKU,
                    danmaku.getUid(),
                    danmaku.getSnick(),
                    danmaku.getContent(),
                    DateUtil.datetime(danmaku.getDate()),
                    danmaku.getRid())
            );
        }
        //LogUtil.i("save danmu");
        return DBUtil.execSQL(sqlList);
    }
    
    /**
     * 
     * @param args
     */
    public static Danmaku queryDanmaku(String sql) {
    	Danmaku ret = new Danmaku();
    	Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ret.setUid(rs.getInt("uid"));
				ret.setContent(rs.getString("content"));
				ret.setDate(rs.getDate("date"));
				ret.setSnick(rs.getString("snick"));
				ret.setRid(rs.getInt("rid"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(e.getMessage());
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
    	
    	return ret;
    }
    

    public static void main(String[] args) {
        //测试
    	
    	//createTable();
    	
    	Danmaku d = queryDanmaku("select * from danmaku order by id desc limit 1");
    	LogUtil.i(d.getContent()+" : "+d.getSnick());
    	
        List<Danmaku> danmakus = new ArrayList<>();
        danmakus.add(new Danmaku(99999999, "X", "X", 9999));
        danmakus.add(new Danmaku(99999999, "XX", "XX", 9999));
        danmakus.add(new Danmaku(99999999, "XX", "XX", 9999));

        saveDanmaku(danmakus);
    }
}
