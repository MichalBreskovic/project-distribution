package sk.kopr.consumer;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;
import java.util.logging.Logger;

@SpringBootApplication
@EnableScheduling
public class ConsumerApplication {

    public static final Logger logger = (Logger) LoggerFactory.getLogger(ArrivalConsumer.class);

    @Value("${consumer.queue}")
    private String platform;

    private List<List<Train>> trains = new ArrayList<>(5);

    public ConsumerApplication() {
        for(int i = 0; i < 5; i++){
            trains.set(i, new ArrayList<>());
        }
    }

    @RabbitListener(queues = "platform." + "${consumer.queue}")
    public void handleTrainArrival(Train train) {
        List<Train> t = trains.get(Integer.parseInt(platform));
        t.add(train);
        Collections.sort(t, Comparator.comparing(Train::getDepartureTime));
        trains.set(Integer.parseInt(platform), t);
    }

    @Scheduled(fixedRate = 2000)
    public void check() {
        int platform = Integer.parseInt(this.platform);
        List<Train> t = trains.get(platform);
        for(Train train : t) {
            if(train.getDepartureTime().compareTo(new Date()) < 0) {
                t.remove(train);
            }
        }
        trains.set(platform, t);
        printPlatformTable(platform);
    }

    public void printPlatformTable(int platform) {
        List<Train> t = trains.get(platform);
        logger.info("Vlak\tCas odchodu\tCielova stanica\tMeskanie");
        for(Train train : t) {
            logger.info(train.getType() + train.getNumber() + "\t" + train.getDepartureTime().toString() + "\t" + train.getDestination() + "\t" + train.getDelay() );
        }
    }

    @Bean
    TopicExchange stationExchange() {
        return ExchangeBuilder.topicExchange("station").build();
    }

    @Bean
    Queue platformQueue() {
        return new Queue("platform." + platform);
    }

    @Bean
    Binding stationToPlatformBinding() {
        return BindingBuilder
                .bind(platformQueue())
                .to(stationExchange())
                .with("station.platform." + platform);
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
