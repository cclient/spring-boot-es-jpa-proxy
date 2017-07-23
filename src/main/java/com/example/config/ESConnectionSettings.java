package com.example.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cclie on 2016/7/1.
 */
//@Component
@ConfigurationProperties(prefix = "es")
public class ESConnectionSettings {

    @NotEmpty
    private String clustername;
    @NotNull
    private List<String> servers = new ArrayList<String>();

    public String getClustername() {
        return clustername;
    }

    public void setClustername(String clustername) {
        this.clustername = clustername;
    }

    public List<String> getServers() {
        return this.servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

}
