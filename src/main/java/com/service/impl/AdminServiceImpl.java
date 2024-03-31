package com.service.impl;

import com.entity.Admin;
import com.mapper.AdminMapper;
import com.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员  服务实现类
 * </p>
 *
 * @author hospital
 * @since 2021-04-24
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
