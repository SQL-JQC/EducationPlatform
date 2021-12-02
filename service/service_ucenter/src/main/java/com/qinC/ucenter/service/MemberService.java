package com.qinC.ucenter.service;

import com.qinC.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qinC.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author qinC
 * @since 2021-11-22
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);

    Member getOpenIdMember(String openid);

    Integer countRegister(String day);

}
