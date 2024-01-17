package io.getarrays.securecapita.repository.implementation;

import io.getarrays.securecapita.domain.AdminPurchaseRequisition;
import io.getarrays.securecapita.domain.PrincipalAdministratorPurchaseRequisition;
import io.getarrays.securecapita.query.AdminPurchaseQuery;
import io.getarrays.securecapita.query.PrincipalAdministratorPurchaseRequisitionQuery;
import io.getarrays.securecapita.repository.PrincipalAdministratorPurchaseRequisitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PrincipalAdministratorPurchaseRequisitionImp implements PrincipalAdministratorPurchaseRequisitionRepository <PrincipalAdministratorPurchaseRequisition>{

    private final NamedParameterJdbcTemplate jdbc;
    public AdminPurchaseQuery adminPurchaseQuery;
    RowMapper<PrincipalAdministratorPurchaseRequisition> rowMapper = (rs, rowNum) -> {
        PrincipalAdministratorPurchaseRequisition  principalAdministratorPurchaseRequisition = new PrincipalAdministratorPurchaseRequisition();
        principalAdministratorPurchaseRequisition .setId(rs.getLong("id"));
        principalAdministratorPurchaseRequisition .setDate(rs.getDate("date"));
        principalAdministratorPurchaseRequisition .setReceiverEmail(rs.getString("receiverEmail"));
        principalAdministratorPurchaseRequisition .setSignature(rs.getString("signature"));
        principalAdministratorPurchaseRequisition .setUserId(rs.getLong("user_id"));
        return  principalAdministratorPurchaseRequisition ;
    };


    @Override
    public PrincipalAdministratorPurchaseRequisition create(PrincipalAdministratorPurchaseRequisition principalAdministratorPurchaseRequisition, Long userId) {
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = getSqlParameterSource(principalAdministratorPurchaseRequisition, userId);
        jdbc.update(PrincipalAdministratorPurchaseRequisitionQuery.INSERT_PrincipalAdministratorPurchaseRequisition_REQUEST_QUERY, parameters, holder);
        return principalAdministratorPurchaseRequisition;
    }
    private SqlParameterSource getSqlParameterSource(PrincipalAdministratorPurchaseRequisition principalAdministratorPurchaseRequisition, Long userId) {
        return new MapSqlParameterSource()
                .addValue("userId",principalAdministratorPurchaseRequisition.getUserId())
                .addValue("id",principalAdministratorPurchaseRequisition.getId())
                .addValue("date", principalAdministratorPurchaseRequisition.getDate())
                .addValue("receiverEmail", principalAdministratorPurchaseRequisition.getReceiverEmail())

                .addValue("signature", principalAdministratorPurchaseRequisition.getSignature());
    }



}
