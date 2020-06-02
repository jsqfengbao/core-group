package org.jeecg.modules.finance.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.DateUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.finance.entity.FundPay;
import org.jeecg.modules.finance.service.IFundPayService;
import org.jeecg.modules.finance.service.IFundTypeService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 支出表
 * @Author: jeecg-boot
 * @Date:   2020-03-07
 * @Version: V1.0
 */
@Api(tags="支出表")
@RestController
@RequestMapping("/finance/fundPay")
@Slf4j
public class FundPayController extends JeecgController<FundPay, IFundPayService> {
	@Autowired
	private IFundPayService fundPayService;
	@Autowired
	private IFundTypeService fundTypeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param fundPay
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "支出表-分页列表查询")
	@ApiOperation(value="支出表-分页列表查询", notes="支出表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(FundPay fundPay,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		String sysOrgCode = getUser().getOrgCode();
		fundPay.setSysOrgCode(sysOrgCode);
		QueryWrapper<FundPay> queryWrapper = QueryGenerator.initQueryWrapper(fundPay, req.getParameterMap());
		Page<FundPay> page = new Page<FundPay>(pageNo, pageSize);
		IPage<FundPay> pageList = fundPayService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param fundPay
	 * @return
	 */
	@AutoLog(value = "支出表-添加")
	@ApiOperation(value="支出表-添加", notes="支出表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody FundPay fundPay) {
		String fundTypeId = fundPay.getFundTypeId();
		if(!fundTypeId.isEmpty()) {
			fundPay.setFundTypeName(fundTypeService.getById(fundTypeId).getName());
		}
		if(null != fundPay.getPayTime()){
			String payTime = DateUtils.formatDate(fundPay.getPayTime());
			fundPay.setYearNum(Integer.parseInt(payTime.substring(0,4)));
			fundPay.setMonthNum(Integer.parseInt(payTime.substring(5,7)));
			fundPay.setDayNum(Integer.parseInt(payTime.substring(8,10)));
		}

		fundPayService.save(fundPay);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param fundPay
	 * @return
	 */
	@AutoLog(value = "支出表-编辑")
	@ApiOperation(value="支出表-编辑", notes="支出表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody FundPay fundPay) {
		fundPayService.updateById(fundPay);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "支出表-通过id删除")
	@ApiOperation(value="支出表-通过id删除", notes="支出表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		fundPayService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "支出表-批量删除")
	@ApiOperation(value="支出表-批量删除", notes="支出表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.fundPayService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "支出表-通过id查询")
	@ApiOperation(value="支出表-通过id查询", notes="支出表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		FundPay fundPay = fundPayService.getById(id);
		if(fundPay==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(fundPay);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param fundPay
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FundPay fundPay) {
        return super.exportXls(request, fundPay, FundPay.class, "支出表");
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
        return super.importExcel(request, response, FundPay.class);
    }

}
