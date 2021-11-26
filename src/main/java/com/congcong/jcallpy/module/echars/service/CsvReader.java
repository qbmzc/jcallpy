package com.congcong.jcallpy.module.echars.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author cong
 * @since 2021/11/26 14:55
 */
@Slf4j
@Service
public class CsvReader {
    private static final String BASE_PATH = "/Users/cong/IdeaProjects/jcallpy/src/main/resources/csv/res_%s.csv";
    private static final int[] arr = new int[]{0, 20, 40, 60, 80, 100};

    private static final Queue<Integer> queue = new LinkedBlockingDeque<Integer>(arr.length);

    //初始化队列
    static {
        log.info("初始化队列");
        for (int i : arr) {
            queue.offer(i);
        }
    }

    /**
     * 从csv文件中获取结果
     *
     * @return
     */
    public List<Object> getData() {
        if (queue.isEmpty()) return null;
        List<Object> parentList = new ArrayList<>();
        try (FileReader reader = new FileReader(String.format(BASE_PATH, queue.poll()))) {
            CSVFormat csvFormat = CSVFormat.Builder.create()
                    .setHeader("label", "health", "atype", "pos")
                    .setSkipHeaderRecord(true)
                    .build();
            CSVParser records = csvFormat.parse(reader);
            records.forEach(r -> {
                ArrayList<Object> list = new ArrayList<>();
                String label = r.get("label");
                String health = r.get("health");
                String atype = r.get("atype");
                String pos = r.get("pos");
                // System.out.println("label:" + label + ",health:" + health + ",atype:" + atype + ",pos:" + pos);
                String x = pos.substring(pos.indexOf("(") + 1, pos.indexOf(','));
                String y = pos.substring(pos.indexOf(",") + 1, pos.indexOf(')'));
                list.add(Double.valueOf(x).intValue());
                list.add(Double.valueOf(y).intValue());
                list.add(Integer.valueOf(health));
                list.add(label);
                list.add(Integer.valueOf(atype));
                parentList.add(list);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("数据集{}", parentList);
        return parentList;
    }

}
