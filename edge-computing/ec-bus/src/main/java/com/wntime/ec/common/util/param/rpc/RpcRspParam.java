package com.wntime.ec.common.util.param.rpc;

import com.wntime.ec.common.util.param.RspParam;
import lombok.Data;

import java.util.Map;

@Data
public class RpcRspParam<E> extends RspParam<E> {

	protected Map ext;

}
