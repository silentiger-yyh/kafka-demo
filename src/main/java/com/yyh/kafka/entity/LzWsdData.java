package com.yyh.kafka.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 喻云虎
 * @since 2023-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("lz_wsd_data")
@ApiModel(value="LzWsdData对象", description="")
public class LzWsdData implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("message")
    private String message;

    @TableField("consum_time")
    private String consumTime;

    @TableField("dev_id")
    private String devId;

    @TableField("specialty")
    private String specialty;

    @TableField("data_type")
    private String dataType;

    @TableField("impl_type")
    private String implType;

    @TableField("point_id")
    private String pointId;

    @TableField("d_time")
    private String dTime;

    @TableField("sn")
    private String sn;

    @ApiModelProperty(value = "温度")
    @TableField("temperature")
    private String temperature;

    @ApiModelProperty(value = "湿度")
    @TableField("humidity")
    private String humidity;

    @ApiModelProperty(value = "电量")
    @TableField("electricity")
    private String electricity;


}
