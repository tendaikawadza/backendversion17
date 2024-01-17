package io.getarrays.securecapita.service;

import io.getarrays.securecapita.domain.AdminPurchaseRequisition;
import io.getarrays.securecapita.domain.DeputyHeadAdministrationRequisition;

public interface DeputyHeadAdministrationRequisitionService {
    DeputyHeadAdministrationRequisition createDeputyHeadAdministrationRequisition
            (DeputyHeadAdministrationRequisition deputyHeadAdministrationRequisition, Long userId);
}
