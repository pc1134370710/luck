package com.luck.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luck.domain.req.GetUserListReq;
import com.luck.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pc
 * @since 2023-02-20
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    Page<UserInfo> getUserList(Page page, GetUserListReq getUserListReq);
}
