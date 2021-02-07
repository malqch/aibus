package com.wntime.ec.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wing
 * @create 2019-12-09 17:46
 * 服务端接口返回统一格式
 */
@Data
@NoArgsConstructor
public class FsmsRsp<E> {
    int code;
    String message;
    String detailMessage;
    E data;

    public FsmsRsp(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public FsmsRsp(int code, String message, String detailMessage) {
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
    }
}
