package com.yoa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoa.entity.TEmployee;
import com.yoa.entity.TRabbitmqLog;
import com.yoa.entity.TRoleUser;
import com.yoa.entity.TUser;
import com.yoa.mapper.TEmployeeMapper;
import com.yoa.service.ITEmployeeService;
import com.yoa.service.ITRabbitmqLogService;
import com.yoa.service.ITRoleUserService;
import com.yoa.service.ITUserService;
import com.yoa.uitl.RabbitConstant;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liulei
 * @since 2021-05-19
 */
@Service
public class TEmployeeServiceImpl extends ServiceImpl<TEmployeeMapper, TEmployee> implements ITEmployeeService {

    @Autowired
    TEmployeeMapper tEmployeeMapper;

    @Autowired
    ITUserService itUserService;

    @Autowired
    ITRoleUserService itRoleUserService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ITRabbitmqLogService itRabbitmqLogService;


    @Override
    public List<TEmployee> getEmployees() {
        List<TEmployee> employees = tEmployeeMapper.getEmployees();
        return employees;
    }

    /**
     * 添加员工
     * */
    @Override
    public void addEmp(TEmployee tEmployee) {
        try {
            String id = UUID.randomUUID().toString().replaceAll("-","");
            tEmployee.setId(id);
            boolean save = save(tEmployee);
            System.out.println(tEmployee.getAccountNumber());
            System.out.println(save);
            if (save && tEmployee.getAccountNumber() == 1){
                //添加系统用户
                String userid = UUID.randomUUID().toString().replaceAll("-","");
                itUserService.save(new TUser(userid,tEmployee.getName(),"123456",id,null,null,null,null,null));

                //添加用户权限
                String roleUserId = UUID.randomUUID().toString().replaceAll("-","");
                itRoleUserService.save(new TRoleUser(roleUserId,userid,"d8b7e36108e9c9807fb87b6557b13e25"));
            }
            //向rabbitmq发送消息，并携带添加的实体类信息
            if (tEmployee.getEmail()!=null) {
                TRabbitmqLog tRabbitmqLog = new TRabbitmqLog();
                String rabbitmqId = UUID.randomUUID().toString().replaceAll("-", "");
                tRabbitmqLog.setObjId(rabbitmqId);
                tRabbitmqLog.setEmpid(id);
                tRabbitmqLog.setStatus(0);
                tRabbitmqLog.setRoutekey(RabbitConstant.MAIL_ROUTING_KEY_NAME);
                tRabbitmqLog.setExchange(RabbitConstant.MAIL_EXCHANGE_NAME);
                tRabbitmqLog.setCount(RabbitConstant.MAX_TRY_COUNT);
                //超时时间，当前时间加上一分钟
                tRabbitmqLog.setTrytime(LocalDateTime.now().plusMinutes(RabbitConstant.MSG_TIMEOUT));
                tRabbitmqLog.setCreatetimt(LocalDateTime.now());
                tRabbitmqLog.setUpdatetime(LocalDateTime.now());
                //插入
                itRabbitmqLogService.save(tRabbitmqLog);
                //发送消息，参数：交换机、路由键、携带的参数类，数据库主键ID
                //消息队列和交换机由路由键绑定(RabbitMQConfig)，通过交换机和路由键找对应的消息队列
                rabbitTemplate.convertAndSend(RabbitConstant.MAIL_EXCHANGE_NAME, RabbitConstant.MAIL_ROUTING_KEY_NAME, tEmployee, new CorrelationData(rabbitmqId));
            }
        }catch (Exception e){
        e.printStackTrace();
    }
    }

    /**
     * 获取个人信息
     * */
    @Override
    public TEmployee getMyData(String userid) {
        TUser tUser = itUserService.getOne(new QueryWrapper<TUser>().eq("id", userid));
        TEmployee tEmployee = tEmployeeMapper.getEmployee(tUser.getEmployee());
        return tEmployee;
    }
}
