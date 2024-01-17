package io.getarrays.securecapita.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PrincipalAdministratorPurchaseRequisitionDto {

    private Long id;
    private Long userId;
    private String fullName;

    private String receiverEmail;
    private String signature;


    private String name;
    private String type;

    @Column(name = "profileImage", nullable = false, columnDefinition = "BINARY(256)", length = 256)
    @Lob
    private byte[] profileImage;
}
