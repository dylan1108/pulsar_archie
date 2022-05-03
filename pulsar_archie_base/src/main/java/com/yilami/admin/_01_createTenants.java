package com.yilami.admin;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.common.policies.data.TenantInfo;

import java.util.HashSet;

// 演示 如何使用JAVA API 完成 租户操作
public class _01_createTenants {

    public static void main(String[] args) throws Exception {

        // 1. 创建Pulsar的Admin管理对象
        String serviceHttpUrl = "http://192.168.0.10:18080,192.168.0.10:28080,192.168.0.10:38080";
        PulsarAdmin pulsarAdmin = PulsarAdmin.builder().serviceHttpUrl(serviceHttpUrl).build();
        //2. 基于Pulsar的Admin对象进行相关的操作

        //2.1: 如何创建 租户操作
        HashSet<String> allowedClusters = new HashSet<>();
        allowedClusters.add("pulsar_pseudo_cluster");
        TenantInfo config = TenantInfo.builder().allowedClusters(allowedClusters).build();
        pulsarAdmin.tenants().createTenant("itcast_pulsar_t",config);

        // 2.2: 查看当前有那些租户
        /*List<String> tenants = pulsarAdmin.tenants().getTenants();
        for (String tenant : tenants) {
            System.out.println("租户信息为:"+tenant);
        }*/

        //2.3: 删除租户操作
        //pulsarAdmin.tenants().deleteTenant("itcast_pulsar_t");


        //3. 关闭管理对象
        pulsarAdmin.close();

    }

}
