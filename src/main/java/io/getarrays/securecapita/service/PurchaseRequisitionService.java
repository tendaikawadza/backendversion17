package io.getarrays.securecapita.service;

import io.getarrays.securecapita.domain.PurchaseRequisition;
import io.getarrays.securecapita.dto.PurchaseRequisitionDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PurchaseRequisitionService {

    PurchaseRequisition createPurchaseRequest(PurchaseRequisition purchaseRequisition, Long userId);

    List<PurchaseRequisition> getAllPurchaseRequests();

    PurchaseRequisition getPurchaseRequestById(Long id);

    boolean deletePurchaseRequests(Long id);

    void updatePurchaseRequest(PurchaseRequisition purchaseRequisition, Long id);//is the return type here correct, yes correct consistent return value

    PurchaseRequisitionDTO findOneWithUser(Long userId, Long purchaseId);

    List<PurchaseRequisition> getUserAllPurchaseRequests(Long userId);

      void updateSignature(PurchaseRequisitionDTO purchaseRequisition, MultipartFile image);


}