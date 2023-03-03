package cn.ultronxr.valorant.bean;

import lombok.Data;

/**
 * @author Ultronxr
 * @date 2023/02/21 19:06
 * @description
 */
@Data
public class RiotClientVersion {

    private String manifestId;
    private String branch;
    private String version;
    private String buildVersion;
    private String engineVersion;
    private String riotClientVersion;
    private String riotClientBuild;
    private String buildDate;

}
