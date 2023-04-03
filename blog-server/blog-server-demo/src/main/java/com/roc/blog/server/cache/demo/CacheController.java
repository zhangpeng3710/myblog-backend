package com.roc.blog.server.cache.demo;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * spring cache 参考 https://cloud.tencent.com/developer/article/1912986?areaSource=&traceId=
 *
 * @Author: Zhang Peng
 * @Date: 2023/3/31 13:25
 */

@RestController
public class CacheController {
    @Resource
    private CacheService userDao;

    /**
     * 查询出一条数据并且添加到缓存
     *
     * @param userId
     * @return
     */
    @RequestMapping("/getUser")
    @Cacheable(value = "userCache", key = "#userId +'-haha'") //generate cache key "userCache::1-haha"
    public CacheUser getPrud(@RequestParam(required = true) String userId) {
        System.out.println("如果没有缓存，就会调用下面方法，如果有缓存，则直接输出，不会输出此段话");
        return userDao.getUser(Integer.parseInt(userId));
    }

    /**
     * 删除一个缓存
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/deleteUser")
    @CacheEvict("userCache")
    public String deleteUser(@RequestParam(required = true) String userId) {
        return "删除成功";
    }

    /**
     * 添加一条保存的数据到缓存，缓存的key是当前user的id
     *
     * @param user
     * @return
     */
    @RequestMapping("/saveUser")
    @CachePut(value = "userCache", key = "#result.userId + '-' + #result.name") //generate cache key "userCache::1-name"
    public CacheUser saveUser(CacheUser user) {
        return user;
    }


    /**
     * 返回结果userPassword中含有nocache字符串就不缓存
     *
     * @param userId
     * @return
     */
    @RequestMapping("/getUser2")
    @CachePut(value = "userCache", unless = "#result.password.contains('nocache')")
    public CacheUser getUser2(@RequestParam(required = true) String userId) {
        System.out.println("如果走到这里说明，说明缓存没有生效！");
        CacheUser user = new CacheUser(Integer.parseInt(userId), "name_nocache" + userId, "nocache");
        return user;
    }


    @RequestMapping("/getUser3")
    @Cacheable(value = "userCache", key = "#root.targetClass.getName() + #root.methodName + #userId")
    public CacheUser getUser3(@RequestParam(required = true) String userId) {
        System.out.println("如果第二次没有走到这里说明缓存被添加了");
        return userDao.getUser(Integer.parseInt(userId));
    }

}
