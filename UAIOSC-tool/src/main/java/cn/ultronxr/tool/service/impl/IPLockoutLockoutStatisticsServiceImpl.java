//package cn.ultronxr.tool.IPLockoutStatistics.service.impl;
//
//import cn.hutool.core.util.ReUtil;
//import cn.ultronxr.tool.bean.IPLockoutRecord;
//import cn.ultronxr.tool.IPLockoutStatistics.service.IPLockoutStatisticsService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections4.set.ListOrderedSet;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.data.util.Pair;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
///**
// * @author Ultronxr
// * @date 2024/01/25 22:04:26
// * @description
// */
//@Service
//@Slf4j
//public class IPLockoutLockoutStatisticsServiceImpl implements IPLockoutStatisticsService {
//
//
//    @Override
//    public LinkedHashMap<String, Integer> simpleProcess(String content) {
//        String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
//        List<String> allIPList = ReUtil.findAll(regex, content, 0);
//        log.info("records count = {}", allIPList.size());
//        if(allIPList.isEmpty()) {
//            return null;
//        }
//
//        LinkedHashMap<String, Integer>
//                result8 = new LinkedHashMap<>((int) (allIPList.size() / 0.75) + 1),
//                result16 = new LinkedHashMap<>((int) (allIPList.size() / 0.75) + 1),
//                result24 = new LinkedHashMap<>((int) (allIPList.size() / 0.75) + 1),
//                result36 = new LinkedHashMap<>((int) (allIPList.size() / 0.75) + 1);
//        for(String ip : allIPList) {
//            String[] ipArray = ip.split("\\.");
//            String ip8 = ipArray[0] + ".0.0.0/8",
//                    ip16 = ipArray[0] + "." + ipArray[1] + ".0.0/16",
//                    ip24 = ipArray[0] + "." + ipArray[1] + "." + ipArray[2] + ".0/24",
//                    ip36 = ip;
//            result8.put(ip8, result8.getOrDefault(ip8, 0) + 1);
//            result16.put(ip16, result16.getOrDefault(ip16, 0) + 1);
//            result24.put(ip24, result24.getOrDefault(ip24, 0) + 1);
//            result36.put(ip36, result36.getOrDefault(ip36, 0) + 1);
//        }
//        LinkedHashMap<String, Integer> resultAll = new LinkedHashMap<>((int) (allIPList.size() * 4 / 0.75) + 1);
//        resultAll.putAll(result8);
//        resultAll.putAll(result16);
//        resultAll.putAll(result24);
//        resultAll.putAll(result36);
//        return resultAll;
//    }
//
//    @Override
//    public ArrayList<IPLockoutRecord> readRecords(String content) {
//        if(StringUtils.isBlank(content)) {
//            return null;
//        }
//        // 校验文本内容格式
//        String firstLine = content.split("\n")[0];
//        String[] firstLineSplit = firstLine.split("\t");
//        if(firstLineSplit.length != 5) {
//            throw new RuntimeException("文本内容格式错误");
//        }
//
//        ArrayList<IPLockoutRecord> result = new ArrayList<>();
//        content.lines().forEach(line -> {
//            result.add(IPLockoutRecord.ofRecordLine(line));
//        });
//        result.sort(IPLockoutRecord::compareTo);
//        return result;
//    }
//
//    public ArrayList<IPLockoutRecord> simpleProcessTest(String content) {
//        String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
//        List<String> ipList = ReUtil.findAll(regex, content, 0);
//        log.info("records count = {}", ipList.size());
//        if(ipList.isEmpty()) {
//            return null;
//        }
//
//        ArrayList<IPLockoutRecord> resultList = new ArrayList<>(ipList.size());
//        for(String ip : ipList) {
//            String[] ipArray = ip.split("\\.");
//            String ip8 = ipArray[0] + ".0.0.0/8",
//                    ip16 = ipArray[0] + "." + ipArray[1] + ".0.0/16",
//                    ip24 = ipArray[0] + "." + ipArray[1] + "." + ipArray[2] + ".0/24",
//                    ip36 = ip;
//            int indexOfResultList = resultList.indexOf(new IPLockoutRecord(ip36, null));
//            if(indexOfResultList < 0) {
//                resultList.add(new IPLockoutRecord(ip36, 1));
//            } else {
//                resultList.get(indexOfResultList).setLockouts(resultList.get(indexOfResultList).getLockouts() + 1);
//            }
//        }
//        SortedMap
//        resultList.sort(IPLockoutRecord::compareTo);
//        return resultList;
//    }
//