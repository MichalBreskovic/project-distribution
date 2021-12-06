package sk.kopr.consumer;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.convert.Property;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class ArrivalConsumer {
    public static final Logger logger = (Logger) LoggerFactory.getLogger(ArrivalConsumer.class);

    private List<Train> trains = new LinkedList<>();

    @RabbitListener(queues = "")
    public void handleTrainArrival(Train train) {
        trains.add(train);
        Collections.sort(trains, Comparator.comparing(Train::getArrivalTime));
    }

    @Scheduled(fixedRate = 2000)
    public void check() {

    }
}
