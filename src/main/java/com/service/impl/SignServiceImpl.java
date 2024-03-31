package com.service.impl;

import com.entity.Sign;
import com.mapper.SignMapper;
import com.service.SignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 签约表  服务实现类
 * </p>
 *
 * @author hospital
 * @since 2021-04-24
 */
@Service
public class SignServiceImpl extends ServiceImpl<SignMapper, Sign> implements SignService {

}
