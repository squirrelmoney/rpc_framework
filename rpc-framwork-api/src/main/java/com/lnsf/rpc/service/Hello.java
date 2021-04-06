package com.lnsf.rpc.service;

import lombok.*;
import java.io.Serializable;

/**
 * @author shuang.kou
 * @createTime 2020年05月10日 07:04:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hello implements Serializable {
    private String message;
    private String description;
}
