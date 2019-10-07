package cn.tzqwz.zk;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ZKUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(ZKUtils.class);

    @Autowired
    private ZkClient zkClient;

    /**
     * 获取指定父节点的子节点
     * @return
     */
    public List<String> getChildNode(String rootNode){
        List<String> childNodeList = zkClient.getChildren(rootNode);
        LOGGER.info("获取父节点[{}]下面的子节点列表成功:{}",rootNode, JSON.toJSONString(childNodeList));
        return childNodeList;
    }

    /**
     * 给指定目录节点注册事件
     * @param path
     */
    public void subscribeEvent(String path){
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                LOGGER.info("清除/更新本地缓存 parentPath=【{}】,currentChilds=【{}】", parentPath,currentChilds.toString());
            }
        });
    }


}
