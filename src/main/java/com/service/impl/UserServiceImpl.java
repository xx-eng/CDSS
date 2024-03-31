package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dto.UserRegisterDTO;
import com.entity.*;
import com.mapper.*;
import com.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * 用户  服务实现类
 * </p>
 *
 * @author hospital
 * @since 2021-04-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private SignMapper signMapper;
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public User userLogin(String mobile, String password){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("mobile",mobile).eq("password",password);
        return userMapper.selectOne(userQueryWrapper);
    }

    @Override
    public User userRegister(UserRegisterDTO dto){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        QueryWrapper<User> userQueryWrapper2 = new QueryWrapper<>();
        userQueryWrapper.eq("id_card",dto.getIdCard());
        userQueryWrapper2.eq("mobile",dto.getMobile());
        if(userMapper.selectOne(userQueryWrapper) != null && userMapper.selectOne(userQueryWrapper2) != null){
            //身份证号和手机号不能有相同的，表明该用户已注册过
            return null;
        }
        else{
            //根据系统需要，姓名、性别、密码、手机号、身份证号、宿舍区号应均为必填信息。可前端表单控制
            User user = new User();
            user.setPassword(dto.getPassword());
            user.setName(dto.getName());
            user.setSex(dto.getSex());
            user.setAge(dto.getAge());
            user.setMobile(dto.getMobile());
            user.setIdCard(dto.getIdCard());
            user.setAddressId(dto.getAddressId());
            user.setAddress(dto.getAddress());
            user.setMedicalHistory(dto.getMedicalHistory());
            user.setAllergy(dto.getAllergy());
            userMapper.insert(user);
            return user;
        }
    }

    @Override
    public User getUserInfo(int id){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id",id);
        return userMapper.selectOne(userQueryWrapper);
    }

    @Override
    public void updateUserInfo(User user){
        userMapper.updateById(user);
    }

    @Override
    public Collection<Doctor> getDoctor(){
        QueryWrapper<Doctor> doctorQueryWrapper = new QueryWrapper<>();
        return doctorMapper.selectList(doctorQueryWrapper);
    }

    @Override
    public Collection<Doctor> pagingGetDoctor(int page,int size){
        return doctorMapper.pagingGetDoctor(page, size);
    }

    @Override
    public Doctor showDoctor(Integer do_id){
        QueryWrapper<Doctor> doctorQueryWrapper = new QueryWrapper<>();
        doctorQueryWrapper.eq("id",do_id);
        return doctorMapper.selectOne(doctorQueryWrapper);
    }

    @Override
    public int signDoctor(long user_id, long do_id){
        QueryWrapper<Doctor> doctorQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Sign> signQueryWrapper = new QueryWrapper<>();
        doctorQueryWrapper.eq("id",do_id);
        signQueryWrapper.eq("user_id",user_id);
        if(doctorMapper.selectOne(doctorQueryWrapper) != null){
            int remain = doctorMapper.selectOne(doctorQueryWrapper).getRemainNum();
            if(signMapper.selectOne(signQueryWrapper) != null
                    && (signMapper.selectOne(signQueryWrapper).getStatus() == 1 ||
                    signMapper.selectOne(signQueryWrapper).getStatus() == 2)){
                //说明已签约过医生且为签约成功或等待解约状态，返回-1
                return -1;
            }
            else if(remain > 0){//未签约过医生或已解约，且选择的医生剩余可签约数大于0
                Sign sign = new Sign();
                sign.setUserId(user_id);
                sign.setDoctorId(do_id);
                sign.setStatus(1);
                signMapper.insert(sign);
                Doctor doctor = doctorMapper.selectOne(doctorQueryWrapper);
                remain--;
                doctor.setRemainNum(remain);
                doctorMapper.updateById(doctor);
                return 1;//返回1，签约成功
            }
            else{//医生没有可签约名额，返回-2
                return -2;
            }
        }
        else
            return -3;//空指针异常，不存在该医生
    }

    @Override
    public int rescindDoctor(long user_id, long do_id, String reason){
        //用户id，医生id，解约原因
        QueryWrapper<Sign> signQueryWrapper = new QueryWrapper<>();
        signQueryWrapper.eq("user_id",user_id).eq("doctor_id",do_id);
        Sign sign = signMapper.selectOne(signQueryWrapper);
        if(sign != null){
            sign.setStatus(2);//将签约状态改为等待解约状态
            sign.setReason(reason);//添加解约原因
            signMapper.updateById(sign);
            return 1;
        }
        else
            return -1;//空指针异常
    }

    @Override
    public int appointmentDoctor(long uid, String uname, long do_id, String do_name, Date appointment, int time){
        //预约医生，需要先检查排班表中已选时间内该医生是否还有预约容量，
        //如果有，还需检查是否已有相同的预约记录，
        //没有则需要在预约表中添加预约情况记录项，并将排班表中预约剩余量-1，返回1

        QueryWrapper<Schedule> scheduleQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Appointment> appointmentQueryWrapper = new QueryWrapper<>();
        int weekday = DateUtils.dateToWeek(appointment);//获取星期
        scheduleQueryWrapper.eq("doctor_id",do_id).eq("weekday",weekday).eq("time",time);
        Schedule schedule = scheduleMapper.selectOne(scheduleQueryWrapper);
        appointmentQueryWrapper.eq("user_id",uid).eq("doctor_id",do_id)
                .eq("appointment",appointment).eq("time",time);
        Appointment appoint = appointmentMapper.selectOne(appointmentQueryWrapper);
        if(schedule != null){
            if(schedule.getRemainNumber() < 1){
                return -1;
            }
            else if(appoint == null){
                schedule.setRemainNumber(schedule.getRemainNumber()-1);
                scheduleMapper.updateById(schedule);

                Appointment app = new Appointment();
                app.setUserId(uid);
                app.setUserName(uname);
                app.setDoctorId(do_id);
                app.setDoctorName(do_name);
                app.setAppointment(appointment);
                app.setTime(time);
                appointmentMapper.insert(app);
                return  1;
            }
            else {
                return -1;
            }
        }
        else
            return -1;
    }

    @Override
    public int cancelAppointment(long uid, long do_id, Date appointment, int time){
        //取消预约，取消尚未到时间的预约
        //删除预约表中对应记录项，并将对应医生排班表中对应项的剩余容量+1
        QueryWrapper<Appointment> appointmentQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Schedule> scheduleQueryWrapper = new QueryWrapper<>();
        appointmentQueryWrapper.eq("user_id",uid).eq("doctor_id",do_id)
                .eq("appointment",appointment).eq("time",time);
        Appointment app = appointmentMapper.selectOne(appointmentQueryWrapper);
        int weekday = DateUtils.dateToWeek(appointment);
        scheduleQueryWrapper.eq("doctor_id",do_id).eq("weekday",weekday).eq("time",time);
        Schedule schedule = scheduleMapper.selectOne(scheduleQueryWrapper);
        if(app != null && schedule !=null){
            appointmentMapper.deleteById(app);
            schedule.setRemainNumber(schedule.getRemainNumber()+1);
            scheduleMapper.updateById(schedule);
            return 1;
        }
        else
            return -1;
    }

    @Override
    public Collection<Schedule> getSchedule(long uid){
        QueryWrapper<Sign> signQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Schedule> scheduleQueryWrapper = new QueryWrapper<>();
        signQueryWrapper.eq("user_id",uid);
        Sign sign = signMapper.selectOne(signQueryWrapper);
        long do_id = sign.getDoctorId();//找到用户签约医生的id
        scheduleQueryWrapper.eq("doctor_id",do_id);
        return scheduleMapper.selectList(scheduleQueryWrapper);
    }

    @Override
    public Collection<Appointment> getAppointment(long uid){
        //查看预约情况。由于签约医生可帮患者预约本院其他医生，因此不需限定医生id
        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",uid);
        return appointmentMapper.selectList(queryWrapper);
    }
}
