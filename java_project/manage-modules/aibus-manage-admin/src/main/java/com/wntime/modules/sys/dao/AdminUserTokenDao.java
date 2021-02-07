

package com.wntime.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.modules.sys.entity.AdminUserToken;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token

 */
@Mapper
public interface AdminUserTokenDao extends BaseMapper<AdminUserToken> {

    AdminUserToken queryByToken(String token);

    void deleteToken(String token);
}
