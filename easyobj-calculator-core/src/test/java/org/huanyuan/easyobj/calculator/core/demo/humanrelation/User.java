package org.huanyuan.easyobj.calculator.core.demo.humanrelation;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-17 14:20
 */
@Data
@AllArgsConstructor
class User {
    Integer id;
    Integer leader;
    Integer mentor;
    Integer organization;
    String name;
    String mobile;
    // 0 male 1 female
    Integer gender;
}
