package io.studyit.gateway.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "studyit.gateway.filter")
@Getter @Setter
public class FilterProperties {
    private List<String> whitelist;
}
