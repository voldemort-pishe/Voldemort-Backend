package hr.pishe.service.impl;

import com.kavenegar.sdk.KavenegarApi;
import com.kavenegar.sdk.excepctions.ApiException;
import com.kavenegar.sdk.excepctions.HttpException;
import hr.pishe.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class SmsServiceImpl implements SmsService {

    private final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);

    private final String API_KEY = "6168782F3368713965576C4E76634F524C4E4F7259506236732B4C796A476A67";

    @Override
    public Boolean send(String cellphone, String key) {
        log.debug("Request to send sms : {}, {}", cellphone, key);
        KavenegarApi kavenegarApi = new KavenegarApi(API_KEY);
        try {
            kavenegarApi.verifyLookup(cellphone, key, "PisheVerify");
            return true;
        } catch (ApiException | HttpException ex) {
            return false;
        }
    }
}
