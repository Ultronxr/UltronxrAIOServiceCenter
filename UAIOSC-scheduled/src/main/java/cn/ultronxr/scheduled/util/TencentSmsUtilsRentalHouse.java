package cn.ultronxr.scheduled.util;

import cn.ultronxr.common.constant.ResBundle;
import cn.ultronxr.common.util.TencentSmsUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author Ultronxr
 * @date 2023/04/15 19:15:31
 * @description
 */
@Slf4j
public class TencentSmsUtilsRentalHouse {

    private static final String SIGN = ResBundle.SMS.getString("sign");

    private static final String TEMPLATE_ID_ELECTRICITY_BILL = ResBundle.SMS.getString("template.id.electricityBill");
    //private static final String TEMPLATE_ID_ELECTRICITY_BILL = ResBundle.SMS.getString("template.id.electricityBill.2Status");


    /**
     * 对tencentSms()方法的 RentalHouseFees 封装
     * 只需要传入一个短信模板ID即可，其他参数省略不填
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
     *          发送短信后返回的{@code SendSmsResponse}对象转成JsonString的结果字符串
     *          如果传入的短信模板为null或为空，return的字符串为“{}”
     */
    public static SendSmsResponse tencentSmsElectricityBill(String[] templateParamSet, String[] phoneNumberSet) {
        if(Objects.isNull(phoneNumberSet) || phoneNumberSet.length == 0){
            phoneNumberSet = ResBundle.SMS.getString("phoneNumber.electricityBill").split(" ");
        }

        SendSmsResponse response = new SendSmsResponse();
        try {
            response =
                TencentSmsUtils.tencentSms(
                    SIGN,
                    TEMPLATE_ID_ELECTRICITY_BILL,
                    templateParamSet,
                    phoneNumberSet
                );
        } catch (TencentCloudSDKException e) {
            log.warn("腾讯云SMS服务发送短信失败！sign={}  templateId={}  templateParamSet={}  phoneNumberSet={}",
                    SIGN, TEMPLATE_ID_ELECTRICITY_BILL, templateParamSet, phoneNumberSet);
            log.warn("", e);
        }
        //return SendSmsResponse.toJsonString(response);
        return response;
    }

}
