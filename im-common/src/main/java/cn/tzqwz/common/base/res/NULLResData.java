package cn.tzqwz.common.base.res;

/**
 * 定义空的数据类
 */
public class NULLResData {

    //单例设计模式
    private static final NULLResData NULL_RES_DATA = new NULLResData();

    private NULLResData(){

    }

    public static NULLResData createNULLRespData(){
        return NULL_RES_DATA;
    }
}
