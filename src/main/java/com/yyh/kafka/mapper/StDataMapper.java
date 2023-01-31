package com.yyh.kafka.mapper;

import com.yyh.kafka.entity.StData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 喻云虎
 * @since 2022-10-11
 */
public interface StDataMapper extends BaseMapper<StData> {
    List<HashMap<String, String>> getAllData();
}
