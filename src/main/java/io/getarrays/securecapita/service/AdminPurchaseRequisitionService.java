package io.getarrays.securecapita.service;

import io.getarrays.securecapita.domain.AdminPurchaseRequisition;
import io.getarrays.securecapita.domain.PurchaseRequisition;

public interface AdminPurchaseRequisitionService {

   AdminPurchaseRequisition  createAdminPurchaseRequesition(AdminPurchaseRequisition adminPurchaseRequisition, Long userId);
}
