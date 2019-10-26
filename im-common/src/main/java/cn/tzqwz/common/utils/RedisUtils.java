package cn.tzqwz.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Field;
import java.util.Map;

public class RedisUtils {

    private static  RedisTemplate<String,Object> redisTemplate;

    static{
        redisTemplate = SpringUtils.getBean("redisTemplate",RedisTemplate.class);
    }


    /**
     * 存储JavaBean对象
     * @param key
     * @param object
     */
    public static void putObj(String key ,Object object){
        Class<?> objClass = object.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for (Field field:fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = null;
            try {
                fieldValue = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            redisTemplate.opsForHash().put(key,fieldName,fieldValue);
        }
    }

    /**
     * 获取JavaBean的JSONStr
     * @param key
     * @return
     */
    public static String getObjJsonStr(String key){
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        String jsonStr = JSONObject.toJSONString(entries);
        return jsonStr;
    }

    /**
     *保存Set类型数据结构
     * @param key
     * @param value
     * @return
     */
    public static boolean saveSet(String key,String value){
        Long count = redisTemplate.opsForSet().add(key, value);
        if(count==0){
            return false;
        }
        return true;
    }
}
