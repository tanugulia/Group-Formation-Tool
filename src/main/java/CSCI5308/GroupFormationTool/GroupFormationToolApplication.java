package CSCI5308.GroupFormationTool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"CSCI5308.GroupFormationTool"})
@EnableAsync
public class GroupFormationToolApplication {
    public static void main(String[] args) {
        SpringApplication.run(GroupFormationToolApplication.class, args);
    }
}
