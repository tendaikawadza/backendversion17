package io.getarrays.securecapita.repository;

import io.getarrays.securecapita.domain.DeputyHeadAdministrationRequisition;

public interface DeputyHeadPurchaseRequestRepository <T extends DeputyHeadAdministrationRequisition>{
    T create(DeputyHeadAdministrationRequisition data, Long userId);
}
