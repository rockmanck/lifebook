package ua.lifebook.db.sync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.lifebook.db.LifeBookJdbc;

@Component
public class EnumSyncManager {
    @Autowired private LifeBookJdbc lifeBookJdbc;
    // TODO run it on server startup: fetch all enums, find related table and insert there all enum values
}
