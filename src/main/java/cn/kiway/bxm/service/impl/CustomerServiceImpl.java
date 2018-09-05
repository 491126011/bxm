package cn.kiway.bxm.service.impl;

import org.springframework.stereotype.Service;
import cn.kiway.bxm.service.CustomerService;
import cn.kiway.bxm.entity.Customer;
import cn.kiway.bxm.mapper.CustomerMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ym on Tue Sep 04 15:40:31 CST 2018
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper,Customer> implements CustomerService {
	
}
