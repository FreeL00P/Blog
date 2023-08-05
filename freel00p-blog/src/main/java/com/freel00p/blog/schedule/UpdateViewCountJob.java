package com.freel00p.blog.schedule;

import com.freel00p.blog.config.RedisCache;
import com.freel00p.domain.entity.Article;
import com.freel00p.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.freel00p.constants.RedisConstants.ARTICLE_VIEW_COUNT;

/**
 * TestJob
 *
 * @author fj
 * @since 2023/8/5 11:16
 */
@Component
public class UpdateViewCountJob {

//    @Scheduled(cron = "0/5 * * * * ?")
//    public void testJob(){
//        //要执行的代码
//        System.out.println("定时任务执行了");
//    }
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0/10 * * * ?")//CRON 表达式每10分钟将redis中的信息更新到数据库
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(ARTICLE_VIEW_COUNT);

        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articles);
    }
}
