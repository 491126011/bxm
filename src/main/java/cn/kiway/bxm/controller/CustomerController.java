package cn.kiway.bxm.controller;

import cn.kiway.bxm.entity.Customer;
import cn.kiway.bxm.service.CustomerService;
import cn.kiway.bxm.utils.Common;
import com.xiaoleilu.hutool.http.HttpUtil;
import com.xiaoleilu.hutool.json.JSONObject;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import cn.kiway.bxm.controller.BaseController;
import cn.kiway.bxm.entity.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import springfox.documentation.annotations.ApiIgnore;


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

    @Value("${bxm.bankCardKey}")
    private String bankCardKey;

    @Value("${bxm.idCardKey}")
    private String idCardKey;
	
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

    /***
     * 验证用户身份证
     * @param realName
     * @param idCard
     * @return
     */
    @ApiOperation("验证用户身份证")
    @GetMapping("idCard/validation")
    public ResponseResult validateIdcard(@RequestParam @ApiParam("姓名") String realName, @RequestParam @ApiParam("身份证号") String idCard){
        ResponseResult re = null;
        String url = "http://op.juhe.cn/idcard/query";
        try {
            url = url + "?key=" + idCardKey +"&idcard="+idCard+"&realname="+URLEncoder.encode(realName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String retStr = HttpUtil.get(url);
        JSONObject retObj = new JSONObject(retStr);
        int errCode = retObj.getInt("error_code");
        if(errCode == 0){
            JSONObject result = retObj.getJSONObject("result");
            int res = result.getInt("res");
            if(res == 1){
                re = new ResponseResult(200);
                re.setData(retObj.get("reason"));
            }else{
                re = new ResponseResult(400);
                re.setErrorMsg("身份证号与姓名不匹配");
            }
        }else {
            re = new ResponseResult(errCode);
            re.setErrorMsg(retObj.getStr("reason"));
        }
        return re;
    }

    /***
     * 验证银行卡
     * @param entity
     * @return
     */
    @ApiOperation("验证银行卡")
    @GetMapping("bankCard/validation")
    public ResponseResult validateBankCard(@Valid Customer entity,BindingResult result){
        ResponseResult re = null;
        re = super.hasError(result);
        if(re != null){
            return re;
        }

        String url = "http://v.juhe.cn/verifybankcard4/query.php";
        try {
            url = url + "?key=" + bankCardKey +"&bankcard="+entity.getBankCard()+
                    "&realname="+URLEncoder.encode(entity.getName(),"UTF-8")
                    +"&mobile=" + entity.getPhone() + "&idcard=" + entity.getIdCard();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String retStr = HttpUtil.get(url);
        JSONObject retObj = new JSONObject(retStr);
        int errCode = retObj.getInt("error_code");
        if(errCode == 0){
            JSONObject resultObj = retObj.getJSONObject("result");
            int res = resultObj.getInt("res");
            if(res == 1){
                re = new ResponseResult(200);
                re.setData(retObj.get("reason"));
            }else{
                re = new ResponseResult(400);
                re.setErrorMsg(resultObj.getStr("message"));
            }
        }else {
            re = new ResponseResult(errCode);
            re.setErrorMsg(retObj.getStr("reason"));
        }
        return re;
    }
}
