package cn.ultronxr.wechat.publicPlatform.bean.DTO;

import cn.hutool.json.JSONObject;
import lombok.Data;

/**
 * @author Ultronxr
 * @date 2023/09/21 18:47:17
 * @description 模板消息 DTO
 */
@Data
public class MessageTemplateDTO {

    /** 接收该消息的用户 open ID */
    private String[] toUserOpenIds;

    /** 消息模板 ID */
    private String templateId;

    /** 消息跳转链接，无需跳转则留空 */
    private String url;

    /** 消息标题颜色，例 #FF0000（当前无效） */
    private String topColor;

    /**
     * 消息模板预留关键词内容，json 格式
     * <pre>
     * {
     *     "keyword1": {
     *         "value": "消息内容1！",
     *         "color": "#CCCCCC"
     *     },
     *     "keyword2": {
     *         "value": "消息内容2",
     *         "color": "#CCCCCC"
     *     }
     * }
     * <pre/>
     */
    private JSONObject data;

}
