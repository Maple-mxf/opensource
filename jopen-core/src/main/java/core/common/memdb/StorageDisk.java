package core.common.memdb;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * data  storage
 *
 * @author maxuefeng
 * @since 2019/9/24
 */
public class StorageDisk {

    private Set<Database> databases = new CopyOnWriteArraySet<>();

    static StorageDisk instance = new StorageDisk();

    private StorageDisk() {
    }

    public Database getDatabase(String name) {
        Optional<Database> optional = databases.stream().filter(db -> db.name().equals(name)).findAny();
        return optional.orElse(null);
    }

    void addDatabase(Database database) {
        Optional<Database> optional = databases.stream().filter(db -> db.name().equals(database.name())).findAny();
        if (optional.isPresent()) {
            throw new IllegalArgumentException("data exsited");
        }
        databases.add(database);
    }

    Boolean contain(String name) {
        Optional<Database> optional = databases.stream().filter(db -> db.name().equals(name)).findAny();
        return optional.isPresent();
    }
}
