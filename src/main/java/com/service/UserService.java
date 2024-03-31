package com.service;

import com.dto.UserRegisterDTO;
import com.entity.Appointment;
import com.entity.Doctor;
import com.entity.Schedule;
import com.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.sql.Date;
import java.util.Collection;

/**
 * <p>
 * 用户  服务类
 * </p>
 *
 * @author hospital
 * @since 2021-04-24
 */
public interface UserService extends IService<User> {
    User userLogin(String mobile, String password);

    User userRegister(UserRegisterDTO dto);

    User getUserInfo(int id);

    void updateUserInfo(User user);

    Collection<Doctor> getDoctor();

    Collection<Doctor> pagingGetDoctor(int page,int size);

    Doctor showDoctor(Integer do_id);

    int signDoctor(long user_id, long do_id);

    int rescindDoctor(long user_id, long do_id, String reason);

    int appointmentDoctor(long uid, String uname, long do_id, String do_name, Date appointment, int time);

    int cancelAppointment(long uid, long do_id, Date appointment, int time);

    Collection<Schedule> getSchedule(long uid);

    Collection<Appointment> getAppointment(long uid);

}
