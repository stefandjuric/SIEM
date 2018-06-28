package com.example.siem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "agents")
public class AgentData {

    @Id
    private String id;

    private String[] filePaths;

    private String[] types;

    private boolean enabled;

    private String role;

    private String[] ports;

    private String port;

    private String name;

    private String batch;

    private String level;

    @Override
    public String toString() {
        return "AgentData{" +
                "id='" + id + '\'' +
                ", filePaths=" + Arrays.toString(filePaths) +
                ", types=" + Arrays.toString(types) +
                ", enabled=" + enabled +
                ", role='" + role + '\'' +
                ", ports=" + Arrays.toString(ports) +
                ", port='" + port + '\'' +
                ", name='" + name + '\'' +
                ", batch='" + batch + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
