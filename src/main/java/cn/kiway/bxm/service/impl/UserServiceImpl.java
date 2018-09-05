package cn.kiway.bxm.service.impl;

import org.springframework.stereotype.Service;
import cn.kiway.bxm.service.UserService;
import cn.kiway.bxm.entity.User;
import cn.kiway.bxm.mapper.UserMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ym on Tue Sep 04 15:40:31 CST 2018
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
	
}
