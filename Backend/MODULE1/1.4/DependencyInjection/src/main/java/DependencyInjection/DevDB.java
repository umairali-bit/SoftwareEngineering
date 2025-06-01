package DependencyInjection;

import org.springframework.stereotype.Component;

@Component
public class DevDB implements DB{

   public String getData() {
        return "DEV DATA";
    }
}

