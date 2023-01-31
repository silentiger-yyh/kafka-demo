package com.yyh.kafka.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
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
 * @since 2022-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("st_data")
@ApiModel(value="StData对象", description="")
public class StData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("channel_id")
    private String channelId;

    @TableField("channel_name")
    private String channelName;

    @TableField("value")
    private String value;

    @TableField("time_stamp")
    private Date timeStamp;


}
