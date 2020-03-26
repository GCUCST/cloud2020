package com.cst.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoaderBlancer {

    ServiceInstance instance(List<ServiceInstance> serviceInstances);


}
