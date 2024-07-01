package com.voltMoney.carSchedulerService.controller;

import com.voltMoney.carSchedulerService.dto.ServiceOperatorRequestDTO;
import com.voltMoney.carSchedulerService.model.ServiceOperator;
import com.voltMoney.carSchedulerService.service.ServiceOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-operators")
public class ServiceOperatorController {

    @Autowired
    private ServiceOperatorService serviceOperatorService;

    @PostMapping
    public ServiceOperator createServiceOperator(@RequestBody ServiceOperatorRequestDTO serviceOperatorRequestDTO) {
        return serviceOperatorService.saveServiceOperator(serviceOperatorRequestDTO);
    }

    @GetMapping
    public List<ServiceOperator> getAllServiceOperators() {
        return serviceOperatorService.getAllServiceOperators();
    }

    @GetMapping("/{id}")
    public ServiceOperator getServiceOperatorById(@PathVariable Long id) {
        return serviceOperatorService.getServiceOperatorById(id);
    }
}
