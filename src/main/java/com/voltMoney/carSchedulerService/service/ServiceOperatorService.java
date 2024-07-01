package com.voltMoney.carSchedulerService.service;


import com.voltMoney.carSchedulerService.dto.ServiceOperatorRequestDTO;
import com.voltMoney.carSchedulerService.model.ServiceOperator;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ServiceOperatorService {

    List<ServiceOperator> getAllServiceOperators();

    ServiceOperator saveServiceOperator(ServiceOperatorRequestDTO serviceOperator);

    ServiceOperator getServiceOperatorById(Long id);
}
