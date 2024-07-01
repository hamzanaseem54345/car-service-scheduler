package com.voltMoney.carSchedulerService.service;

import com.voltMoney.carSchedulerService.dto.ServiceOperatorRequestDTO;
import com.voltMoney.carSchedulerService.model.ServiceOperator;
import com.voltMoney.carSchedulerService.respository.ServiceOperatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceOperatorImpl implements ServiceOperatorService{


    @Autowired
    private ServiceOperatorRepository serviceOperatorRepository;

    @Override
    public List<ServiceOperator> getAllServiceOperators() {
        return serviceOperatorRepository.findAll();
    }

    @Override
    public ServiceOperator saveServiceOperator(ServiceOperatorRequestDTO serviceOperatorDto) {
      return serviceOperatorRepository.save(ServiceOperator.builder().name(serviceOperatorDto.getName()).build());

    }

    @Override
    public ServiceOperator getServiceOperatorById(Long id) {
        return serviceOperatorRepository.findById(id).orElse(ServiceOperator.builder().build());
    }
}
