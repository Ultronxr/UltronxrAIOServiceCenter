package cn.ultronxr.tool.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author Ultronxr
 * @date 2024/01/25 22:13:30
 * @description IP 登录被封锁的记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IPLockoutRecord implements Comparable<IPLockoutRecord> {

    /** 日期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    /** IP 地址 */
    private String IPAddress;

    /** 登录用户名 */
    private Set<String> usernameSet;

    /** 登录网关 */
    private Set<String> gatewaySet;

    /** 封锁总次数 */
    private Integer lockouts;

    /** 当前是否处于封锁状态 */
    private Boolean isLockout;


    public IPLockoutRecord(String IPAddress, Integer lockouts) {
        this.IPAddress = IPAddress;
        this.lockouts = lockouts;
    }

    /**
     * 从一行文本格式的记录内容中构造一个 IPLockoutRecord 对象
     * @param recordLine 一行文本格式的记录内容<br/>
     *                      其固定格式为：日期 时间\tIPv4地址\t登录用户名 (封锁次数 lockouts)\t登录网关\t封锁状态
     *                      <br/>例1：2024/1/26 01:23:16	1.22.33.44	Martin (2 lockouts)	wp_login	Unlock
     *                      <br/>例2：2024/1/24 13:29:41	2.3.44.55	Bob (1 lockouts)	wp_xmlrpc	Unlocked
     * @return 构造的 IPLockoutRecord 对象
     */
    public static IPLockoutRecord ofRecordLine(String recordLine) {
        if(StringUtils.isBlank(recordLine)) {
            return null;
        }
        String[] split = recordLine.split("\t");
        IPLockoutRecord record = null;
        try {
            record = new IPLockoutRecord();
            record.setDateTime(LocalDateTime.parse(split[0]));
            record.setIPAddress(split[1]);
            record.setUsernameSet(Set.of(split[2].split(" ")[0]));
            record.setGatewaySet(Set.of(split[3]));
            record.setLockouts(Integer.parseInt(split[2].split(" ")[1].substring(1)));
            record.setIsLockout(split[4].endsWith("ed"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return record;
    }

    /**
     * 重写 equals 方法，用于判断两个对象是否相等<br/>
     * 这里只比较 IPAddress 这一个属性， IP 相同即判断为同一个对象
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IPLockoutRecord that = (IPLockoutRecord) o;
        //return Objects.equals(dateTime, record.dateTime) && Objects.equals(IPAddress, record.IPAddress) && Objects.equals(usernameSet, record.usernameSet) && Objects.equals(gatewaySet, record.gatewaySet) && Objects.equals(lockouts, record.lockouts) && Objects.equals(isLockout, record.isLockout);
        return Objects.equals(IPAddress, that.IPAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, IPAddress, usernameSet, gatewaySet, lockouts, isLockout);
    }

    /**
     * 重写 compareTo 方法，用于排序<br/>
     * 按照 lockouts 降序排序
     */
    @Override
    public int compareTo(@NotNull IPLockoutRecord o) {
        if(this.getLockouts() > o.getLockouts()) {
            return -1;
        } else if(this.getLockouts() < o.getLockouts()) {
            return 1;
        } else {
            // lockouts 相等时，按照 IPAddress 字典序升序排序
            //return 0;
            return this.getIPAddress().compareTo(o.getIPAddress());
        }
    }

}
