package org.jeecg.modules.finance.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.finance.entity.FundYearBudget;
import org.jeecg.modules.finance.service.IFundYearBudgetService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 年预算
 * @Author: jeecg-boot
 * @Date:   2020-03-07
 * @Version: V1.0
 */
@Api(tags="年预算")
@RestController
@RequestMapping("/finance/fundYearBudget")
@Slf4j
public class FundYearBudgetController extends JeecgController<FundYearBudget, IFundYearBudgetService> {
	@Autowired
	private IFundYearBudgetService fundYearBudgetService;
	
	/**
	 * 分页列表查询
	 *
	 * @param fundYearBudget
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "年预算-分页列表查询")
	@ApiOperation(value="年预算-分页列表查询", notes="年预算-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(FundYearBudget fundYearBudget,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		String sysOrgCode = getUser().getOrgCode();
		fundYearBudget.setSysOrgCode(sysOrgCode);
		QueryWrapper<FundYearBudget> queryWrapper = QueryGenerator.initQueryWrapper(fundYearBudget, req.getParameterMap());
		Page<FundYearBudget> page = new Page<FundYearBudget>(pageNo, pageSize);
		IPage<FundYearBudget> pageList = fundYearBudgetService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param fundYearBudget
	 * @return
	 */
	@AutoLog(value = "年预算-添加")
	@ApiOperation(value="年预算-添加", notes="年预算-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody FundYearBudget fundYearBudget) {
		fundYearBudgetService.save(fundYearBudget);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param fundYearBudget
	 * @return
	 */
	@AutoLog(value = "年预算-编辑")
	@ApiOperation(value="年预算-编辑", notes="年预算-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody FundYearBudget fundYearBudget) {
		fundYearBudgetService.updateById(fundYearBudget);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "年预算-通过id删除")
	@ApiOperation(value="年预算-通过id删除", notes="年预算-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		fundYearBudgetService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "年预算-批量删除")
	@ApiOperation(value="年预算-批量删除", notes="年预算-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.fundYearBudgetService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "年预算-通过id查询")
	@ApiOperation(value="年预算-通过id查询", notes="年预算-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		FundYearBudget fundYearBudget = fundYearBudgetService.getById(id);
		if(fundYearBudget==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(fundYearBudget);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param fundYearBudget
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FundYearBudget fundYearBudget) {
        return super.exportXls(request, fundYearBudget, FundYearBudget.class, "年预算");
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
        return super.importExcel(request, response, FundYearBudget.class);
    }

}
