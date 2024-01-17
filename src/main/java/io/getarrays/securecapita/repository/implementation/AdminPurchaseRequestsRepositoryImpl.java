package io.getarrays.securecapita.repository.implementation;

import io.getarrays.securecapita.domain.AdminPurchaseRequisition;
import io.getarrays.securecapita.domain.PurchaseRequisition;
import io.getarrays.securecapita.query.AdminPurchaseQuery;
import io.getarrays.securecapita.query.PurchaseQuery;
import io.getarrays.securecapita.repository.AdminPurchaseRequestsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import static io.getarrays.securecapita.query.AdminPurchaseQuery.*;

@Repository
@RequiredArgsConstructor
@Slf4j

public class AdminPurchaseRequestsRepositoryImpl implements AdminPurchaseRequestsRepository <AdminPurchaseRequisition>{

    private final NamedParameterJdbcTemplate jdbc;
    public AdminPurchaseQuery adminPurchaseQuery;
    RowMapper<AdminPurchaseRequisition> rowMapper = (rs, rowNum) -> {
        AdminPurchaseRequisition  adminPurchaseRequisition = new AdminPurchaseRequisition();
        adminPurchaseRequisition.setId(rs.getLong("id"));
        adminPurchaseRequisition.setDate(rs.getDate("date"));
        adminPurchaseRequisition.setReceiverEmail(rs.getString("receiverEmail"));
       adminPurchaseRequisition.setSignature(rs.getString("signature"));
       adminPurchaseRequisition.setUserId(rs.getLong("user_id"));
        return adminPurchaseRequisition;
    };


    @Override
    public AdminPurchaseRequisition create(AdminPurchaseRequisition adminPurchaseRequisition, Long userId) {
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = getSqlParameterSource(adminPurchaseRequisition, userId);
        jdbc.update(AdminPurchaseQuery.INSERT_AdminPurchaseRequisition_REQUEST_QUERY, parameters, holder);
        return adminPurchaseRequisition;
    }
    private SqlParameterSource getSqlParameterSource(AdminPurchaseRequisition    adminPurchaseRequisition, Long userId) {
        return new MapSqlParameterSource()
                .addValue("userId",adminPurchaseRequisition.getUserId())
                .addValue("id",adminPurchaseRequisition.getId())
                .addValue("date", adminPurchaseRequisition.getDate())
                .addValue("receiverEmail", adminPurchaseRequisition.getReceiverEmail())

                .addValue("signature", adminPurchaseRequisition.getSignature());
    }


















}
