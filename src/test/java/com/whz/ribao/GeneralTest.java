package com.whz.ribao;

import com.whz.ribao.service.MailService;
import com.whz.ribao.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.Year;
import java.util.Date;

/**
 *
 */
public class GeneralTest {

    @Resource
    private MailService mailService;

    @Test
    public void one(){

    }
}
