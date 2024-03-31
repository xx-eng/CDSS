package com.controller;


import com.dto.UserAppointDTO;
import com.dto.UserLoginDTO;
import com.dto.UserRegisterDTO;
import com.dto.UserRescindDTO;
import com.entity.Appointment;
import com.entity.Doctor;
import com.entity.Schedule;
import com.entity.User;
import com.enumeration.ResultCodeEnum;
import com.response.BaseResponse;
import com.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Date;

/**
 * <p>
 * 用户  前端控制器
 * </p>
 *
 * @author hospital
 * @since 2021-04-24
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/user")
public class UserController {
    BaseResponse baseResponse = new BaseResponse();

    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public BaseResponse userLogin(@RequestBody UserLoginDTO userLoginDTO, HttpSession session) {
        User user = userService.userLogin(userLoginDTO.getMobile(),
                userLoginDTO.getPassword());
        if (user != null) {
            session.setAttribute("id", user.getId());
            session.setAttribute("name", user.getName());
            System.out.println("set:" + session.getAttribute("id") + " " + session.getId());
            System.out.println(user);
            return baseResponse.returnResponse(user, session.getId(),
                    ResultCodeEnum.LOGIN_SUCCESS.getCode(),
                    ResultCodeEnum.LOGIN_SUCCESS.getDesc());
        } else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_FAILURE.getCode(),
                    ResultCodeEnum.LOGIN_FAILURE.getDesc());
        }
    }

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public BaseResponse register(@RequestBody UserRegisterDTO userRegisterDTO) {
        User check = userService.userRegister(userRegisterDTO);
        if (check == null) {
            System.out.println(ResultCodeEnum.REGISTER_FAILURE.getCode());
            System.out.println(ResultCodeEnum.REGISTER_FAILURE.getDesc());
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.REGISTER_FAILURE.getCode(),
                    ResultCodeEnum.REGISTER_FAILURE.getDesc());
        } else {
            return baseResponse.returnResponse(check,
                    ResultCodeEnum.REGISTER_SUCCESS.getCode(),
                    ResultCodeEnum.REGISTER_SUCCESS.getDesc());
        }
    }

    @ApiOperation(value = "查看个人信息")
    @GetMapping("/get")
    public BaseResponse getUserInfo(HttpSession session) {
        if (session.getAttribute("id") != null) {
            int id = Integer.parseInt(session.getAttribute("id").toString());
            User user = userService.getUserInfo(id);
            return baseResponse.returnResponse(user,
                    ResultCodeEnum.DB_FIND_SUCCESS.getCode(),
                    ResultCodeEnum.DB_FIND_SUCCESS.getDesc());
        } else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_INVALID.getCode(),
                    ResultCodeEnum.LOGIN_INVALID.getDesc());
        }
    }

    @ApiOperation(value = "修改个人信息")
    @PostMapping("/update")
    public BaseResponse updateUserInfo(@RequestBody User user, HttpSession session) {
        if (session.getAttribute("id") != null) {
            user.setId((long) Integer.parseInt(session.getAttribute("id").toString()));
            userService.updateUserInfo(user);
            return baseResponse.returnResponse(user,
                    ResultCodeEnum.DB_UPDATE_SUCCESS.getCode(),
                    ResultCodeEnum.DB_UPDATE_SUCCESS.getDesc());
        } else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_INVALID.getCode(),
                    ResultCodeEnum.LOGIN_INVALID.getDesc());
        }
    }

    @ApiOperation(value = "所有医生列表")
    @GetMapping("/getDoctorList")
    public BaseResponse getDoctor(HttpSession session, HttpServletRequest request) {
        System.out.println("cookies:"+ request.getCookies());
        System.out.println("session:" + session.getId() + " " + session.getAttribute("id"));
        if (session.getAttribute("id") != null) {
            Collection<Doctor> doctors = userService.getDoctor();
            return baseResponse.returnResponse(doctors,
                    ResultCodeEnum.DB_FIND_SUCCESS.getCode(),
                    ResultCodeEnum.DB_FIND_SUCCESS.getDesc());
        } else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_INVALID.getCode(),
                    ResultCodeEnum.LOGIN_INVALID.getDesc());
        }
    }

    @ApiOperation(value = "分页显示医生列表")
    @GetMapping("/pagingGetDoctorList/{page}/{size}")
    public BaseResponse pagingGetDoctor(@PathVariable int page, @PathVariable int size, HttpSession session) {
        if (session.getAttribute("id") != null) {
            Collection<Doctor> doctors = userService.pagingGetDoctor(
                    (page - 1) * size, size
            );
            return baseResponse.returnResponse(doctors,
                    ResultCodeEnum.DB_FIND_SUCCESS.getCode(),
                    ResultCodeEnum.DB_FIND_SUCCESS.getDesc());
        } else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_INVALID.getCode(),
                    ResultCodeEnum.LOGIN_INVALID.getDesc());
        }
    }

    @ApiOperation(value = "查看医生信息")
    @GetMapping("/showDoctor/{do_id}")
    public BaseResponse getDoctorInfo(@PathVariable Integer do_id, HttpSession session) {
        if (session.getAttribute("id") != null) {
            Doctor doctor = userService.showDoctor(do_id);
            return baseResponse.returnResponse(doctor,
                    ResultCodeEnum.DB_FIND_SUCCESS.getCode(),
                    ResultCodeEnum.DB_FIND_SUCCESS.getDesc());
        } else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_INVALID.getCode(),
                    ResultCodeEnum.LOGIN_INVALID.getDesc());
        }
    }

    @ApiOperation(value = "签约医生")
    @GetMapping("/signDoctor/{do_id}")
    public BaseResponse signDoctor(@PathVariable Integer do_id, HttpSession session) {
        if (session.getAttribute("id") != null) {
            int uid = Integer.parseInt(session.getAttribute("id").toString());//用户id
            int check = userService.signDoctor(uid, do_id);
            if (check == 1) {
                return baseResponse.returnResponse(check,
                        ResultCodeEnum.DB_ADD_SUCCESS.getCode(),
                        ResultCodeEnum.DB_ADD_SUCCESS.getDesc());
            } else {//check为-1表示该用户已签约医生且暂未解约；check为-2表示医生没有可签约名额
                return baseResponse.returnResponse(check,
                        ResultCodeEnum.DB_ADD_FAILURE.getCode(),
                        ResultCodeEnum.DB_ADD_FAILURE.getDesc());
            }
        } else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_INVALID.getCode(),
                    ResultCodeEnum.LOGIN_INVALID.getDesc());
        }
    }

    @ApiOperation(value = "申请解约")
    @PostMapping("/rescindDoctor")
    public BaseResponse rescindDoctor(@RequestBody UserRescindDTO dto, HttpSession session) {
        if (session.getAttribute("id") != null) {
            int uid = Integer.parseInt(session.getAttribute("id").toString());//用户id
            int check = userService.rescindDoctor(uid, dto.getDo_id(), dto.getReason());
            if (check == 1) {
                return baseResponse.returnResponse(check,
                        ResultCodeEnum.DB_UPDATE_SUCCESS.getCode(),
                        ResultCodeEnum.DB_UPDATE_SUCCESS.getDesc());
            } else {
                return baseResponse.returnResponse(check,
                        ResultCodeEnum.DB_UPDATE_FAILURE.getCode(),
                        ResultCodeEnum.DB_UPDATE_FAILURE.getDesc());
            }
        } else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_INVALID.getCode(),
                    ResultCodeEnum.LOGIN_INVALID.getDesc());
        }
    }

    @ApiOperation(value = "预约医生")
    @PostMapping("/appointmentDoctor")
    public BaseResponse appointmentDoctor(@RequestBody UserAppointDTO dto, HttpSession session) {
        if (session.getAttribute("id") != null) {
            int uid = Integer.parseInt(session.getAttribute("id").toString());
            String uname = session.getAttribute("name").toString();

            int check = userService.appointmentDoctor(uid, uname, dto.getDo_id(),
                    dto.getDo_name(), dto.getAppointment(), dto.getTime());
            if (check == 1) {
                return baseResponse.returnResponse(check,
                        ResultCodeEnum.DB_UPDATE_SUCCESS.getCode(),
                        ResultCodeEnum.DB_UPDATE_SUCCESS.getDesc());
            } else {
                return baseResponse.returnResponse(check,
                        ResultCodeEnum.DB_UPDATE_FAILURE.getCode(),
                        ResultCodeEnum.DB_UPDATE_FAILURE.getDesc());
            }
        } else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_INVALID.getCode(),
                    ResultCodeEnum.LOGIN_INVALID.getDesc());
        }
    }

    @ApiOperation(value = "取消预约")
    @PostMapping("/cancelAppointment")
    public BaseResponse cancelAppointment(@RequestBody UserAppointDTO dto, HttpSession session) {
        if (session.getAttribute("id") != null) {
            int uid = Integer.parseInt(session.getAttribute("id").toString());
            int check = userService.cancelAppointment(uid, dto.getDo_id(),
                    dto.getAppointment(), dto.getTime());
            if (check == 1) {
                return baseResponse.returnResponse(check,
                        ResultCodeEnum.DB_UPDATE_SUCCESS.getCode(),
                        ResultCodeEnum.DB_UPDATE_SUCCESS.getDesc());
            } else {
                return baseResponse.returnResponse(check,
                        ResultCodeEnum.DB_UPDATE_FAILURE.getCode(),
                        ResultCodeEnum.DB_UPDATE_FAILURE.getDesc());
            }
        } else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_INVALID.getCode(),
                    ResultCodeEnum.LOGIN_INVALID.getDesc());
        }
    }

    @ApiOperation(value = "查看签约医生排班表")
    @GetMapping("/getSchedule")
    public BaseResponse getSchedule(HttpSession session) {
        if (session.getAttribute("id") != null) {
            int uid = Integer.parseInt(session.getAttribute("id").toString());
            Collection<Schedule> schedules = userService.getSchedule(uid);
            if (schedules != null) {
                return baseResponse.returnResponse(schedules,
                        ResultCodeEnum.DB_FIND_SUCCESS.getCode(),
                        ResultCodeEnum.DB_FIND_SUCCESS.getDesc());
            } else {
                return baseResponse.returnResponse(null,
                        ResultCodeEnum.DB_FIND_FAILURE.getCode(),
                        ResultCodeEnum.DB_FIND_FAILURE.getDesc());
            }
        } else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_INVALID.getCode(),
                    ResultCodeEnum.LOGIN_INVALID.getDesc());
        }
    }

    @ApiOperation(value = "查看已预约情况")
    @PostMapping("/getAppointment")
    public BaseResponse getAppointment(HttpSession session){
        if (session.getAttribute("id") != null){
            int uid = Integer.parseInt(session.getAttribute("id").toString());
            Collection<Appointment> appointments = userService.getAppointment(uid);
            if(appointments != null){
                return baseResponse.returnResponse(appointments,
                        ResultCodeEnum.DB_FIND_SUCCESS.getCode(),
                        ResultCodeEnum.DB_FIND_SUCCESS.getDesc());
            }
            else {
                return baseResponse.returnResponse(null,
                        ResultCodeEnum.DB_FIND_FAILURE.getCode(),
                        ResultCodeEnum.DB_FIND_FAILURE.getDesc());
            }
        }
        else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_INVALID.getCode(),
                    ResultCodeEnum.LOGIN_INVALID.getDesc());
        }
    }
}

