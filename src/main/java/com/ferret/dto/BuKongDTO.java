package com.ferret.dto;

import com.ferret.bean.BuKong;
import com.ferret.bean.BuKongInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author cc;
 * @since 2018/2/7;
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class BuKongDTO extends BuKong{
    private BuKongInfo info;
}

