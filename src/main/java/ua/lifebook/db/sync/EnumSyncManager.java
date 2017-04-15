package ua.lifebook.db.sync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.lifebook.db.PlansService;

@Component
public class EnumSyncManager {
    @Autowired private PlansService plansService;
    // TODO run it on server startup: fetch all enums, find related table and insert there all enum values
}
