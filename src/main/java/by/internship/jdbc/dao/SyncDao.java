package by.internship.jdbc.dao;

import by.internship.jdbc.model.db.SyncInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SyncDao {

   public Optional<SyncInfo> getLastSync();
   public void updateLastSync(SyncInfo syncInfo);

}
