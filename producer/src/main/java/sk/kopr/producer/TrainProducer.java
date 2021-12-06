package sk.kopr.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TrainProducer {

    @Autowired
    AmqpTemplate amqpTemplate;

//    @Scheduled(fixedRate = 2000)
    public void registerTrainArrival(String type, Long number, int platform, Date arrivalTime) {
        Train train = new Train();
        train.setType(type);
        train.setNumber(number);
        train.setPlatform(platform);
        train.setArrivalTime(arrivalTime);

        amqpTemplate.convertAndSend("station", "station.platform." + platform, train);
    }

    public void registerTrainDeparture(String type, Long number, int platform, Date departureTime) {
        Train train = new Train();
        train.setType(type);
        train.setNumber(number);
        train.setPlatform(platform);
        train.setDepartureTime(departureTime);

        amqpTemplate.convertAndSend("station", "station.platform." + platform, train);
    }

    public void registerTrainDelay(String type, Long number, int platform, int delay) {
        Train train = new Train();
        train.setType(type);
        train.setNumber(number);
        train.setPlatform(platform);
        train.setDelay(delay);

        amqpTemplate.convertAndSend("station", "station.platform." + platform, train);
    }

    @Scheduled(fixedRate = 5000)
    public void generateTrain() {
        Train train = new Train();
        train.setType("R");
        train.setNumber(Double.doubleToLongBits(Math.random() * 1000));
        int platform = (int)(Math.random() * 5);
        train.setPlatform(platform);
        train.setDelay((int)(Math.random() * 450));
        train.setArrivalTime(new Date());
        Date d = new Date();
        d.setMinutes(train.getArrivalTime().getMinutes() + 5);
        train.setDepartureTime(d);

        amqpTemplate.convertAndSend("station", "station.platform." + platform, train);
    }

}
