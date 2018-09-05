package cn.kiway.bxm.controller;

import cn.kiway.bxm.entity.Customer;
import cn.kiway.bxm.service.CustomerService;
import cn.kiway.bxm.utils.Common;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.kiway.bxm.controller.BaseController;
import cn.kiway.bxm.entity.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.mapper.EntityWrapper;


/**
 * Created by ym on 2018-09-04.
 */
@Api(tags="Customer")
@RestController
@RequestMapping("customer")
public class CustomerController extends BaseController{

	private Class<?> clazz = CustomerController.class;
	
	@Autowired
	private CustomerService service;
	
	@ApiOperation("分页查询")
	@GetMapping
	public ResponseResult findPage(Customer entity,Page<Customer> page){
    	ResponseResult re = null;
        EntityWrapper wrapper = new EntityWrapper();
        Page<Customer> pagination = service.selectPage(page,wrapper);

        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("list", pagination.getRecords());
        retMap.put("totalRecord", pagination.getTotal());
        retMap.put("totalPage", pagination.getPages());
        re = new ResponseResult<>(200, retMap);
    	return re;
    }
    
    @ApiOperation("根据ID删除纪录")
    @DeleteMapping("{id}")
    public ResponseResult deleteById(@PathVariable("id")String id){
    	service.deleteById(id);
    	return ResponseResult.ok();
    }
    
    @ApiOperation("根据ID查询详细信息")
    @GetMapping("{id}")
    public ResponseResult getById(@PathVariable("id")String id){
        Customer entity = service.selectById(id);
    	return ResponseResult.ok(200,entity);
    }
    
    @ApiOperation("修改纪录")
    @PutMapping("{id}")
    public ResponseResult update(@PathVariable("id")String id,@Valid Customer entity,BindingResult result,HttpServletRequest request) {
        ResponseResult re = null;
        re = super.hasError(result);
        if(re != null){
            return re;
        }
        entity.setId(id);
        service.updateById(entity);
        return ResponseResult.ok();
    }  
    
    @ApiOperation("新增客户消息")
    @PostMapping
    public ResponseResult create(@Valid Customer entity,BindingResult result,HttpServletRequest request) {
        ResponseResult re = null;
        re = super.hasError(result);
        if(re != null){
            return re;
        }
        entity.setCreateDate(Common.createDate());
        service.insert(entity);
    	return ResponseResult.ok();
    }    
}
