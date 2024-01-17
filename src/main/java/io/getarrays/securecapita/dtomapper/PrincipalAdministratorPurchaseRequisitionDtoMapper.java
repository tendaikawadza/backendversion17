package io.getarrays.securecapita.dtomapper;

import io.getarrays.securecapita.domain.AdminPurchaseRequisition;
import io.getarrays.securecapita.domain.PrincipalAdministratorPurchaseRequisition;
import io.getarrays.securecapita.domain.User;
import io.getarrays.securecapita.dto.AdminPurchaseRequisitionDto;
import io.getarrays.securecapita.dto.PrincipalAdministratorPurchaseRequisitionDto;
import org.springframework.beans.BeanUtils;

public class PrincipalAdministratorPurchaseRequisitionDtoMapper {

    public static PrincipalAdministratorPurchaseRequisitionDto fromsPrincipalAdministratorPurchaseRequisitionDto(PrincipalAdministratorPurchaseRequisition principalAdministratorPurchaseRequisition, User user) {
        PrincipalAdministratorPurchaseRequisitionDto principalAdministratorPurchaseRequisitionDto = new PrincipalAdministratorPurchaseRequisitionDto();
        BeanUtils.copyProperties(principalAdministratorPurchaseRequisitionDto, principalAdministratorPurchaseRequisitionDto);
        //setDepartment(user.getDepartment());
        principalAdministratorPurchaseRequisitionDto.setFullName(user.getFirstName() + " " + user.getLastName());
        return principalAdministratorPurchaseRequisitionDto;

    }
}