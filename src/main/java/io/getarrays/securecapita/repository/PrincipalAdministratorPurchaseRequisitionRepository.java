package io.getarrays.securecapita.repository;

import io.getarrays.securecapita.domain.DeputyHeadAdministrationRequisition;
import io.getarrays.securecapita.domain.PrincipalAdministratorPurchaseRequisition;

public interface PrincipalAdministratorPurchaseRequisitionRepository<T extends PrincipalAdministratorPurchaseRequisition> {


    T create(T data, Long userId);
}
