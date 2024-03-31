package com.service.impl;

import com.entity.Appointment;
import com.mapper.AppointmentMapper;
import com.service.AppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 预约表  服务实现类
 * </p>
 *
 * @author hospital
 * @since 2021-04-24
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

}
