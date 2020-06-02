package org.jeecg.modules.blog.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.blog.entity.BlogArticle;
import org.jeecg.modules.blog.service.IBlogArticleService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 文章
 * @Author: jeecg-boot
 * @Date:   2020-04-07
 * @Version: V1.0
 */
@Api(tags="文章")
@RestController
@RequestMapping("/blog/blogArticle")
@Slf4j
public class BlogArticleController extends JeecgController<BlogArticle, IBlogArticleService> {
	@Autowired
	private IBlogArticleService blogArticleService;
	
	/**
	 * 分页列表查询
	 *
	 * @param blogArticle
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "文章-分页列表查询")
	@ApiOperation(value="文章-分页列表查询", notes="文章-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BlogArticle blogArticle,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BlogArticle> queryWrapper = QueryGenerator.initQueryWrapper(blogArticle, req.getParameterMap());
		Page<BlogArticle> page = new Page<BlogArticle>(pageNo, pageSize);
		IPage<BlogArticle> pageList = blogArticleService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param blogArticle
	 * @return
	 */
	@AutoLog(value = "文章-添加")
	@ApiOperation(value="文章-添加", notes="文章-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BlogArticle blogArticle) {
		blogArticleService.save(blogArticle);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param blogArticle
	 * @return
	 */
	@AutoLog(value = "文章-编辑")
	@ApiOperation(value="文章-编辑", notes="文章-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BlogArticle blogArticle) {
		blogArticleService.updateById(blogArticle);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文章-通过id删除")
	@ApiOperation(value="文章-通过id删除", notes="文章-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		blogArticleService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "文章-批量删除")
	@ApiOperation(value="文章-批量删除", notes="文章-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.blogArticleService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文章-通过id查询")
	@ApiOperation(value="文章-通过id查询", notes="文章-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BlogArticle blogArticle = blogArticleService.getById(id);
		if(blogArticle==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(blogArticle);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param blogArticle
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BlogArticle blogArticle) {
        return super.exportXls(request, blogArticle, BlogArticle.class, "文章");
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
        return super.importExcel(request, response, BlogArticle.class);
    }

	 @AutoLog("文章发表-发表文章")
	 @ApiOperation(value="文章发表-发表文章",notes = "发表文章-发表文章")
	 @PostMapping("/articles/publish")
	 public Result<?> publishArticle(@RequestBody BlogArticle blogArticle){
    	log.info("blogArticle: "+blogArticle.toString());
		 String sysOrgCode = getUser().getOrgCode();
		 blogArticle.setSysOrgCode(sysOrgCode);
		 blogArticle.setCreateBy(getUser().getUsername());
		 blogArticle.setCategoryId(blogArticle.getBlogCategory().getId());
		 blogArticleService.save(blogArticle);
		 return Result.ok("添加成功！");
	 }

	 protected LoginUser getUser(){
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 return sysUser;
	 }
}
