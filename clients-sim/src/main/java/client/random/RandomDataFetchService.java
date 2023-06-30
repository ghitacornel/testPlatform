package client.random;

import client.model.Client;
import client.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class RandomDataFetchService {

    private final Random random = new Random();
    private final ClientService clientService;

    public Client findRandomClient() {
        List<Client> all = clientService.findAll();
        if (all.isEmpty()) return null;
        int index = random.nextInt(all.size());
        return all.get(index);
    }

}
