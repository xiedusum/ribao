package com.whz.ribao.web;

import com.whz.ribao.core.SessionContext;
import com.whz.ribao.entity.Zhoubao;
import com.whz.ribao.entity.result.BaseWebModel;
import com.whz.ribao.entity.result.JsonResult;
import com.whz.ribao.entity.result.PageResult;
import com.whz.ribao.service.ZhoubaoService;
import com.whz.ribao.utils.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.time.Year;
import java.util.Date;

/**
 *
 */
@Controller
@RequestMapping("/zhoubao")
public class ZhoubaoController extends BaseController {

    @Resource
    private ZhoubaoService service;

    @RequestMapping("/")
    public String index(Model model){
        long week = DateUtil.getFragmentInWeek(new Date());
        String weekq = Year.now()+""+week;
        model.addAttribute("name", SessionContext.getUserInfo().getName());
        model.addAttribute("weekq", weekq);
        model.addAttribute("weekz", DateUtil.getNextWeek(weekq));
        return "zhoubao";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(Zhoubao zhoubao){
        return jsonResult(service.save(zhoubao));
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ResponseBody
    public PageResult find(BaseWebModel wm, String name, String weekq, String weekz){
        return service.find(wm,name,weekq,weekz);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult find(Long[] ids){
        return jsonResult(service.del(ids));
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletResponse response){
        service.export(response);
    }
}
