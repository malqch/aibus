package com.wntime.ec.common.util.param.rpc;

import com.wntime.ec.common.util.param.ReqParam;
import lombok.Data;

import java.util.Map;

@Data
public class RpcReqParam<E> extends ReqParam<E> {

    protected Map ext;

}
