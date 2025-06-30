package by.internship.jdbc.service.db;

import by.internship.jdbc.model.db.SyncInfo;

public interface SyncService {

    public SyncInfo getLastSyncInfo();
    public void updateLastSyncInfo(SyncInfo syncInfo);

}
