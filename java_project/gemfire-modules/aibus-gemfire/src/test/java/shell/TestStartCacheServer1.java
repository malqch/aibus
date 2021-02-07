package shell;

import org.kohsuke.args4j.CmdLineException;

import java.io.IOException;


/**
* @ClassName StartLocalCacheServer
* @Description 在本地启动一个cache server节点
* @Author BuXiaoLi
* @Date 2020/5/31 22:50
*/
public class TestStartCacheServer1 {

    public static void main(String[] args) {
        String[] cmds={"-nodeName","s2","-cacheXml","cache-server.xml","-env","dev"};
        StartCacheServer startCacheServer = new StartCacheServer();
        try {
            startCacheServer.basicStart(cmds);

            startCacheServer.waitForComplete();
        } catch (CmdLineException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}