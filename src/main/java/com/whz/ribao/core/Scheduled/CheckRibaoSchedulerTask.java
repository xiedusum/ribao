package com.whz.ribao.core.Scheduled;

import com.whz.ribao.entity.Ribao;
import com.whz.ribao.entity.UserInfo;
import com.whz.ribao.service.MailService;
import com.whz.ribao.service.RibaoService;
import com.whz.ribao.service.UserInfoService;
import com.whz.ribao.utils.DateUtil;
import com.whz.ribao.utils.sms.SmsUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 如果没有填写日报就他提醒他
 */
@Component
public class CheckRibaoSchedulerTask {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private RibaoService ribaoService;

    @Resource
    private MailService mailService;


    @Scheduled(cron="0 30 17 ? * MON-FRI")
    private void mail(){
        List<UserInfo> notifyUsers = getNotifyUsers();
        if (CollectionUtils.isNotEmpty(notifyUsers))
            notifyUsers.stream()
                    .filter(u -> checkEmail(u.getEmail()))
                    .forEach(u -> {
                        mailService.sendSimpleMail(u.getEmail(), "未填写日报的邮件提醒",
                                u.getName()+"，您好！请您尽快填写日报，谢谢！");
                    });
    }

    @Scheduled(cron="0 00 20 ? * MON-FRI")
    private void sms(){
        List<UserInfo> notifyUsers = getNotifyUsers();
        if (CollectionUtils.isNotEmpty(notifyUsers))
            notifyUsers.stream()
                    .filter(u -> checkPhone(u.getPhone()))
                    .forEach(u -> {
                        SmsUtil.sendSms(u.getPhone(), "{\"name\":\""+u.getUsername()+"\"}");
                    });
    }

    private boolean checkEmail(String email){
        return StringUtils.isNotEmpty(email);
    }

    private boolean checkPhone(String phone){
        return phone != null && phone.matches("1\\d{10}");
    }

    /**
     * 获取要通知的用户
     * @return
     */
    private List<UserInfo> getNotifyUsers(){
        //要检查的用户
        List<UserInfo> userInfos = userInfoService.findAll();

        // 2017-08-16
        Date date = DateUtil.stringToDate(DateUtil.getNowDate());
        //要检查的日报，包含今天的完成情况和以后的计划
        List<Ribao> ribaos = ribaoService.findByDateGreaterThanEqual(date);
        //今天完成情况
        List<Ribao> todayRibaos = ribaos.stream().filter(ribao -> ribao.getDate().equals(date)).collect(Collectors.toList());
        //以后计划
        List<Ribao> planRibaos = ribaos.stream().filter(ribao -> ribao.getDate().after(date)).collect(Collectors.toList());
        //1、找到今天完成情况没写或者以后计划没写的
        //1.1、找到写了完成情况而且写了计划的同学
        List<Integer> todayUserIds = todayRibaos.stream()
                .map(ribao -> ribao.getUserInfo().getUid()).collect(Collectors.toList());
        List<Integer> planUserIds = planRibaos.stream()
                .map(ribao -> ribao.getUserInfo().getUid()).collect(Collectors.toList());
        Collection<Integer> ids = CollectionUtils.intersection(todayUserIds, planUserIds);//都写了
        //2、写的不规范的
        todayUserIds = todayRibaos.stream()
                .filter(ribao -> ribao.getPlan() == null || ribao.getPractical() == null)
                .map(ribao -> ribao.getUserInfo().getUid()).collect(Collectors.toList());
        planUserIds = planRibaos.stream()
                .filter(ribao -> !(ribao.getPlan() != null && ribao.getPractical() == null))
                .map(ribao -> ribao.getUserInfo().getUid()).collect(Collectors.toList());
        ids = CollectionUtils.subtract(ids, todayUserIds);
        //真正合格的用户
        Collection<Integer> finalIds = CollectionUtils.subtract(ids, planUserIds);

        return userInfos.stream().filter(u -> !finalIds.contains(u.getUid())).collect(Collectors.toList());
    }
}
