package cn.kiway.bxm.service;

import cn.kiway.bxm.entity.BaseData;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * Created by ym on Tue Sep 04 15:40:31 CST 2018.
 */
public interface BaseDataService extends IService<BaseData> {

    /***
     * @description 获取一级基础数据
     * @author yimin
     * @date 2017年7月20日
     * @return
     */
    public List<BaseData> find();
}
