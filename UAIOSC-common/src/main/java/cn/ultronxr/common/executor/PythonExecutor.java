package cn.ultronxr.common.executor;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.ultronxr.common.constant.System;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Ultronxr
 * @date 2023/03/04 12:10
 * @description Java 执行 python 脚本
 */
@Component
@Slf4j
public class PythonExecutor {
    
    private static final String OS = System.OS_NAME;

    private static final String WINDOWS_PATH = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1) + "python/";  // Windows路径
    private static final String LINUX_PATH = "/UAIOSC/python";// Linux路径

    private static ExecutorService taskPool =
            new ThreadPoolExecutor(8, 16, 200L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(600), new ThreadFactoryBuilder().setNamePrefix("PythonExecutor-runner-").build());

    /**
     * 执行python文件【异步 无需等待py文件执行完毕】
     *
     * @param fileName python文件地址
     * @param params   参数
     * @throws IOException
     */
    public static void execPythonFile(String fileName, String params) {
        taskPool.submit(() -> {
            try {
                exec(fileName, params);
            } catch (IOException e) {
                log.error("读取python文件 fileName=" + fileName + " 异常", e);
            }
        });
    }

    /**
     * 执行python文件 【同步 会等待py执行完毕】
     *
     * @param fileName python文件地址
     * @param params   参数
     * @throws IOException
     */
    public static List<String> execPythonFileSync(String fileName, String... params) {
        try {
            return execSync(fileName, params);
        } catch (IOException e) {
            log.error("读取python文件 fileName=" + fileName + " 异常", e);
        }
        return null;
    }

    private static void exec(String fileName, String params) throws IOException {
        log.info("读取python文件 init fileName={}&path={}", fileName, WINDOWS_PATH);
        Process process;
        if (OS.startsWith("Windows")) {
            // windows执行脚本需要使用 cmd.exe /c 才能正确执行脚本
            process = new ProcessBuilder("cmd.exe", "/c", "python", WINDOWS_PATH + fileName, params).start();
        } else {
            // linux执行脚本一般是使用python3 + 文件所在路径
            process = new ProcessBuilder("python3", LINUX_PATH + fileName, params).start();
        }

        new Thread(() -> {
            log.info("读取python文件 开始 fileName={}", fileName);
            BufferedReader errorReader = null;
            // 脚本执行异常时的输出信息
            errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            List<String> errorString = read(fileName, errorReader);
            log.info("读取python文件 异常 fileName={}&errorString={}", fileName, errorString);
        }).start();

        new Thread(() -> {
            // 脚本执行正常时的输出信息
            BufferedReader inputReader = null;
            inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            List<String> returnString = read(fileName, inputReader);
            log.info("读取python文件 fileName={}&returnString={}", fileName, returnString);
        }).start();

        try {
            log.info("读取python文件 wait fileName={}", fileName);
            process.waitFor();
        } catch (InterruptedException e) {
            log.error("读取python文件 fileName=" + fileName + " 等待结果返回异常", e);
        }
        log.info("读取python文件 fileName={} == 结束 ==", fileName);
    }

    private static List<String> execSync(String fileName, String... params) throws IOException {
        log.info("同步读取python文件 init fileName={}&path={}", fileName, WINDOWS_PATH);
        Process process;
        if (OS.startsWith("Windows")) {
            // windows执行脚本需要使用 cmd.exe /c 才能正确执行脚本
            List<String> list = new ArrayList<>();
            list.add("cmd.exe");
            list.add("/c");
            list.add("python");
            list.add(WINDOWS_PATH + fileName);
            list.addAll(List.of(params));
            //process = new ProcessBuilder("cmd.exe", "/c", "python", WINDOWS_PATH + fileName, params).start();
            process = new ProcessBuilder(list).start();
        } else {
            // linux执行脚本一般是使用python3 + 文件所在路径
            List<String> list = new ArrayList<>();
            list.add("python3");
            list.add(LINUX_PATH + fileName);
            list.addAll(List.of(params));
            //process = new ProcessBuilder("python3", LINUX_PATH + fileName, params).start();
            process = new ProcessBuilder(list).start();
        }

        taskPool.submit(() -> {
            log.info("读取python文件 开始 fileName={}", fileName);
            BufferedReader errorReader = null;
            // 脚本执行异常时的输出信息
            errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            List<String> errorString = read(fileName, errorReader);
            log.info("读取python文件 异常 fileName={}&errorString={}", fileName, errorString);
        });

        AtomicReference<List<String>> returnString = new AtomicReference<List<String>>();
        taskPool.submit(() -> {
            // 脚本执行正常时的输出信息
            BufferedReader inputReader = null;
            inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            returnString.set(read(fileName, inputReader));
            log.info("读取python文件 fileName={}&returnString={}", fileName, returnString);
        });

        try {
            log.info("同步读取python文件 wait fileName={}", fileName);
            process.waitFor();
        } catch (InterruptedException e) {
            log.error("同步读取python文件 fileName=" + fileName + " 等待结果返回异常", e);
        }
        log.info("同步读取python文件 fileName={} == 结束 ==", fileName);
        return returnString.get();
    }

    private static List<String> read(String fileName, BufferedReader reader) {
        List<String> resultList = new ArrayList<>();
        String res = "";
        while (true) {
            try {
                if ((res = reader.readLine()) == null) break;
            } catch (IOException e) {
                log.error("读取python文件 fileName=" + fileName + " 读取结果异常", e);
            }
            resultList.add(res);
        }
        return resultList;
    }

}