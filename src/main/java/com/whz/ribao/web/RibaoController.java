package com.whz.ribao.web;

import com.whz.ribao.core.SessionContext;
import com.whz.ribao.entity.Ribao;
import com.whz.ribao.entity.result.BaseWebModel;
import com.whz.ribao.entity.result.JsonResult;
import com.whz.ribao.entity.result.PageResult;
import com.whz.ribao.service.RibaoService;
import com.whz.ribao.utils.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 *
 */
@Controller
@RequestMapping("/ribao")
public class RibaoController extends BaseController {

    @Resource
    private RibaoService service;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("name", SessionContext.getUserInfo().getName());
        model.addAttribute("dateq", DateUtil.dateToString(DateUtils.addDays(new Date(), -1)));
        model.addAttribute("datez", DateUtil.dateToString(DateUtils.addDays(new Date(), 1)));
        return "ribao";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(Ribao ribao){
        return jsonResult(service.save(ribao));
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ResponseBody
    public PageResult find(BaseWebModel wm, String name, Date dateq, Date datez){
        return service.find(wm,name,dateq,datez);
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
