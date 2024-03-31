package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.entity.Doctor;
import com.enumeration.ResultCodeEnum;
import com.mapper.DoctorMapper;
import com.response.BaseResponse;
import com.service.DoctorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.print.Doc;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 医生表  前端控制器
 * </p>
 *
 * @author hospital
 * @since 2021-04-24
 */
@Api(tags = "医生")
@RestController
@RequestMapping("/doctor")
public class DoctorController {
    BaseResponse baseResponse = new BaseResponse();

    @Autowired
    private DoctorService doctorService;

    @Resource
    private DoctorMapper doctorMapper;

    @ApiOperation(value = "获取所有医生列表")
    @GetMapping("/list")
    public BaseResponse list(HttpSession session){
        if (session.getAttribute("id") != null) {
            Collection<Doctor> doctors = this.doctorService.list();
            return baseResponse.returnResponse(doctors,
                    ResultCodeEnum.DB_FIND_SUCCESS.getCode(),
                    ResultCodeEnum.DB_FIND_SUCCESS.getDesc());
        } else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_INVALID.getCode(),
                    ResultCodeEnum.LOGIN_INVALID.getDesc());
        }
    }

    @ApiOperation(value = "获取医生个人信息")
    @GetMapping("/find/{id}") //通过id返回医生信息
    public BaseResponse find(@PathVariable("id") Integer id,
                             HttpSession session){
        if(session.getAttribute("id") != null){
            Doctor doctor = this.doctorService.getById(id);
            return baseResponse.returnResponse(doctor,
                    ResultCodeEnum.DB_FIND_SUCCESS.getCode(),
                    ResultCodeEnum.DB_FIND_SUCCESS.getDesc());
        }
        else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_FAILURE.getCode(),
                    ResultCodeEnum.LOGIN_FAILURE.getDesc());
        }
    }

    @ApiOperation(value = "获取医生姓名")
    @GetMapping("/findName/{id}")
    public BaseResponse findName(@PathVariable("id") Integer id,
                                 HttpSession session){
        if (session.getAttribute("id") != null) {
            Doctor doctor = this.doctorService.getById(id);
            if(doctor != null){
                String doName = doctor.getName();
                return baseResponse.returnResponse(doName,
                        ResultCodeEnum.DB_FIND_SUCCESS.getCode(),
                        ResultCodeEnum.DB_FIND_SUCCESS.getDesc());
            }
            else{
                return baseResponse.returnResponse(null,
                        ResultCodeEnum.DB_FIND_FAILURE.getCode(),
                        ResultCodeEnum.DB_FIND_FAILURE.getDesc());
            }
        }
        else{
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_FAILURE.getCode(),
                    ResultCodeEnum.LOGIN_FAILURE.getDesc());
        }
    }


    @ApiOperation(value = "医生登录")
    @PostMapping("/login")
    public BaseResponse testLogin(@RequestParam("id") Integer id,
                             @RequestParam("password") String password,
                             HttpSession session){
        QueryWrapper wrapper = new QueryWrapper();
        Map<String ,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("password",password);
        wrapper.allEq(map);
        Doctor doctor = this.doctorService.getOne(wrapper);
        if(doctor != null){
            session.setAttribute("id", doctor.getId());
            return baseResponse.returnResponse(doctor,
                    ResultCodeEnum.LOGIN_SUCCESS.getCode(),
                    ResultCodeEnum.LOGIN_SUCCESS.getDesc());
        }
        else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_FAILURE.getCode(),
                    ResultCodeEnum.LOGIN_FAILURE.getDesc());
        }
    }

    @ApiOperation(value = "获取某科室下所有医生")
    @PostMapping("/allDoctor")
    public BaseResponse findAllDoctor(@RequestParam("valueOffice") Integer office,
                                      HttpSession session){
        if (session.getAttribute("id") != null) {
            QueryWrapper wrapper = new QueryWrapper();
            Map<String ,Object> map = new HashMap<>();
            map.put("office",office);
            wrapper.allEq(map);
            Collection<Doctor> doctors = this.doctorService.list(wrapper);
            if(doctors != null){
                return baseResponse.returnResponse(doctors,
                        ResultCodeEnum.DB_FIND_SUCCESS.getCode(),
                        ResultCodeEnum.DB_FIND_SUCCESS.getDesc());
            }
            else{
                return baseResponse.returnResponse(null,
                        ResultCodeEnum.DB_FIND_FAILURE.getCode(),
                        ResultCodeEnum.DB_FIND_FAILURE.getDesc());
            }
        }
        else{
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_INVALID.getCode(),
                    ResultCodeEnum.LOGIN_INVALID.getDesc());
        }
    }


    @ApiOperation(value = "修改密码")
    @PostMapping("/changePwd")
    public BaseResponse changePwd(@RequestParam("id") Long id,
                             @RequestParam("password") String password,
                                  HttpSession session){
        if (session.getAttribute("id") != null) {
            UpdateWrapper<Doctor> updateWrapper = new UpdateWrapper<>();
            Map<String ,Object> map = new HashMap<>();
            map.put("id",id);
            updateWrapper.allEq(map).set("password",password);
            doctorMapper.update(null,updateWrapper);
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.DB_UPDATE_SUCCESS.getCode(),
                    ResultCodeEnum.DB_UPDATE_SUCCESS.getDesc());
        }
        else {
            return baseResponse.returnResponse(null,
                    ResultCodeEnum.LOGIN_INVALID.getCode(),
                    ResultCodeEnum.LOGIN_INVALID.getDesc());
        }
    }
}
