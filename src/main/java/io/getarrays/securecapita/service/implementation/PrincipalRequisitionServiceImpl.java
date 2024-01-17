package io.getarrays.securecapita.service.implementation;

import io.getarrays.securecapita.domain.PrincipalAdministratorPurchaseRequisition;
import io.getarrays.securecapita.service.PrincipalRequisitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalRequisitionServiceImpl implements PrincipalRequisitionService {


    @Override
    public PrincipalAdministratorPurchaseRequisition createPrincipalAdministratorRequisition(PrincipalAdministratorPurchaseRequisition principalAdministratorRequisition, Long userId) {
        return null;


    }
}
