package com.qinC.msm.service;

import java.util.Map;

public interface MsmService {

    boolean send(String code, Map<String, Object> param, String phone);

}
