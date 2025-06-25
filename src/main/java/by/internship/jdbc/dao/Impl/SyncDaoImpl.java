package by.internship.jdbc.dao.Impl;

import by.internship.jdbc.dao.SyncDao;
import by.internship.jdbc.exception.JdbcOperationException;
import by.internship.jdbc.model.db.SyncInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@PropertySource("classpath:sql.properties")
public class SyncDaoImpl implements SyncDao {

    private static final String FAILED_TO_GET_LAST_SYNC_ERR_MSG = "Failed to get last sync";
    private static final String FAILED_TO_UPDATE_LAST_SYNC_ERR_MSG = "Failed to update last sync";


    @Value("${sql.syncInfo.getLastSync}")
    private String getLastSyncQuery;

    @Value("${sql.syncInfo.updateLastSync}")
    private String updateLastSyncQuery;

    private final DataSource dataSource;

    public SyncDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<SyncInfo> getLastSync() {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(getLastSyncQuery);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                SyncInfo syncInfo = new SyncInfo();
                syncInfo.setId(UUID.fromString(rs.getString("id")));
                syncInfo.setLastSyncDateTime(rs.getTimestamp("last_sync_date_time").toLocalDateTime());
                syncInfo.setUpdatedRows(rs.getInt("updated_rows"));
                return Optional.of(syncInfo);
            }
        } catch (SQLException e) {
            log.error(FAILED_TO_GET_LAST_SYNC_ERR_MSG);
            throw new JdbcOperationException(FAILED_TO_GET_LAST_SYNC_ERR_MSG, e);
        }

        return Optional.empty();
    }

    @Override
    public void updateLastSync(SyncInfo syncInfo) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(updateLastSyncQuery)) {

            ps.setObject(1, syncInfo.getId());
            ps.setObject(2, Timestamp.valueOf(syncInfo.getLastSyncDateTime()));
            ps.setObject(3, syncInfo.getUpdatedRows());
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(FAILED_TO_UPDATE_LAST_SYNC_ERR_MSG);
            throw new JdbcOperationException(FAILED_TO_UPDATE_LAST_SYNC_ERR_MSG, e);
        }

    }
}
