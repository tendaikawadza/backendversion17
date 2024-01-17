package io.getarrays.securecapita.resource;


import io.getarrays.securecapita.domain.PrincipalAdministratorPurchaseRequisition;
import io.getarrays.securecapita.dto.UserDTO;
import io.getarrays.securecapita.service.PrincipalAdministratorPurchaseRequisitionService;
import io.getarrays.securecapita.service.PrincipalRequisitionService;
import io.getarrays.securecapita.service.UserService;
import io.getarrays.securecapita.service.implementation.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static io.getarrays.securecapita.utils.UserUtils.getAuthenticatedUser;

@RestController
@RequestMapping(path = "/principalpurchase-requisition")
@RequiredArgsConstructor
@Slf4j
public class PrincipalAdministratorPurchaseRequisitionResource {

    private final PrincipalAdministratorPurchaseRequisitionService  principalAdministratorPurchaseRequisitionService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;



    @PostMapping("/create")
    public ResponseEntity< PrincipalAdministratorPurchaseRequisition> createPrincipalPurchaseRequesition(@RequestBody  PrincipalAdministratorPurchaseRequisition principalAdministratorPurchaseRequisition, Authentication authentication) {
        UserDTO user = userService.getUserByEmail(getAuthenticatedUser(authentication).getEmail());
        PrincipalAdministratorPurchaseRequisition createdPrincipalPurchase = principalAdministratorPurchaseRequisitionService.createPrincipalAdministratorPurchaseRequisition        (principalAdministratorPurchaseRequisition, user.getId());

        String email = principalAdministratorPurchaseRequisition.getReceiverEmail();
        String subject = "Purchase Request Verification From Principal Administrator";
        String message = "purchase request has been sent for Attention Name: " +principalAdministratorPurchaseRequisition.getReceiverEmail() +
                "\n\nA purchase request email verification has been sent to: " + principalAdministratorPurchaseRequisition.getReceiverEmail();
        emailService.sendEmail(email, subject, message);

        return ResponseEntity.ok(createdPrincipalPurchase);
    }

}