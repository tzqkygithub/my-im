package cn.tzqwz.cache;

import cn.tzqwz.zk.ZKUtils;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * IM服务地址缓存
 */
@Component
public class IMServerCache {

    @Autowired
    private ZKUtils zkUtils;

    @Autowired
    private LoadingCache<String,String> loadingCache;


}
