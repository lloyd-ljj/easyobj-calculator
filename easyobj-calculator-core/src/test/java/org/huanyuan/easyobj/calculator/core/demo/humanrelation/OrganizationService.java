package org.huanyuan.easyobj.calculator.core.demo.humanrelation;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-17 14:23
 */
public class OrganizationService {
    private static Map<Integer, Organization> orgMap = ImmutableMap.<Integer, Organization>builder()
            .put(1, new Organization(1, "org_red", 3))
            .put(2, new Organization(2, "org_blue", 4))
            .put(3, new Organization(3, "org_yellow", 5))
            .build();

    public static Organization getOrg(Integer id) {
        return orgMap.get(id);
    }
}
