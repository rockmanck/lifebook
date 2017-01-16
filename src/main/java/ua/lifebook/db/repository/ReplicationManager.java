package ua.lifebook.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import ua.lifebook.plans.Plan;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReplicationManager {
    private final boolean isPrimaryNode;
    private final Path localStorage;

    @Autowired
    public ReplicationManager(boolean isPrimaryNode, String localStoragePath) {
        this.isPrimaryNode = isPrimaryNode;
        this.localStorage = Paths.get(localStoragePath);

        // create local storage path for secondary node if it is not created yet
        if (!isPrimaryNode) {
            if (!Files.exists(localStorage)) {
                try {
                    Files.createDirectories(localStorage);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to create local replication storage: " + localStorage.toString(), e);
                }
            }
        }
    }

    public void store(Object bean) {
        if (isPrimaryNode) return;

        final Class<?> beanClass = bean.getClass();
        final Table table = beanClass.getAnnotation(Table.class);
        if (table == null) return;


    }

    public static void main(String[] args) {
        final Object plan = new Plan();
        System.out.println(plan.getClass().getAnnotation(Table.class));
    }
}
