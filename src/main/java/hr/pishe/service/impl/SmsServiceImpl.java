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

    private final String API_KEY = "4F426F766F584A2F4155385A6D7177713345414230436E3465447749472F4D79";

    @Override
    public Boolean send(String cellphone, String key) {
        log.debug("Request to send sms : {}, {}", cellphone, key);
        KavenegarApi kavenegarApi = new KavenegarApi(API_KEY);
        try {
            kavenegarApi.verifyLookup(cellphone, key, "AvandVerify");
            return true;
        } catch (ApiException | HttpException ex) {
            return false;
        }
    }
}
