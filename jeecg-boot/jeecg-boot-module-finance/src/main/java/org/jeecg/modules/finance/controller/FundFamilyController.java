package org.jeecg.modules.finance.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.finance.entity.FundFamily;
import org.jeecg.modules.finance.service.IFundFamilyService;

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
 * @Description: 家庭表
 * @Author: jeecg-boot
 * @Date:   2020-03-05
 * @Version: V1.0
 */
@Api(tags="家庭表")
@RestController
@RequestMapping("/finance/fundFamily")
@Slf4j
public class FundFamilyController extends JeecgController<FundFamily, IFundFamilyService> {
	@Autowired
	private IFundFamilyService fundFamilyService;
	
	/**
	 * 分页列表查询
	 *
	 * @param fundFamily
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "家庭表-分页列表查询")
	@ApiOperation(value="家庭表-分页列表查询", notes="家庭表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(FundFamily fundFamily,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		String sysOrgCode = getUser().getOrgCode();
		fundFamily.setSysOrgCode(sysOrgCode);
		QueryWrapper<FundFamily> queryWrapper = QueryGenerator.initQueryWrapper(fundFamily, req.getParameterMap());
		Page<FundFamily> page = new Page<FundFamily>(pageNo, pageSize);
		IPage<FundFamily> pageList = fundFamilyService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param fundFamily
	 * @return
	 */
	@AutoLog(value = "家庭表-添加")
	@ApiOperation(value="家庭表-添加", notes="家庭表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody FundFamily fundFamily) {
		fundFamilyService.save(fundFamily);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param fundFamily
	 * @return
	 */
	@AutoLog(value = "家庭表-编辑")
	@ApiOperation(value="家庭表-编辑", notes="家庭表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody FundFamily fundFamily) {
		fundFamilyService.updateById(fundFamily);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "家庭表-通过id删除")
	@ApiOperation(value="家庭表-通过id删除", notes="家庭表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		fundFamilyService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "家庭表-批量删除")
	@ApiOperation(value="家庭表-批量删除", notes="家庭表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.fundFamilyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "家庭表-通过id查询")
	@ApiOperation(value="家庭表-通过id查询", notes="家庭表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		FundFamily fundFamily = fundFamilyService.getById(id);
		if(fundFamily==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(fundFamily);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param fundFamily
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FundFamily fundFamily) {
        return super.exportXls(request, fundFamily, FundFamily.class, "家庭表");
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
        return super.importExcel(request, response, FundFamily.class);
    }

}
