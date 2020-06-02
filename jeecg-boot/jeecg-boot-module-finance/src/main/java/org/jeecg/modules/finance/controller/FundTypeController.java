package org.jeecg.modules.finance.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.finance.entity.FundType;
import org.jeecg.modules.finance.service.IFundTypeService;

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
 * @Description: 资金类型
 * @Author: jeecg-boot
 * @Date:   2020-03-04
 * @Version: V1.0
 */
@Api(tags="资金类型")
@RestController
@RequestMapping("/finance/fundType")
@Slf4j
public class FundTypeController extends JeecgController<FundType, IFundTypeService> {
	@Autowired
	private IFundTypeService fundTypeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param fundType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "资金类型-分页列表查询")
	@ApiOperation(value="资金类型-分页列表查询", notes="资金类型-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(FundType fundType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		String sysOrgCode = getUser().getOrgCode();
		fundType.setSysOrgCode(sysOrgCode);
		QueryWrapper<FundType> queryWrapper = QueryGenerator.initQueryWrapper(fundType, req.getParameterMap());
		Page<FundType> page = new Page<FundType>(pageNo, pageSize);
		IPage<FundType> pageList = fundTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param fundType
	 * @return
	 */
	@AutoLog(value = "资金类型-添加")
	@ApiOperation(value="资金类型-添加", notes="资金类型-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody FundType fundType) {
		fundTypeService.save(fundType);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param fundType
	 * @return
	 */
	@AutoLog(value = "资金类型-编辑")
	@ApiOperation(value="资金类型-编辑", notes="资金类型-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody FundType fundType) {
		fundTypeService.updateById(fundType);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "资金类型-通过id删除")
	@ApiOperation(value="资金类型-通过id删除", notes="资金类型-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		fundTypeService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "资金类型-批量删除")
	@ApiOperation(value="资金类型-批量删除", notes="资金类型-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.fundTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "资金类型-通过id查询")
	@ApiOperation(value="资金类型-通过id查询", notes="资金类型-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		FundType fundType = fundTypeService.getById(id);
		if(fundType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(fundType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param fundType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FundType fundType) {
        return super.exportXls(request, fundType, FundType.class, "资金类型");
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
        return super.importExcel(request, response, FundType.class);
    }

}
