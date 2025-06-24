package by.internship.jdbc.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncInfo {

    private UUID id;

    private LocalDateTime lastSync;

}
