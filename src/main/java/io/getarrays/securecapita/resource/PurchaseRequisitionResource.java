package io.getarrays.securecapita.resource;

import io.getarrays.securecapita.domain.HttpResponse;
import io.getarrays.securecapita.domain.PurchaseRequisition;
import io.getarrays.securecapita.domain.Stock;
import io.getarrays.securecapita.dto.PurchaseRequisitionDTO;
import io.getarrays.securecapita.dto.UserDTO;
import io.getarrays.securecapita.event.NewUserEvent;
import io.getarrays.securecapita.purchaserequestnew.PurchaseRequestDto;
import io.getarrays.securecapita.purchaserequestnew.PurchaseResponseDto;
import io.getarrays.securecapita.report.PurchaseRequisitionReport;
import io.getarrays.securecapita.service.PurchaseRequisitionService;
import io.getarrays.securecapita.service.StockService;
import io.getarrays.securecapita.service.UserService;
import io.getarrays.securecapita.service.implementation.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import static io.getarrays.securecapita.enumeration.EventType.PROFILE_PICTURE_UPDATE;
import static io.getarrays.securecapita.utils.UserUtils.getAuthenticatedUser;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.apache.tomcat.util.http.fileupload.FileUploadBase.CONTENT_DISPOSITION;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.parseMediaType;

@RestController
@RequestMapping(path = "/purchase-requisition")
@RequiredArgsConstructor
@Slf4j
public class PurchaseRequisitionResource {

    private final PurchaseRequisitionService purchaseRequisitionService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
//

    @PostMapping(value = "/createlist", consumes = {"multipart/form-data"})
    public ResponseEntity<List<PurchaseRequisition>> createPurchases(@RequestParam("files") MultipartFile photo,@RequestBody  List<PurchaseRequisition> purchaseRequisitions, Authentication authentication) {
        UserDTO user = userService.getUserByEmail(getAuthenticatedUser(authentication).getEmail());
        List<PurchaseRequisition> createdPurchases = new ArrayList<>();

        for (PurchaseRequisition purchaseRequisition : purchaseRequisitions) {
            String uniqueFile = null;
            try {
                uniqueFile = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
                Path rootPath = Path.of("uploads").resolve(uniqueFile).toAbsolutePath();
                Files.copy(photo.getInputStream(), rootPath);
                purchaseRequisition.setSignature(uniqueFile);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            log.info("Signature file: {}",purchaseRequisition.toString());
            PurchaseRequisition createdPurchase = purchaseRequisitionService.createPurchaseRequest(purchaseRequisition, user.getId());
            String email = purchaseRequisition.getReceiverEmail();
            String subject = "Purchase Request Send has been sent login Verification From Administrator";
            String message = "Hello " + purchaseRequisition.getId() + " for Product Name" + purchaseRequisition.getItemDescription() + ", " +
                    "\n A Purchase Request Email Verification Was Sent To \n" + purchaseRequisition.getReceiverEmail();
            emailService.sendEmail(email, subject, message);//email sent a notification
            createdPurchases.add(createdPurchase);
        }

        return ResponseEntity.ok(createdPurchases);

    }

    //might upload more than one image
    // i think if people upload is single then people can upload again ok
//where you want string purchase success
    // but why you use List on Multipart
    // whats best single upload
//I NEED TO UPLOAD IMAGE SIGNATURE where the string response message
    // in my local the code working i dont understand your code
    // this code working im your machine yes if you cant do that you can ask junior for detail
    //you mean this code if i send you to test it its working,, on this purchase request the multipart working

//    @PostMapping("/createlist")
//    public ResponseEntity<List<PurchaseRequisition>> createPurchases(@RequestBody List<PurchaseRequisition> purchaseRequisitions, Authentication authentication) {
//        UserDTO user = userService.getUserByEmail(getAuthenticatedUser(authentication).getEmail());
//        List<PurchaseRequisition> createdPurchases = new ArrayList<>();
//
//        for (PurchaseRequisition purchaseRequisition : purchaseRequisitions) {
//            PurchaseRequisition createdPurchase = purchaseRequisitionService.createPurchaseRequest(purchaseRequisition, user.getId());
//            String email = purchaseRequisition.getReceiverEmail();
//            String subject = "Purchase Request Send has been sent login Verification From Administrator";
//            String message = "Hello " + purchaseRequisition.getId() + " for Product Name" + purchaseRequisition.getItemDescription() + ", " +
//                    "\n A Purchase Request Email Verification Was Sent To \n" + purchaseRequisition.getReceiverEmail();
//            emailService.sendEmail(email, subject, message);
//            createdPurchases.add(createdPurchase);
//        }
//
//        return ResponseEntity.ok(createdPurchases);
//
//    }


//    @PostMapping(value = "/upload", consumes = { MediaType.APPLICATION_JSON_VALUE,
//            MediaType.MULTIPART_FORM_DATA_VALUE })
//    public ResponseEntity<List<PurchaseRequestDto>> uploadFiles(@RequestPart("files") List<MultipartFile> files,
//                                                                @RequestPart("purchaseRequestDto") List<PurchaseRequestDto> purchaseRequestDto) throws IOException {
//        log.info("POST - /purchase request for Multipart Upload - request -> '{}'", purchaseRequestDto);
//
//        if (files == null || files.isEmpty() || purchaseRequestDto == null || purchaseRequestDto.isEmpty()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        List<PurchaseRequestDto> savedPurchaseRequests = purchaseRequestService.saveFile(files, purchaseRequestDto);
//        return new ResponseEntity<>(savedPurchaseRequests, HttpStatus.CREATED);
//    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<PurchaseRequisition> findById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseRequisitionService.getPurchaseRequestById(id));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<PurchaseRequisition>> findAll() {
        return ResponseEntity.ok(purchaseRequisitionService.getAllPurchaseRequests());
    }

//    @PostMapping(path = "/")
//    public ResponseEntity<PurchaseRequests> save(@RequestBody PurchaseRequests purchaseRequests) {
//        return ResponseEntity.ok(purchaseRequestsService.(purchaseRequests));
//    }


    @GetMapping("/download/report")
    public ResponseEntity<Resource> downloadReport() {
        List<PurchaseRequisition> purchaseRequisitions = new ArrayList<>();
        // customerService.getCustomers().iterator().forEachRemaining(customers::add);

        purchaseRequisitionService.getAllPurchaseRequests().iterator().forEachRemaining(purchaseRequisitions::add);
        PurchaseRequisitionReport report = new PurchaseRequisitionReport(purchaseRequisitions);
        HttpHeaders headers = new HttpHeaders();
        headers.add("File-Name", "PurchaseRequisition-report.xlsx");
        headers.add(CONTENT_DISPOSITION, "attachment;File-Name=PurchaseRequisition-report.xlsx");
        return ResponseEntity.ok().contentType(parseMediaType("application/vnd.ms-excel"))
                .headers(headers).body(report.export());
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<PurchaseRequisition> updatePurchaseRequest(@PathVariable Long id, @RequestBody PurchaseRequisition purchaseRequisition) {
        purchaseRequisitionService.updatePurchaseRequest(purchaseRequisition, id);
        return ResponseEntity.ok(purchaseRequisitionService.getPurchaseRequestById(id)); //maybe that will return latest value,ok lets try
    }

    //correct , not correct you need getting latest purchase update,how is it writtern, if you want resturn purchase update need change service and repostiory to have return value not void,
    // or you can call get purchase id in here
    @GetMapping(path = "/user/{id}/{userId}")
    public ResponseEntity<PurchaseRequisitionDTO> findOneWithUser(@PathVariable Long id, @PathVariable Long userId) {
        return ResponseEntity.ok(purchaseRequisitionService.findOneWithUser(userId, id));
    }


    @GetMapping("/user/{userId}/purchase-requisition")
    public ResponseEntity<List<PurchaseRequisition>> getUserAllPurchaseRequests(@PathVariable Long userId) {
        List<PurchaseRequisition> purchaseRequests = purchaseRequisitionService.getUserAllPurchaseRequests(userId);
        return ResponseEntity.ok(purchaseRequests);
    }


    @PatchMapping("/update/signature")
    public ResponseEntity<HttpResponse> updateSignature(PurchaseRequisitionDTO purchaseRequisition, @RequestParam("image") MultipartFile image) throws InterruptedException {
        purchaseRequisitionService.updateSignature(purchaseRequisition, image);

        Long purchaseRequestId = purchaseRequisition.getId(); // Assuming PurchaseRequisitionDTO has a method to get the ID
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .data(Map.of("purchaseRequisition", purchaseRequisitionService.getPurchaseRequestById(purchaseRequestId)))
                        .timeStamp(now().toString())
                        .message("signature updated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


}
