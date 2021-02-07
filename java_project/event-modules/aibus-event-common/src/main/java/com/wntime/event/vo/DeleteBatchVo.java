package com.wntime.event.vo;

import lombok.Data;

/**
 * 复制自 @see com.wntime.service.common.vo.DeleteBatchVo
 * @author ysc
 * 2020/9/7 9:33
 */

@Data
public class DeleteBatchVo {
    String ids[];
    String validatePassword;
}
