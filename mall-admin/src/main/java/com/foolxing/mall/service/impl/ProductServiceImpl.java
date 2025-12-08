package com.foolxing.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foolxing.mall.config.RedissonConfig;
import com.foolxing.mall.mapper.ProductMapper;
import com.foolxing.mall.pojo.Product;
import com.foolxing.mall.pojo.VO.ProductVO;
import com.foolxing.mall.pojo.query.ProductQuery;
import com.foolxing.mall.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author FoolXing
 * @since 2025-12-01
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public IPage<ProductVO> list(ProductQuery productQuery) {
        IPage<ProductVO> page = new Page<>(productQuery.getPage(), productQuery.getLimit());
        IPage<ProductVO> list = productMapper.list(page, productQuery);
        return list;
    }

    @Cacheable(value = "productCache",key = "#id",sync = true)
    @Override
    public Product selectById(Integer id) {
        return productMapper.selectById(id);
    }

    @CacheEvict(value = "productCache",key = "#product.id")
    @Override
    public void update(Product product) {
        productMapper.updateById(product);
    }


    /*@Override
    public Product selectById(Integer id) {
        Product product = (Product) redisTemplate.opsForValue().get("product：" + id);
        if (ObjectUtils.isEmpty(product)) {
            synchronized (this){
                product = (Product) redisTemplate.opsForValue().get("product：" + id);
                if (ObjectUtils.isEmpty(product)) {
                    product = productMapper.selectById(id);
                    if (ObjectUtils.isEmpty(product)) {
                        redisTemplate.opsForValue().set("product：" + id, new Product(), 1, TimeUnit.MINUTES);
                    }else {
                        redisTemplate.opsForValue().set("product：" + id,product);
                    }
                }
            }
        }
        return product;
    }*/

    /*@Override
    public Product selectById(Integer id) {
        Product product = (Product) redisTemplate.opsForValue().get("product：" + id);
        if (ObjectUtils.isEmpty(product)) {
            RLock lock = redissonClient.getLock("lock:product" + id);
            lock.lock();
            try {
                product = (Product) redisTemplate.opsForValue().get("product：" + id);
                if (ObjectUtils.isEmpty(product)) {
                    product = productMapper.selectById(id);
                    if (ObjectUtils.isEmpty(product)) {
                        redisTemplate.opsForValue().set("product：" + id, new Product(), 1, TimeUnit.MINUTES);
                    }else {
                        redisTemplate.opsForValue().set("product：" + id,product);
                    }
                }
            } finally {
                lock.unlock();
            }
        }
        return product;
    }*/
}
