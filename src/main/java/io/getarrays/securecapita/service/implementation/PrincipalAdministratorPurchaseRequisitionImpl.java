package io.getarrays.securecapita.service.implementation;

import io.getarrays.securecapita.domain.AdminPurchaseRequisition;
import io.getarrays.securecapita.domain.PrincipalAdministratorPurchaseRequisition;
import io.getarrays.securecapita.domain.User;
import io.getarrays.securecapita.repository.AdminPurchaseRequestsRepository;
import io.getarrays.securecapita.repository.PrincipalAdministratorPurchaseRequisitionRepository;
import io.getarrays.securecapita.repository.UserRepository;
import io.getarrays.securecapita.service.PrincipalAdministratorPurchaseRequisitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalAdministratorPurchaseRequisitionImpl implements PrincipalAdministratorPurchaseRequisitionService {



    //    private final AdPurchaseRequestsRepository<PurchaseRequisition> purchaseRequestsRepository;
    private final UserRepository<User> userRepository;


    private  final PrincipalAdministratorPurchaseRequisitionRepository<PrincipalAdministratorPurchaseRequisition> principalAdministratorPurchaseRequisitionRepository;


    @Override
    public  PrincipalAdministratorPurchaseRequisition  createPrincipalAdministratorPurchaseRequisition ( PrincipalAdministratorPurchaseRequisition principalAdministratorPurchaseRequisition, Long userId) {
        return   principalAdministratorPurchaseRequisitionRepository .create(principalAdministratorPurchaseRequisition, userId);
    }


}
