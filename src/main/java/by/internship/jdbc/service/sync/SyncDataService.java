package by.internship.jdbc.service.sync;

import by.internship.jdbc.model.db.SyncInfo;
import by.internship.jdbc.service.db.SyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncDataService {

    private static final String SYNC_START_MSG = "Full sync started";
    private static final String SYNC_COMPLETE_MSG = "Full sync complete";

    private final SyncEmployeeService syncEmployeeService;
    private final SyncProjectService syncProjectService;
    private final SyncEmployeeProjectService syncEmployeeProjectService;
    private final SyncService syncService;

    @Scheduled(fixedDelay = 30*1000)
    public void syncAll() {
        //LocalDateTime lastSync = Optional.ofNullable(syncService.getLastSyncInfo().getLastSyncDateTime()).orElse(LocalDateTime.MIN);

        LocalDateTime lastSync = Optional.ofNullable(syncService.getLastSyncInfo())
                .map(SyncInfo::getLastSyncDateTime)
                .orElse(LocalDateTime.MIN);

        syncEmployeeService.syncEmployees(lastSync);
        syncProjectService.syncProjects(lastSync);
        syncEmployeeProjectService.syncEmployeeProject(lastSync);

        syncService.updateLastSyncInfo(new SyncInfo(UUID.randomUUID(), LocalDateTime.now(), null));
        log.info(SYNC_COMPLETE_MSG);
    }

}
