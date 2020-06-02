package org.jeecg.modules.finance.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.finance.entity.FundBudget;
import org.jeecg.modules.finance.service.IFundBudgetService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.finance.service.IFundTypeService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 预算管理
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Api(tags="预算管理")
@RestController
@RequestMapping("/finance/fundBudget")
@Slf4j
public class FundBudgetController extends JeecgController<FundBudget, IFundBudgetService> {
	@Autowired
	private IFundBudgetService fundBudgetService;
	@Autowired
	private IFundTypeService fundTypeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param fundBudget
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "预算管理-分页列表查询")
	@ApiOperation(value="预算管理-分页列表查询", notes="预算管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(FundBudget fundBudget,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		String sysOrgCode = getUser().getOrgCode();
		fundBudget.setSysOrgCode(sysOrgCode);
		QueryWrapper<FundBudget> queryWrapper = QueryGenerator.initQueryWrapper(fundBudget, req.getParameterMap());
		Page<FundBudget> page = new Page<FundBudget>(pageNo, pageSize);
		IPage<FundBudget> pageList = fundBudgetService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param fundBudget
	 * @return
	 */
	@AutoLog(value = "预算管理-添加")
	@ApiOperation(value="预算管理-添加", notes="预算管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody FundBudget fundBudget) {
		if(!fundBudget.getFundTypeId().isEmpty()){
			fundBudget.setFundTypeName(fundTypeService.getById(fundBudget.getFundTypeId()).getName());
		}
		fundBudgetService.save(fundBudget);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param fundBudget
	 * @return
	 */
	@AutoLog(value = "预算管理-编辑")
	@ApiOperation(value="预算管理-编辑", notes="预算管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody FundBudget fundBudget) {
		fundBudgetService.updateById(fundBudget);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "预算管理-通过id删除")
	@ApiOperation(value="预算管理-通过id删除", notes="预算管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		fundBudgetService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "预算管理-批量删除")
	@ApiOperation(value="预算管理-批量删除", notes="预算管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.fundBudgetService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "预算管理-通过id查询")
	@ApiOperation(value="预算管理-通过id查询", notes="预算管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		FundBudget fundBudget = fundBudgetService.getById(id);
		if(fundBudget==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(fundBudget);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param fundBudget
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FundBudget fundBudget) {
        return super.exportXls(request, fundBudget, FundBudget.class, "预算管理");
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
        return super.importExcel(request, response, FundBudget.class);
    }

}
