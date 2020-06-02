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
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.blog.entity.BlogComment;
import org.jeecg.modules.blog.service.IBlogCommentService;
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
 * @Description: 博客评论表
 * @Author: jeecg-boot
 * @Date:   2020-04-08
 * @Version: V1.0
 */
@Api(tags="博客评论表")
@RestController
@RequestMapping("/blog/blogComment")
@Slf4j
public class BlogCommentController extends JeecgController<BlogComment, IBlogCommentService> {
	@Autowired
	private IBlogCommentService blogCommentService;

	
	/**
	 * 分页列表查询
	 *
	 * @param blogComment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "博客评论表-分页列表查询")
	@ApiOperation(value="博客评论表-分页列表查询", notes="博客评论表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BlogComment blogComment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BlogComment> queryWrapper = QueryGenerator.initQueryWrapper(blogComment, req.getParameterMap());
		Page<BlogComment> page = new Page<BlogComment>(pageNo, pageSize);
		IPage<BlogComment> pageList = blogCommentService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param blogComment
	 * @return
	 */
	@AutoLog(value = "博客评论表-添加")
	@ApiOperation(value="博客评论表-添加", notes="博客评论表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BlogComment blogComment) {
		blogCommentService.save(blogComment);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param blogComment
	 * @return
	 */
	@AutoLog(value = "博客评论表-编辑")
	@ApiOperation(value="博客评论表-编辑", notes="博客评论表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BlogComment blogComment) {
		blogCommentService.updateById(blogComment);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "博客评论表-通过id删除")
	@ApiOperation(value="博客评论表-通过id删除", notes="博客评论表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		blogCommentService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "博客评论表-批量删除")
	@ApiOperation(value="博客评论表-批量删除", notes="博客评论表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.blogCommentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "博客评论表-通过id查询")
	@ApiOperation(value="博客评论表-通过id查询", notes="博客评论表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BlogComment blogComment = blogCommentService.getById(id);
		if(blogComment==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(blogComment);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param blogComment
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BlogComment blogComment) {
        return super.exportXls(request, blogComment, BlogComment.class, "博客评论表");
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
        return super.importExcel(request, response, BlogComment.class);
    }



}
