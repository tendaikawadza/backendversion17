package io.getarrays.securecapita.repository.implementation;

import io.getarrays.securecapita.domain.DeputyHeadAdministrationRequisition;
import io.getarrays.securecapita.query.DeputyHeadAdministrationQuery;
import io.getarrays.securecapita.repository.DeputyHeadPurchaseRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DeputyHeadPurchaseRequisitionImpl implements DeputyHeadPurchaseRequestRepository {
    private final NamedParameterJdbcTemplate jdbc;
    @Override
    public DeputyHeadAdministrationRequisition create(DeputyHeadAdministrationRequisition deputyHeadAdministrationRequisition, Long userId) {
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = getSqlParameterSource(deputyHeadAdministrationRequisition, userId);
        jdbc.update(DeputyHeadAdministrationQuery.INSERT_DeputyHeadAdministrationRequisition_REQUEST_QUERY, parameters, holder);
        return deputyHeadAdministrationRequisition;
    }
    private SqlParameterSource getSqlParameterSource(DeputyHeadAdministrationRequisition adminPurchaseRequisition, Long userId) {
        return new MapSqlParameterSource()
                .addValue("userId",adminPurchaseRequisition.getUserId())
                .addValue("id",adminPurchaseRequisition.getId())
                .addValue("date", adminPurchaseRequisition.getDate())
                .addValue("receiverEmail", adminPurchaseRequisition.getReceiverEmail())

                .addValue("signature", adminPurchaseRequisition.getSignature());








    }
}
