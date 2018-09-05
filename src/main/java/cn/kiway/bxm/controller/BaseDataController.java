package cn.kiway.bxm.controller;

import cn.kiway.bxm.entity.BaseData;
import cn.kiway.bxm.service.BaseDataService;
import cn.kiway.bxm.utils.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import cn.kiway.bxm.entity.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


/**
 * Created by ym on 2018-09-04.
 */
@Api(tags="Basedata")
@RestController
@RequestMapping("baseData")
@Slf4j
public class BaseDataController extends BaseController{

	@Autowired
	private BaseDataService service;

	@Value("${web.web-path}")
	private String path;

    @Value("${bxm.project}")
	private String project;

	@ApiOperation("分页查询")
	@GetMapping
	public ResponseResult findPage(BaseData entity, Page<BaseData> page){
    	ResponseResult re = null;
        EntityWrapper wrapper = new EntityWrapper();
        Page<BaseData> pagination = service.selectPage(page,wrapper);

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
        BaseData entity = service.selectById(id);
    	return ResponseResult.ok(200,entity);
    }
    
    @ApiOperation("修改纪录")
    @PutMapping("{id}")
    public ResponseResult update(@PathVariable("id")String id, @Valid BaseData entity, BindingResult result, HttpServletRequest request) {
        ResponseResult re = null;
        re = super.hasError(result);
        if(re != null){
            return re;
        }
        entity.setId(id);
        service.updateById(entity);
        return ResponseResult.ok();
    }  
    
    @ApiOperation("新增纪录")
    @PostMapping
    public ResponseResult create(@Valid BaseData entity, BindingResult result, HttpServletRequest request) {
        ResponseResult re = null;
        re = super.hasError(result);
        if(re != null){
            return re;
        }
        service.insert(entity);
    	return ResponseResult.ok();
    }

    @ApiOperation("获取支付图片")
    @GetMapping("qrcode")
    public ResponseResult getDataByType(){
        ResponseResult re = null;
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("type", "qrCode");
        BaseData baseData = service.selectOne(wrapper);
        String url = "";
        if(baseData != null){
            url = baseData.getValue();
        }
        return new ResponseResult(200,url);
    }

    @ApiOperation("上传支付二维码文件")
    @PostMapping("file/qrCode")
    public ResponseResult file(@RequestParam("file") MultipartFile file) throws IOException {
        ResponseResult re = null;
        Map<String, Object> resultMap = new HashMap<>();

        String picPath = "static" + File.separator  + "pic" +
                File.separator + (new SimpleDateFormat("yyyyMMdd").
                format(new Date())) + File.separator + System.currentTimeMillis() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        String filePath = path + picPath ;
        File oriFile = new File(filePath);
        if(!oriFile.exists()){
            oriFile.getParentFile().mkdirs();
        }
        file.transferTo(oriFile);
        EntityWrapper wrapper = new EntityWrapper();
        String url = project + File.separator + picPath;
        wrapper.eq("type","qrCode");
        BaseData baseData = service.selectOne(wrapper);
        if(baseData == null){
            BaseData bd = new BaseData();
            bd.setCreateDate(Common.createDate());
            bd.setName("付款二维码");
            bd.setParentId("0");
            bd.setType("qrCode");
            bd.setValue(url);
            service.insert(bd);
        }else{
            BaseData bd = new BaseData();
            bd.setValue(url);
            EntityWrapper upWrapper = new EntityWrapper();
            upWrapper.eq("type", "qrCode");
            service.update(bd,upWrapper);
        }
        return re;
    }
}
