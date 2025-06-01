package DependencyInjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdDB implements DB {

    @Autowired
    private DevDB db;

    public  String getData() {
        return db.getData();

    }
}
