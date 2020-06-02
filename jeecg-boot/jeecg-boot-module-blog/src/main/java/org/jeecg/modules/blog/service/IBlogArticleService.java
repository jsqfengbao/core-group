package org.jeecg.modules.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.blog.bo.ArchivesBo;
import org.jeecg.modules.blog.bo.ArticleBo;
import org.jeecg.modules.blog.bo.ArticleHotBo;
import org.jeecg.modules.blog.entity.BlogArticle;

import java.util.List;

/**
 * @Description: 文章
 * @Author: jeecg-boot
 * @Date:   2020-04-07
 * @Version: V1.0
 */
public interface IBlogArticleService extends IService<BlogArticle> {

    public List<ArticleBo> queryArticlesApi(String tagId,String categoryId,String year,String month,int currIndex,int pageSize);

    public List<ArticleHotBo> queryArticlesApiByViewCountsAndLimit();

    public List<ArticleHotBo> queryArticlesApiByCreateTimeAndLimit();

    public BlogArticle queryArticlesApiById(String articleId);

    public List<ArticleBo> queryArticlesApiByCategoryId(int currIndex,int pageSize,String categoryId);

    public List<ArticleBo> queryArticlesApiByTagId(int currIndex,int pageSize,String tagId);

    public List<ArchivesBo> queryArticlesApiByArchives();
}
