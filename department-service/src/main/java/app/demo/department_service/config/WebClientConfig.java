package app.demo.department_service.config;

import app.demo.department_service.client.EmployeeClient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebClientConfig {

    LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    public EmployeeClient employeeClient() {
        return buildFactory("http://employee-service").createClient(EmployeeClient.class);
    }

    private HttpServiceProxyFactory buildFactory(String baseUrl) {
        final WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .filter(filterFunction)
                .build();
        return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();
    }

}
