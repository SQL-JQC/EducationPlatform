package com.qinC.ucenter.mapper;

import com.qinC.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author qinC
 * @since 2021-11-22
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer countRegister(String day);

}
