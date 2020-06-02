package org.jeecg.common.util.sms;

import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * author jinsq
 *
 * @date 2020/3/30 20:05
 */
public class QCloudSmsHelper {
    private final static Logger logger= LoggerFactory.getLogger(QCloudSmsHelper.class);

    static  int sdkAppId;
    static  String appKey;

    public static int getSdkAppId() {
        return sdkAppId;
    }

    public static void setSdkAppId(int sdkAppId) {
        QCloudSmsHelper.sdkAppId = sdkAppId;
    }

    public static String getAppKey() {
        return appKey;
    }

    public static void setAppKey(String appKey) {
        QCloudSmsHelper.appKey = appKey;
    }

    public static boolean sendSms(String phone, JSONObject templateParamJson, DySmsEnum smsEnum) throws Exception {
        SmsSingleSender ssender = new SmsSingleSender(sdkAppId, appKey);

        //验证json参数
        validateParam(templateParamJson,smsEnum);

        boolean result = false;
        ArrayList<String> objList = new ArrayList<>();
        for(String key : templateParamJson.keySet()){
            objList.add(templateParamJson.getString(key));
        }
        SmsSingleSenderResult senderResult = ssender.sendWithParam("86", phone,Integer.parseInt(smsEnum.getTemplateCode()),objList , smsEnum.getSignName(), "", "");

        logger.info("短信接口返回的数据----------------");
        logger.info("{Code:" + senderResult.getResponse().reason+",Message:" + senderResult.errMsg+",sid:"+ senderResult.sid+",body:"+senderResult.getResponse().body+"}");
        if ("OK".equals(senderResult.getResponse().reason)) {
            result = true;
        }
        return result;

    }

    private static void validateParam(JSONObject templateParamJson, DySmsEnum smsEnum) {
        String keys = smsEnum.getKeys();
        String [] keyArr = keys.split(",");
        for(String item :keyArr) {
            if(!templateParamJson.containsKey(item)) {
                throw new RuntimeException("模板缺少参数："+item);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("code", "1234");
        obj.put("minute","2");
        List<String> objList = new ArrayList<>();
        sendSms("15626475985", obj, DySmsEnum.REGISTER_TEMPLATE_CODE);
    }
}
