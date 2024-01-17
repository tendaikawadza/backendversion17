package io.getarrays.securecapita.service.implementation;
import io.getarrays.securecapita.domain.*;
import io.getarrays.securecapita.repository.*;
import io.getarrays.securecapita.domain.User;
import io.getarrays.securecapita.repository.AdminPurchaseRequestsRepository;
import io.getarrays.securecapita.repository.UserRepository;
import io.getarrays.securecapita.service.AdminPurchaseRequisitionService;
import io.getarrays.securecapita.service.DeputyHeadAdministrationRequisitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.getarrays.securecapita.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class DeputyHeadAdministrationRequisitionServiceImpl implements DeputyHeadAdministrationRequisitionService {

    private final UserRepository<User> userRepository;


    private final DeputyHeadPurchaseRequestRepository<DeputyHeadAdministrationRequisition> deputyHeadPurchaseRequestsRepository;




    @Override
    public DeputyHeadAdministrationRequisition createDeputyHeadAdministrationRequisition(DeputyHeadAdministrationRequisition deputyHeadAdministrationRequisition, Long userId) {
        return deputyHeadPurchaseRequestsRepository.create(


                deputyHeadAdministrationRequisition, userId);
    }
}
