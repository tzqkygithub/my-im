package cn.tzqwz.controller;

import cn.tzqwz.common.base.controller.BaseController;
import cn.tzqwz.common.base.res.BaseResponse;
import cn.tzqwz.common.base.res.NULLResData;
import cn.tzqwz.common.dto.input.SendPushInDTO;
import cn.tzqwz.im.server.IMServer;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器端接口
 */
@RestController
public class IndexController extends BaseController<NULLResData> {

    @Autowired
    private IMServer imServer;

    /**
     * 发送消息
     * @return
     */
    @PostMapping(value = "/sendPush")
    public BaseResponse<NULLResData> sendPush(@RequestBody SendPushInDTO sendPushInDTO){
        //向客户端发送消息
        imServer.sendPush(sendPushInDTO);
        return setResultSuccess(sendPushInDTO.getReceiverUserId());
    }
}
