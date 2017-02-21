package nl.openweb.iot.monitor;


import java.util.function.BiConsumer;

import lombok.Setter;
import nl.openweb.iot.monitor.domain.Reading;
import nl.openweb.iot.monitor.repository.ReadingRepository;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.grove.*;
import nl.openweb.iot.wio.scheduling.ScheduledTask;
import nl.openweb.iot.wio.scheduling.SchedulingUtils;
import nl.openweb.iot.wio.scheduling.TaskContext;

public class Monitor implements ScheduledTask {

    protected final int period;
    protected final ReadingRepository repository;
    protected final ReadingStrategy strategy;
    @Setter
    private BiConsumer<Reading, TaskContext> alertMonitor;

    public Monitor(int period, ReadingRepository repository, ReadingStrategy strategy) {
        this.period = period;
        this.repository = repository;
        this.strategy = strategy;
    }

    @Override
    public TaskExecutionResult execute(Node node, TaskContext context) throws WioException {
        TaskExecutionResult nextRun = SchedulingUtils.secondsLater(period);
        Reading reading = new Reading(node.getName());
        for (Grove grove : node.getGroves()) {
            addData(grove, reading);
        }
        repository.save(reading);
        if (alertMonitor != null) {
            alertMonitor.accept(reading, context);
        }
        return nextRun;
    }

    protected void addData(Grove grove, Reading reading) throws WioException {
        if (GroveTempHum.class.isAssignableFrom(grove.getClass())) {
            addData((GroveTempHum) grove, reading);
        } else if (GroveLuminance.class.isAssignableFrom(grove.getClass())) {
            addData((GroveLuminance) grove, reading);
        } else if (GroveDust.class.isAssignableFrom(grove.getClass())) {
            addData((GroveDust) grove, reading);
        } else if (GroveAirQuality.class.isAssignableFrom(grove.getClass())) {
            addData((GroveAirQuality) grove, reading);
        } else if (GroveRelay.class.isAssignableFrom(grove.getClass())) {
            addData((GroveRelay) grove, reading);
        } else if (GroveMoisture.class.isAssignableFrom(grove.getClass())) {
            addData((GroveMoisture) grove, reading);
        } else if (GroveMagneticSwitch.class.isAssignableFrom(grove.getClass())) {
            addData((GroveMagneticSwitch) grove, reading);
        } else if (GroveUltraRanger.class.isAssignableFrom(grove.getClass())) {
            addData((GroveUltraRanger) grove, reading);
        } else if (GroveServo.class.isAssignableFrom(grove.getClass())) {
            addData((GroveServo) grove, reading);
        } else if (GroveLoudness.class.isAssignableFrom(grove.getClass())) {
            addData((GroveLoudness) grove, reading);
        } else if (GroveSound.class.isAssignableFrom(grove.getClass())) {
            addData((GroveSound) grove, reading);
        } else if (GroveCo2.class.isAssignableFrom(grove.getClass())) {
            addData((GroveCo2) grove, reading);
        }
    }

    private void addData(GroveTempHum grove, Reading reading) throws WioException {
        reading.setTemperature(strategy.readDouble(grove::readTemperature));
        reading.setHumidity(strategy.readDouble(grove::readHumidity));
    }

    private void addData(GroveLoudness grove, Reading reading) throws WioException {
        reading.setLoudness(strategy.readInteger(grove::readLoudness));
    }

    private void addData(GroveSound grove, Reading reading) throws WioException {
        reading.setLoudness(strategy.readInteger(grove::readSoundLevel));
    }

    private void addData(GroveLuminance grove, Reading reading) throws WioException {
        reading.setLuminance(strategy.readDouble(grove::readLuminance));
    }

    private void addData(GroveDust grove, Reading reading) throws WioException {
        reading.setDust(strategy.readDouble(grove::readDust));
    }

    private void addData(GroveAirQuality grove, Reading reading) throws WioException {
        reading.setAirQuality(strategy.readInteger(grove::readQuality));
    }

    private void addData(GroveRelay grove, Reading reading) throws WioException {
        reading.setRelay(grove.readOnOff());
    }

    private void addData(GroveMoisture grove, Reading reading) throws WioException {
        reading.setMoisture(strategy.readInteger(grove::readMoisture));
    }

    private void addData(GroveCo2 grove, Reading reading) throws WioException {
        reading.setCo2(strategy.readDouble(grove::readConcentration));
    }

    private void addData(GroveMagneticSwitch grove, Reading reading) throws WioException {
        reading.setMagneticSwitch(1 == grove.readApproach());
    }

    private void addData(GroveUltraRanger grove, Reading reading) throws WioException {
        reading.setUltraRanger(grove.readRangeInCm());
    }

    private void addData(GroveServo grove, Reading reading) throws WioException {
        reading.setServoAngle(grove.readAngle());
    }

}
