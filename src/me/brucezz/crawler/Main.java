package me.brucezz.crawler;

import java.util.Set;

import me.brucezz.crawler.config.Config;
import me.brucezz.crawler.thread.CrawlerThread;

/**
 * @author hyliu
 * @date 2016-6-18
 */
public class Main {
    public static void main(String[] args) {
        if (!Config.loadSuccess) return;

        Set<String> nameSet = Config.ROOM_MAP.keySet();

        for (String name : nameSet) {
            new Thread(new CrawlerThread(name, Config.ROOM_MAP.get(name)), "Crawler-"+name).start();
        }
    }
}
