package io.turntabl.tcmsEmail;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Receiver {
    public static List<Client> clients = new ArrayList<>();

    public void messageListener(Client client) {
        System.out.println("Get Message Listener: " + client);
        clients.add(client);
        System.out.println("Get All client : " + clients);

    }
    @ApiOperation("Get All Notification Published By Customer Service")
    @GetMapping("/v1/api/notifications")
    public List<Client> getNotifications() {
        return clients;
    }
}
