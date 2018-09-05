package cn.kiway.bxm.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.kiway.bxm.service.BaseDataService;
import cn.kiway.bxm.entity.BaseData;
import cn.kiway.bxm.mapper.BaseDataMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

/**
 * Created by ym on Tue Sep 04 15:40:31 CST 2018
 */
@Service
public class BaseDataServiceImpl extends ServiceImpl<BaseDataMapper,BaseData> implements BaseDataService {

    @Autowired
    private BaseDataMapper baseDataDao;
    /***
     * @description 获取一级基础数据
     * @author yimin
     * @date 2017年7月20日
     * @return
     */
    @Override
    public List<BaseData> find() {
        EntityWrapper wrapper = new EntityWrapper<>();
        wrapper.eq("parentId","0");
        return baseDataDao.selectList(wrapper);
    }
}
