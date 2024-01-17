package io.getarrays.securecapita.repository.implementation;

import io.getarrays.securecapita.domain.PurchaseRequisition;
import io.getarrays.securecapita.dto.PurchaseRequisitionDTO;
import io.getarrays.securecapita.dto.UserDTO;
import io.getarrays.securecapita.exception.ApiException;
import io.getarrays.securecapita.purchaserequestnew.PurchaseRequestRowMapper;
import io.getarrays.securecapita.purchaserequestnew.PurchaseResponseDto;
import io.getarrays.securecapita.query.PurchaseQuery;
import io.getarrays.securecapita.repository.PurchaseRequestsRepository;
import io.getarrays.securecapita.rowmapper.PurchaseRequisitionRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.getarrays.securecapita.query.PurchaseQuery.*;
import static io.getarrays.securecapita.query.UserQuery.UPDATE_USER_IMAGE_QUERY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Map.of;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;


@Repository
@RequiredArgsConstructor
@Slf4j

public class PurchaseRequestsJdbc implements PurchaseRequestsRepository<PurchaseRequisition> {
    private static int lastGeneratedNumber = 99;
    private final NamedParameterJdbcTemplate jdbc;
 //where but department code there, rs is resultSet it must same with column datamase, you can matching with column database
 private static final Map<String, String> departmentCodeMap = new HashMap<>();

    @Value("${spring.datasource.url}")
    private String DB_URL;

    @Value("${spring.datasource.username}")
    private String DB_USERNAME;

    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;
    RowMapper<PurchaseRequisition> rowMapper = (rs, rowNum) -> {
        PurchaseRequisition purchaseRequest = new PurchaseRequisition();
        purchaseRequest.setId(rs.getLong("id"));
        purchaseRequest.setDate(rs.getDate("date"));
        purchaseRequest.setDepartmentCode(rs.getInt("department_code"));

        purchaseRequest.setReceiverEmail(rs.getString("receiver_email"));
        purchaseRequest.setReason(rs.getString("reason"));
        purchaseRequest.setItemNumber(rs.getInt("item_number"));
        purchaseRequest.setItemDescription(rs.getString("item_description"));
        purchaseRequest.setUnitPrice(rs.getInt("unit_price"));
        purchaseRequest.setQuantity(rs.getInt("quantity"));
        purchaseRequest.setEstimatedValue(rs.getInt("estimated_value"));
        purchaseRequest.setSignature(rs.getString("signature"));
        purchaseRequest.setUserId(rs.getLong("user_id"));
        return purchaseRequest;
    };

    @Override
    public PurchaseRequisition create(PurchaseRequisition purchaseRequests, Long userId) {
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = getSqlParameterSource(purchaseRequests, userId);
        jdbc.update(PurchaseQuery.INSERT_PurchaseRequisition_REQUEST_QUERY, parameters, holder);
        return purchaseRequests;
    }

    //its the same the format must same, an example before  wait i want explain. ab example before departmentCode become department_code it must same with database

    @Override
    public List<PurchaseRequisition> list() {
        try {
            String query = "SELECT * FROM purchaserequisition;";
            List<PurchaseRequisition> purchaseRequests = jdbc.query(query, rowMapper);                        //query(query, new UserRowMapper());
            return purchaseRequests;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred while retrieving the list of purchase request. Please try again.");
        }

    }


//    @Override
//    public PurchaseRequisition getUserPurchaseRequests(PurchaseRequisition purchaseRequests, Long userId) {
//
//        SqlParameterSource parameters = getSqlParameterSource(purchaseRequests, userId);
//        jdbc.update(PurchaseQuery.Get_PurchaseRequisitions_To_User_REQUEST_QUERY, parameters, holder);
//        return purchaseRequests;
//    }





    private SqlParameterSource getSqlParameterSource(PurchaseRequisition purchaseRequisition, Long userId) {
        return new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("id", purchaseRequisition.getId())
                .addValue("date", purchaseRequisition.getDate())
                .addValue("departmentCode", purchaseRequisition.getDepartmentCode())
                .addValue("department_code", generateDepartmentCode(""))
                .addValue("receiverEmail", purchaseRequisition.getReceiverEmail())
                .addValue("reason", purchaseRequisition.getReason())
                .addValue("itemNumber", purchaseRequisition.getItemNumber())
                .addValue("itemDescription", purchaseRequisition.getItemDescription())
                .addValue("unitPrice", purchaseRequisition.getUnitPrice())
                .addValue("quantity", purchaseRequisition.getQuantity())
                .addValue("estimatedValue", purchaseRequisition.getEstimatedValue())
                .addValue("signature", purchaseRequisition.getSignature());
    }


    @Override
    public PurchaseRequisition get(Long id) {
        try {

            return jdbc.queryForObject(PurchaseQuery.SELECT_PURCHASE_REQUESTS_BY_ID_QUERY, of("id", id), rowMapper);

        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No PURCHASE REQUESTS found by id: " + id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }


//    @Override
//    public void update(PurchaseRequests purchaseRequests, Long id) {
//
//    }
@Override
public void update(PurchaseRequisition data, Long id) {
    try {
        String updateQuery = "UPDATE purchaserequisition SET " +
                "date = :date, " +
                "department_code = :departmentCode, " +
                "receiver_email = :receiverEmail, " +
                "reason = :reason, " +
                "item_number = :itemNumber, " +
                "Item_description = :itemDescription, " +
                "unit_price = :unitPrice, " +
                "quantity = :quantity, " +
                "estimated_value = :estimatedValue, " +
                "user_id = :userId, " +
                "signature = :signature " +
                "WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("date", data.getDate())
                .addValue("departmentCode", data.getDepartmentCode())
                .addValue("receiverEmail", data.getReceiverEmail())
                .addValue("reason", data.getReason())
                .addValue("itemNumber", data.getItemNumber())
                .addValue("itemDescription", data.getItemDescription())
                .addValue("unitPrice", data.getUnitPrice())
                .addValue("quantity", data.getQuantity())
                .addValue("estimatedValue", data.getEstimatedValue())
                .addValue("signature", data.getSignature())
                .addValue("userId", data.getUserId())
                .addValue("id", id);

        jdbc.update(updateQuery, parameters);
    } catch (Exception exception) {
        log.error(exception.getMessage());
        throw new ApiException("An error occurred. Please try again.");
    }
}


    @Override
    public boolean delete(Long id) {
        try {
            String DELETE_FROM_PURCHASEREQUESTS_BY_PURCHASEREQUEST_ID = "DELETE FROM PURCHASEREQUEST WHERE id = :purchaserequestwId";
            jdbc.update(DELETE_FROM_PURCHASEREQUESTS_BY_PURCHASEREQUEST_ID, Collections.singletonMap("purchaserequestsId", id));
            return true;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }

    }

    @Override
    public PurchaseRequisition findOneWithUser(Long id) {
        try {
            return jdbc.queryForObject(SELECT_PURCHASE_REQUESTS_BY_ID_QUERY, of("id", id), new PurchaseRequisitionRowMapper());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }
    @Override
    public void updateSignature(PurchaseRequisitionDTO purchaseRequisition, MultipartFile image) {
        String purchaseRequisitionSignatureUrl = setPurchaseRequisitionSignatureUrl(purchaseRequisition.getId());
        saveSignature(purchaseRequisition.getId(), image);
        jdbc.update(UPDATE_PURCHASE_SIGNATURE_IMAGE_QUERY, Map.of("imageUrl", purchaseRequisitionSignatureUrl, "id", purchaseRequisition.getId()));
    }

    private String setPurchaseRequisitionSignatureUrl(Long id) {
        String idString = String.valueOf(id);
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/image/" + idString + ".png").toUriString();
    }

    private void saveSignature(Long id, MultipartFile image) {
        String idString = String.valueOf(id);
        Path fileStorageLocation = Paths.get(System.getProperty("user.home"), "Downloads", "images").toAbsolutePath().normalize();
        try {
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
                log.info("Created directories: {}", fileStorageLocation);
            }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(idString + ".png"), StandardCopyOption.REPLACE_EXISTING);
            log.info("File saved in: {} folder", fileStorageLocation);
        } catch (IOException exception) {
            log.error(exception.getMessage());
            throw new ApiException("Unable to save image: " + exception.getMessage());
        }
    }








    @Override
    public List<PurchaseRequisition> getUserAllPurchaseRequests(Long userId) {
        try {
            String query = "SELECT * FROM purchase_requests WHERE user_id = :userId";
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("userId", userId);
            List<PurchaseRequisition> purchaseRequests = jdbc.query(query, parameters, rowMapper);
            return purchaseRequests;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }




    }


    private String generateDepartmentCode(String departmentCode) {
        String prefix = getDepartmentCodePrefix(departmentCode);
        String generatedNumbers = generateUniqueNumbers();
        return prefix + generatedNumbers;
    }

    private String getDepartmentCodePrefix(String departmentCode) {
        int departmentCodeHyphenIndex = departmentCode.indexOf("-");
        if (departmentCodeHyphenIndex != -1) {
            return departmentCode.substring(0, departmentCodeHyphenIndex);
        }
        return departmentCode;
    }

    private String generateUniqueNumbers() {
        int maxNumber = 999;
        String generatedNumbers;
        boolean isUnique = false;

        while (!isUnique) {
            lastGeneratedNumber++;
            if (lastGeneratedNumber > maxNumber) {
                lastGeneratedNumber = 100; // Reset to the minimum number if it exceeds the maximum
            }
            generatedNumbers = String.format("%03d", lastGeneratedNumber);
            if (isDepartmentCodeUnique(generatedNumbers)) {
                isUnique = true;
                return generatedNumbers;
            }
        }
        return null;
    }

    private boolean isDepartmentCodeUnique(String generatedNumbers) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT COUNT(*) FROM stocks WHERE LOWER(department_code) = LOWER(?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, getDepartmentCodePrefix(generatedNumbers));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count == 0;
                    }
                }
            }
        } catch (SQLException sqlException) {
            log.error("Error while generating unique department code with exception - '{}' " +
                    "and exceptionMessage - '{}'", sqlException, sqlException.getMessage());
        }

        return false;
    }







}
