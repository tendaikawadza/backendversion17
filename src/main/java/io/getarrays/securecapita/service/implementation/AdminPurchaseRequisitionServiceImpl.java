package io.getarrays.securecapita.service.implementation;

import io.getarrays.securecapita.domain.AdminPurchaseRequisition;
import io.getarrays.securecapita.domain.PurchaseRequisition;
import io.getarrays.securecapita.domain.User;
import io.getarrays.securecapita.repository.AdminPurchaseRequestsRepository;
import io.getarrays.securecapita.repository.PurchaseRequestsRepository;
import io.getarrays.securecapita.repository.UserRepository;
import io.getarrays.securecapita.service.AdminPurchaseRequisitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.getarrays.securecapita.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AdminPurchaseRequisitionServiceImpl implements AdminPurchaseRequisitionService {


//    private final AdPurchaseRequestsRepository<PurchaseRequisition> purchaseRequestsRepository;
private final UserRepository<User> userRepository;


    private  final AdminPurchaseRequestsRepository <AdminPurchaseRequisition>adminPurchaseRequestsRepository;


    @Override
    public AdminPurchaseRequisition createAdminPurchaseRequesition (AdminPurchaseRequisition adminPurchaseRequisition, Long userId) {
        return adminPurchaseRequestsRepository.create(adminPurchaseRequisition, userId);
    }






}
