package cn.tzqwz.cache;

import cn.tzqwz.config.ApplicationConfig;
import cn.tzqwz.zk.ZKUtils;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * IM服务地址缓存
 */
@Component
public class IMServerCache {


    @Autowired
    private ZKUtils zkUtils;

    @Autowired
    private LoadingCache<String,String> loadingCache;//使用Google提供的缓存类

    /**
     * 获取所有的服务节点
     */
    public List<String> findZKServerNode(String rootNodePath){
        List<String> nodeList = new ArrayList<>();
        //判断缓存中是否有数据
        if(loadingCache.size()==0) {
            //没有数据,从zk上获取所有的服务节点
            List<String> childNodeList = zkUtils.getChildNode(rootNodePath);
            for (String childNode : childNodeList) {
                String[] split = childNode.split("-");
                addCache(split[1]);
                nodeList.add(split[1]);
            }
            return nodeList;
        }
            //将缓存中的数据取出
            ConcurrentMap<String, String> childNodeMap = loadingCache.asMap();
            for (Map.Entry<String, String> entry : childNodeMap.entrySet()) {
                nodeList.add(entry.getKey());
            }
        return nodeList;
    }

    /**
     * 将服务节点信息添加进入到缓存中
     * @param key
     */
    public void addCache(String key){
        loadingCache.put(key,key);
    }


    /**
     * 触发更新事件更新缓存
     * @param currentChilds
     */
    public void updateCache(List<String> currentChilds) {
        //将缓存中的数据删除
        loadingCache.invalidateAll();
        //将服务节点数据添加到缓存中
            for (String childNode : currentChilds) {
                String[] split = childNode.split("-");
                addCache(split[1]);
            }
    }
}
