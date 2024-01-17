package io.getarrays.securecapita.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_DEFAULT)
public class PurchaseRequisition {
    private Long id;
    private Long userId;
    private Date date;
    private int departmentCode;
    private String reason;
    private int itemNumber;
    private String itemDescription;
    private int unitPrice;
    private int quantity;
    private int estimatedValue;
    private String receiverEmail;
  private String Signature;
    private String name;
    private String type;

    //@Column(name = "profileImage", nullable = false, columnDefinition = "BINARY(256)", length = 256)
    @Lob
    private byte[] profileImage;



}