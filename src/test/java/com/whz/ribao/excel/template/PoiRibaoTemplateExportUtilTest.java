package com.whz.ribao.excel.template;

import com.whz.ribao.entity.BaseBao;
import com.whz.ribao.entity.Ribao;
import com.whz.ribao.entity.result.BaseWebModel;
import com.whz.ribao.entity.result.PageResult;
import com.whz.ribao.service.RibaoService;
import com.whz.ribao.utils.DateUtil;
import com.whz.ribao.utils.poi.PoiRibaoTemplateExportUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by m1897 on 2017/8/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PoiRibaoTemplateExportUtilTest {

    @Resource
    private RibaoService ribaoService;

    @Test
    public void excelExport() throws IOException {
        BaseWebModel wm = new BaseWebModel();
        wm.setPage(1);
        wm.setPageSize(15);
        PageResult pageResult = ribaoService.find(wm, "", DateUtils.addDays(new Date(), -1), DateUtils.addDays(new Date(), 1));
        List<Ribao> rows = pageResult.getRows();
        Map<String, List<BaseBao>> collect = rows.stream()
                .map(ribao -> (BaseBao) ribao)
                .collect(Collectors.groupingBy(
                        ribao -> DateUtil.dateToString(((Ribao)ribao).getDate(), DateUtil.PATTERN_DAY)
                ));
        PoiRibaoTemplateExportUtil.exportExcel(collect);
    }
}
