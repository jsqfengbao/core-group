package org.jeecg.modules.blog.controller;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.blog.bo.*;
import org.jeecg.modules.blog.entity.BlogArticle;
import org.jeecg.modules.blog.entity.BlogCategory;
import org.jeecg.modules.blog.entity.BlogTag;
import org.jeecg.modules.blog.service.IBlogArticleService;
import org.jeecg.modules.blog.service.IBlogCategoryService;
import org.jeecg.modules.blog.service.IBlogCommentService;
import org.jeecg.modules.blog.service.IBlogTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author jinsq
 *
 * @date 2020/4/9 20:47
 */
@Api(tags="博客接口")
@RestController
@RequestMapping("/blog/api")
@Slf4j
public class BlogApiController {
    @Autowired
    private IBlogArticleService blogArticleService;
    @Autowired
    private IBlogCategoryService blogCategoryService;
    @Autowired
    private IBlogCommentService blogCommentService;
    @Autowired
    private IBlogTagService blogTagService;

    @AutoLog("文章-列表")
    @ApiOperation(value = "文章-分页查询",notes = "文章-分页列表查询")
    @GetMapping("/articles")
    public Result<?> queryArticles(@RequestParam(name="pageNumber", defaultValue="1") Integer pageNumber,
                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                   @RequestParam(name="tagId",required = false) String tagId,
                                   @RequestParam(name="categoryId",required = false) String categoryId,
                                   @RequestParam(name="year",required = false) String year,
                                   @RequestParam(name="month",required = false) String month){
       int currIndex = (pageNumber -1) * pageSize;
        List<ArticleBo> articleBoList = blogArticleService.queryArticlesApi(tagId,categoryId,year,month,currIndex,pageSize);
        return Result.ok(articleBoList);
    }

    @AutoLog("文章-最热文章列表")
    @ApiOperation(value = "文章-最热文章列表",notes = "文章-最热文章列表")
    @GetMapping("/articles/hot")
    public Result<?> queryArticlesApiByViewCountsAndLimit(){
        List<ArticleHotBo> articleHotBoList = blogArticleService.queryArticlesApiByViewCountsAndLimit();
        return Result.ok(articleHotBoList);
    }

    @AutoLog("文章-最新文章列表")
    @ApiOperation(value="文章-最新文章列表", notes = "文章-最新文章列表")
    @GetMapping("/articles/new")
    public Result<?> queryArticlesApiByCreateTimeAndLimit(){
        List<ArticleHotBo> articleHotBoList = blogArticleService.queryArticlesApiByCreateTimeAndLimit();
        return Result.ok(articleHotBoList);
    }

    @AutoLog("文章-文章详情")
    @ApiOperation(value="文章-文章详情",notes = "文章-文章详情")
    @GetMapping("/article/view/{articleId}")
    public Result<?> queryArticlesApiById(@PathVariable("articleId") String articleId){
        BlogArticle articleo = blogArticleService.queryArticlesApiById(articleId);
        int commentCounts = articleo.getCommentCounts();
        articleo.setCommentCounts(commentCounts++);
        blogArticleService.updateById(articleo);
        return Result.ok(articleo);
    }

    @AutoLog("文章-文章分类列表")
    @ApiOperation(value = "文章-文章分类列表",notes = "文章-文章分类列表")
    @GetMapping("/articles/category/{categoryId}")
    public Result<?> queryArticlesApiByCategoryId(@RequestParam(name="pageNumber", defaultValue="1") Integer pageNumber,
                                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                  @PathVariable("categoryId") String categoryId){
        int currIndex = (pageNumber -1) * pageSize;
        List<ArticleBo> articleBoList = blogArticleService.queryArticlesApiByCategoryId(currIndex,pageSize,categoryId);
        return Result.ok(articleBoList);
    }
    @AutoLog("文章-文章标签列表")
    @ApiOperation(value = "文章-文章标签列表",notes = "文章-文章标签列表")
    @GetMapping("/articles/tag/{tagId}")
    public Result<?> queryArticlesApiByTagId(@RequestParam(name="pageNumber", defaultValue="1") Integer pageNumber,
                                             @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                             @PathVariable("tagId") String tagId){
        int currIndex = (pageNumber -1) * pageSize;
        List<ArticleBo> articleBoList = blogArticleService.queryArticlesApiByTagId(currIndex,pageSize,tagId);
        return Result.ok(articleBoList);
    }

    @AutoLog("文章-按年月归档")
    @ApiOperation(value = "文章-按年月归档",notes = "文章-按年月归档")
    @GetMapping("/archives")
    public Result<?> queryArticlesApiByArchives(){
        List<ArchivesBo> archivesBoList = blogArticleService.queryArticlesApiByArchives();
        return Result.ok(archivesBoList);
    }

    @AutoLog("标签-所有标签列表")
    @ApiOperation(value = "标签-所有标签列表",notes = "标签-所有标签列表")
    @GetMapping("/tags")
    public Result<?> listTagsByArticleUse(){
        List<BlogTag> blogTags = blogTagService.listTagsByArticleUse();
        return Result.ok(blogTags);
    }

    @AutoLog("标签-最热标签")
    @ApiOperation(value = "标签-最热标签",notes = "标签-最热标签")
    @GetMapping("/tags/hot")
    public Result<?> listHotTagsByArticleUse(){
        List<BlogTag> blogTags = blogTagService.listHotTagsByArticleUse();
        return Result.ok(blogTags);
    }

    @AutoLog("标签-根据ID获取详情")
    @ApiOperation(value = "标签-根据ID获取详情",notes = "标签-根据ID获取详情")
    @GetMapping("/tags/detail/{tagId}")
    public Result<?> getTagsDetailByTagId(@PathVariable("tagId") String tagId){
        TagBo tagBo = blogTagService.getTagsDetailByTagId(tagId);
        return Result.ok(tagBo);
    }

    @AutoLog("文章分类-列表")
    @ApiOperation(value = "文章分类-列表",notes = "文章分类-列表")
    @GetMapping("/categorys")
    public Result<?> queryCategorysApiList(){
        List<BlogCategory> blogCategoryList = blogCategoryService.queryCategorysApiList();
        return Result.ok(blogCategoryList);
    }

    @AutoLog("文章分类-根据ID获取分类详情")
    @ApiOperation(value = "文章分类-根据ID获取分类详情",notes = "文章分类-根据ID获取分类详情")
    @GetMapping("/category/{categoryId}")
    public Result<?> queryCategoryApiById(@PathVariable("categoryId") String categoryId){
        BlogCategory blogCategory = blogCategoryService.queryCategoryApiById(categoryId);
        return Result.ok(blogCategory);
    }

    @AutoLog("评论-根据文章ID获取")
    @ApiOperation(value = "评论-根据文章ID获取",notes = "评论-根据文章ID获取")
    @GetMapping("/comments/article/{articleId}")
    public Result<?> getCommentsByArticleId(@PathVariable("articleId") String articleId) {
        List<CommentBo> commentBoList = blogCommentService.getCommentsByArticleId(articleId);
        return Result.ok(commentBoList);
    }
}
