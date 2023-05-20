package pp.ua.lifebook.db.user;

import org.apache.commons.dbcp2.BasicDataSource;

class UsersStorageMock extends UsersDbStorage {
    UsersStorageMock() {
        super(new BasicDataSource(), null);
    }
}
