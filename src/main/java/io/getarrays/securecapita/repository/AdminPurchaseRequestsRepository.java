package io.getarrays.securecapita.repository;


import io.getarrays.securecapita.domain.AdminPurchaseRequisition;
import io.getarrays.securecapita.purchaserequestnew.PurchaseRequestEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminPurchaseRequestsRepository   <T extends AdminPurchaseRequisition> {

    T create(T data, Long userId);


}
