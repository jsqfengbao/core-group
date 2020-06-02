package org.jeecg.modules.finance.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.DateUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.finance.entity.FundIncome;
import org.jeecg.modules.finance.service.IFundIncomeService;
import org.jeecg.modules.finance.service.IFundTypeService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 收入表
 * @Author: jeecg-boot
 * @Date:   2020-03-07
 * @Version: V1.0
 */
@Api(tags="收入表")
@RestController
@RequestMapping("/finance/fundIncome")
@Slf4j
public class FundIncomeController extends JeecgController<FundIncome, IFundIncomeService> {
	@Autowired
	private IFundIncomeService fundIncomeService;
	@Autowired
	private IFundTypeService fundTypeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param fundIncome
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "收入表-分页列表查询")
	@ApiOperation(value="收入表-分页列表查询", notes="收入表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(FundIncome fundIncome,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		String sysOrgCode = getUser().getOrgCode();
		fundIncome.setSysOrgCode(sysOrgCode);
		QueryWrapper<FundIncome> queryWrapper = QueryGenerator.initQueryWrapper(fundIncome, req.getParameterMap());
		Page<FundIncome> page = new Page<FundIncome>(pageNo, pageSize);
		IPage<FundIncome> pageList = fundIncomeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param fundIncome
	 * @return
	 */
	@AutoLog(value = "收入表-添加")
	@ApiOperation(value="收入表-添加", notes="收入表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody FundIncome fundIncome) {
		String fundTypeId = fundIncome.getFundTypeId();
		if(!fundTypeId.isEmpty()) {
			fundIncome.setFundTypeName(fundTypeService.getById(fundTypeId).getName());
		}
		if(null != fundIncome.getIncomeTime()){
			String payTime = DateUtils.formatDate(fundIncome.getIncomeTime());
			fundIncome.setYearNum(Integer.parseInt(payTime.substring(0,4)));
			fundIncome.setMonthNum(Integer.parseInt(payTime.substring(5,7)));
			fundIncome.setDayNum(Integer.parseInt(payTime.substring(8,10)));
		}
		fundIncomeService.save(fundIncome);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param fundIncome
	 * @return
	 */
	@AutoLog(value = "收入表-编辑")
	@ApiOperation(value="收入表-编辑", notes="收入表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody FundIncome fundIncome) {
		fundIncomeService.updateById(fundIncome);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "收入表-通过id删除")
	@ApiOperation(value="收入表-通过id删除", notes="收入表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		fundIncomeService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "收入表-批量删除")
	@ApiOperation(value="收入表-批量删除", notes="收入表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.fundIncomeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "收入表-通过id查询")
	@ApiOperation(value="收入表-通过id查询", notes="收入表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		FundIncome fundIncome = fundIncomeService.getById(id);
		if(fundIncome==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(fundIncome);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param fundIncome
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FundIncome fundIncome) {
        return super.exportXls(request, fundIncome, FundIncome.class, "收入表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, FundIncome.class);
    }

}
