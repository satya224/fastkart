package com.fastkart.commonlibrary.feign;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 401:
                return new Exception("Unauthorized");
            case 403:
                return new Exception("Forbidden");
            case 404:
                return new Exception("Not Found");
            case 500:
                return new Exception("Internal Server Error");
            default:
                break;
        }
        return new Exception("Error occurred while calling " + methodKey + ". Status code " + response.status());
    }
}
