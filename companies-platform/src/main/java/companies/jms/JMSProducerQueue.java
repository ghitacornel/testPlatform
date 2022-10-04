package companies.jms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;

@Slf4j
@Service
@RequiredArgsConstructor
public class JMSProducerQueue {

    private final Queue queueCompanyCancellation;
    private final JmsTemplate jmsTemplate;

    public void sendDeleteMessage(Integer id) {
        jmsTemplate.convertAndSend(queueCompanyCancellation, id);
        log.info("company delete message send " + id);
    }


}
