package DependencyInjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    private DevDB db;

    String getData() {
        return db.getData();

    }
}
