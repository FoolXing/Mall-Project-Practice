package com.foolxing.mall.service.impl;

import com.foolxing.mall.pojo.OperLog;
import com.foolxing.mall.mapper.OperLogMapper;
import com.foolxing.mall.service.IOperLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author FoolXing
 * @since 2025-12-04
 */
@Service
public class OperLogServiceImpl extends ServiceImpl<OperLogMapper, OperLog> implements IOperLogService {

}
