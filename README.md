# DouyuCrawler
fork 的一个 Java 程序，用于抓取斗鱼弹幕等数据。

# 思路介绍

可以看原作的博客：[抓取斗鱼直播弹幕 - Brucezz's Blog](http://brucezz.github.io/articles/2016/01/11/douyu-crawler/)

# 用法

程序入口为 `me.brucezz.crawler.Main`

需要自己配置 `conf.properties` 文件：

- `debug` ： 决定是否显示详细的调试信息
- `db.X` ： 数据库（MySQL）相关设置
- `room.url.X` ： 抓取房间列表， 可同时抓取多个房间



