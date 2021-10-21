package com.congcong.jcallpy.util;

import lombok.extern.slf4j.Slf4j;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author cong
 * @since 2021/10/19 10:44
 */
@Slf4j
public class CommandUtils {
    /**
     * python 命令
     */
    private static final String PYTHON_COMMAND = "python";

    /**
     * 执行命令并打印执行结果日志
     *
     * @param command 待执行的命令
     */
    public static String doExec(String command) {
        ProcessBuilder pb;
        Process process = null;
        String result = null;


        log.info("执行的命令为:{}", PYTHON_COMMAND + " " + command);
        long start = System.currentTimeMillis();
        try {
            pb = new ProcessBuilder(PYTHON_COMMAND, command);
            // 标准错误将与标准输出合并 在此情况下，合并的数据可从 Process.getInputStream() 返回的流读取
            pb.redirectErrorStream(true);
            process = pb.start();
            InputStream inputStream = process.getInputStream();
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> handler(inputStream));
            //增加超时获取，如果超出一定时间，则转码失败，不能一直阻塞进程
            //超时抛出异常，中断进程
            result = future.get(1, TimeUnit.HOURS);
            int i = process.waitFor();
            if (i != 0) {
                //mac下返回值不为0
                log.info("命令执行失败");
                return null;
            } else {
                log.info("命令执行完成，耗时：{} ms", System.currentTimeMillis() - start);
            }
        } catch (Exception e) {
            log.error("出现异常，异常信息：{}", e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (null != process)
                process.destroy();
        }
        return result;
    }

    /**
     * 处理FFmpeg输出
     *
     * @param stream ff的输出流
     * @return 输出结果
     */
    public static String handler(InputStream stream) {
        String line;
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));) {
            while ((line = reader.readLine()) != null) {
                list.add(line);
                log.info("Stream result：{}", line);
            }
        } catch (Exception e) {
            log.error("cmd error:{}", e.getMessage(), e);
            throw new RuntimeException("error");
        }
        return String.join("\n", list);
    }

    public static void main(String[] args) {
        String pythonFilePath = "/Users/cong/IdeaProjects/PythonSpace/tkinter-demo/test.py";
        String s = doExec(pythonFilePath);
        System.out.println(s);

    }
}
