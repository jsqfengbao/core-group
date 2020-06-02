package org.jeecg.modules.blog.service.impl;

import org.jeecg.modules.blog.bo.ArchivesBo;
import org.jeecg.modules.blog.bo.ArticleBo;
import org.jeecg.modules.blog.bo.ArticleHotBo;
import org.jeecg.modules.blog.entity.BlogArticle;
import org.jeecg.modules.blog.mapper.BlogArticleMapper;
import org.jeecg.modules.blog.service.IBlogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 文章
 * @Author: jeecg-boot
 * @Date:   2020-04-07
 * @Version: V1.0
 */
@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements IBlogArticleService {

    @Autowired
    private BlogArticleMapper blogArticleMapper;

    public List<ArticleBo> queryArticlesApi(String tagId,String categoryId,String year,String month,int currIndex,int pageSize){
        return blogArticleMapper.queryArticlesApi(tagId,categoryId,year,month,currIndex,pageSize);
    }

    public List<ArticleHotBo> queryArticlesApiByViewCountsAndLimit(){
        return blogArticleMapper.queryArticlesApiByViewCountsAndLimit();
    }

    public List<ArticleHotBo> queryArticlesApiByCreateTimeAndLimit(){
        return blogArticleMapper.queryArticlesApiByCreateTimeAndLimit();
    }

    public BlogArticle queryArticlesApiById(String articleId){
        return blogArticleMapper.queryArticlesApiById(articleId);
    }

    public List<ArticleBo> queryArticlesApiByCategoryId(int currIndex,int pageSize,String categoryId){
        return blogArticleMapper.queryArticlesApiByCategoryId(currIndex,pageSize,categoryId);
    }

    public List<ArticleBo> queryArticlesApiByTagId(int currIndex,int pageSize,String tagId){
        return blogArticleMapper.queryArticlesApiByTagId(currIndex,pageSize,tagId);
    }

    public List<ArchivesBo> queryArticlesApiByArchives(){
        return blogArticleMapper.queryArticlesApiByArchives();
    }
}
