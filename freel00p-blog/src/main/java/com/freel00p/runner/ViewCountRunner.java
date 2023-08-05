package com.freel00p.runner;

import com.freel00p.config.RedisCache;
import com.freel00p.domain.entity.Article;
import com.freel00p.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.freel00p.constants.RedisConstants.ARTICLE_VIEW_COUNT;

/**
 * ViewCountRunner
 *
 * @author fj
 * @since 2023/8/5 11:20
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;


    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //启动时将博客浏览量放入缓存
        //查询博客信息
        List<Article> articleList = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articleList.stream().collect(Collectors.toMap(article ->
                        article.getId().toString(), article -> article.getViewCount().intValue()
        ));
        //存储到redis中
        redisCache.setCacheMap(ARTICLE_VIEW_COUNT,viewCountMap);
    }
}
