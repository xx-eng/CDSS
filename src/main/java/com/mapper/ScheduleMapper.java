package com.mapper;

import com.entity.Schedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 排班表  Mapper 接口
 * </p>
 *
 * @author hospital
 * @since 2021-04-24
 */
@Mapper
@Component
public interface ScheduleMapper extends BaseMapper<Schedule> {

}
