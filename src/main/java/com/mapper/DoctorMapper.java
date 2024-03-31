package com.mapper;

import com.entity.Doctor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 医生表  Mapper 接口
 * </p>
 *
 * @author hospital
 * @since 2021-04-24
 */
@Mapper
@Component
public interface DoctorMapper extends BaseMapper<Doctor> {

    /**
     * 根据初始记录数、每页记录数 获取医生列表
     */
    @Select("SELECT * FROM Doctor limit #{page},#{size}")
    List<Doctor> pagingGetDoctor(int page, int size);

}
