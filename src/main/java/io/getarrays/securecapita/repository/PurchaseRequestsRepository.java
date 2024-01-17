package io.getarrays.securecapita.repository;

import io.getarrays.securecapita.domain.PurchaseRequisition;
import io.getarrays.securecapita.dto.PurchaseRequisitionDTO;
import io.getarrays.securecapita.dto.UserDTO;
import io.getarrays.securecapita.purchaserequestnew.PurchaseRequestEntity;
import io.getarrays.securecapita.purchaserequestnew.PurchaseResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PurchaseRequestsRepository   <T extends PurchaseRequisition> {
    List<T> list();

    T create(T data, Long userId);

    T get(Long id);

   // List<PurchaseRequestEntity> saveFile(List<MultipartFile> files, List<PurchaseRequestEntity> pEntity) throws IOException;

    boolean delete(Long id);

    /* More Complex */
    T findOneWithUser(Long id);

    void update(T data ,Long id);//is this correct , yes correct

    //mehtod to get purchase requests belonging to user
   List <T> getUserAllPurchaseRequests(Long userId);

   void updateSignature(PurchaseRequisitionDTO purchaseRequisitionDTO, MultipartFile image);

}
