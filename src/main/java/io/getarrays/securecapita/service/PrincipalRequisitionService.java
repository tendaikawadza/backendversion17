package io.getarrays.securecapita.service;

import io.getarrays.securecapita.domain.PrincipalAdministratorPurchaseRequisition;

public interface PrincipalRequisitionService {
  PrincipalAdministratorPurchaseRequisition createPrincipalAdministratorRequisition
            (PrincipalAdministratorPurchaseRequisition principalAdministratorRequisition, Long userId);
}
