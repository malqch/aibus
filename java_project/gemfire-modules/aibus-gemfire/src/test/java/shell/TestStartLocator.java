package shell;

import org.kohsuke.args4j.CmdLineException;

import java.io.IOException;


/**
 * @ClassName StartLocalLocator
 * @Description 在本地启动一个locator节点
 * @Author BuXiaoLi
 * @Date 2020/5/31 22:49
 */
public class TestStartLocator {

    public static void main(String[] args) throws CmdLineException {
        String[] cmds = {"-port", "10334", "-nodeName", "locator1", "-heapSize", "200M", "-env", "dev"};
        StartLocator startLocator = new StartLocator();
        try {
            startLocator.basicStart(cmds);
            startLocator.waitForComplete();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}