package by.internship.jdbc.service.db.Impl;

import by.internship.jdbc.dao.SyncDao;
import by.internship.jdbc.model.db.SyncInfo;
import by.internship.jdbc.service.db.SyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncServiceImpl implements SyncService {

    private static final String GET_LAST_SYNC = "Getting last sync";
    private static final String UPDATE_LAST_SYNC = "Updating last sync";

    private final SyncDao syncDao;

    @Override
    public SyncInfo getLastSyncInfo() {
        log.info(GET_LAST_SYNC);
        return syncDao.getLastSync().orElse(null);
    }

    @Override
    public void updateLastSyncInfo(SyncInfo syncInfo) {
        log.info(UPDATE_LAST_SYNC);
        syncDao.updateLastSync(syncInfo);
    }
}
