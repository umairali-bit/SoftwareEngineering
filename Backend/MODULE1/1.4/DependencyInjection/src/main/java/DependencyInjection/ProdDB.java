package DependencyInjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Primary
public class ProdDB implements DB {

    @Autowired
    private DevDB db;

    public  String getData() {
        return "Prod Data";

    }
}
