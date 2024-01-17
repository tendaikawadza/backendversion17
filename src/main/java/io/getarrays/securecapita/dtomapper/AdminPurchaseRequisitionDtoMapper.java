package io.getarrays.securecapita.dtomapper;


import io.getarrays.securecapita.domain.AdminPurchaseRequisition;
import io.getarrays.securecapita.domain.PurchaseRequisition;
import io.getarrays.securecapita.domain.User;

import io.getarrays.securecapita.dto.AdminPurchaseRequisitionDto;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

public class AdminPurchaseRequisitionDtoMapper {
    public static AdminPurchaseRequisitionDto fromPurchaseRequisitionDto(AdminPurchaseRequisition adminPurchaseRequisition, User user) {
        AdminPurchaseRequisitionDto   adminpurchaseRequisitionDto = new AdminPurchaseRequisitionDto();
        BeanUtils.copyProperties(adminpurchaseRequisitionDto, adminpurchaseRequisitionDto);
                                                            //setDepartment(user.getDepartment());
        adminpurchaseRequisitionDto.setFullName(user.getFirstName() + " " + user.getLastName());
        return adminpurchaseRequisitionDto;



}}



