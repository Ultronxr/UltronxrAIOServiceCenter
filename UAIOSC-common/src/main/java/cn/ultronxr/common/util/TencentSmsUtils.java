package cn.ultronxr.common.util;

import cn.ultronxr.common.constant.ResBundle;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author Ultronxr
 * @date 2023/04/15 19:03:30
 * @description 腾讯云发送短信 工具类
 */
@Slf4j
public class TencentSmsUtils {

    /**
     * 发送短信
     *
     * @param sign
     *          短信签名，一个字符串，标识发送短信的个人/组织，向腾讯云短信服务申请，且需要审核通过才能使用。<br/>
     *          如果传入的字符串为null或为空，则读取配置文件中的默认短信签名。
     *
     * @param templateId
     *          短信模板ID，一个数字字符串，从腾讯云短信服务申请分配，且需要审核通过才能使用。<br/>
     *          如果传入的字符串为null或为空，则直接return一个空的 SendSmsResponse 对象，停止发送短信。
     *
     * @param templateParamSet
     *          短信模板参数，若无模板参数，则设置为空。<br/>
     *          模板参数的个数需要与 templateId 对应模板的变量个数保持一致。
     *
     * @param phoneNumberSet
     *          接收短信的手机号字符串数组<br/>
     *          如果传入的数组为null或为空，则直接return一个空的 SendSmsResponse 对象，停止发送短信。<br/>
     *          请注意手机号格式（需要携带加号和区号，再接手机号），例：["+8612345678901", "+8612345678902"]
     *
     * @return
     *          {@code SendSmsResponse}腾讯云短信服务的发送短信通用类，记录这次短信发送过程的各种信息<br/>
     *          可以使用{@code SendSmsResponse.toJsonString()}方法输出完整内容<br/>
     *          如果传参smsTemplateId为null或为空，将会return一个空的{@code SendSmsResponse}对象
     *
     * @throws TencentCloudSDKException
     *          腾讯云SDK执行异常
     */
    public static SendSmsResponse tencentSms(String sign, String templateId, String[] templateParamSet, String[] phoneNumberSet)
            throws TencentCloudSDKException {
        if(StringUtils.isEmpty(sign)){
            sign = ResBundle.SMS.getString("sign");
        }
        if(StringUtils.isEmpty(templateId)){
            return new SendSmsResponse();
        }
        if(Objects.isNull(phoneNumberSet) || phoneNumberSet.length == 0){
            return new SendSmsResponse();
        }

        Credential tencentApiCredential = new Credential(
                ResBundle.TENCENT_CLOUD.getString("secret.id"),
                ResBundle.TENCENT_CLOUD.getString("secret.key")
        );

        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setEndpoint("sms.ap-shanghai.tencentcloudapi.com");

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        SmsClient smsClient = new SmsClient(tencentApiCredential, "ap-guangzhou", clientProfile);
        SendSmsRequest req = new SendSmsRequest();
        req.setSignName(sign);
        req.setTemplateId(templateId);
        req.setTemplateParamSet(templateParamSet);
        req.setPhoneNumberSet(phoneNumberSet);
        req.setSmsSdkAppId(ResBundle.TENCENT_CLOUD.getString("app.id"));

        log.info("腾讯云SMS服务发送短信。sign={}  templateId={}  templateParamSet={}  phoneNumberSet={}",
                sign, templateId, templateParamSet, phoneNumberSet);

        return smsClient.SendSms(req);
    }

}
