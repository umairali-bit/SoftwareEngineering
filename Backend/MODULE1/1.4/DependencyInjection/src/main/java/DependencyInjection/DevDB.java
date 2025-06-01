package DependencyInjection;

import org.springframework.stereotype.Component;

@Component
public class DevDB {

    String getData() {
        return "DEV DATA";
    }
}

