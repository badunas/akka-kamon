package com.badun.akkakamondemo.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.badun.akkakamondemo.message.Msg;
import com.badun.akkakamondemo.util.Sleeper;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by Artsiom Badun.
 */
public class ManagerActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private int taskNumber = 1000;

    @Override
    public void preStart() {
        context().setReceiveTimeout(Duration.create(5, TimeUnit.SECONDS));
    }

    public ManagerActor() {
        receive(ReceiveBuilder
                .match(Msg.WorkDone.class, this::handleWorkerMessage)
                .matchAny(this::unhandled)
                .build());
        log.debug("Created manager actor.");
    }

    private void handleWorkerMessage(Msg.WorkDone message) {
        sender().tell(buildWorkMessage(), self());
        log.info("[MANAGER] Manager send a peace of work to worker by worker request.");
    }

    private Msg.PieceOfWork buildWorkMessage() {
        return new Msg.PieceOfWork("Task: " + self().path() + "_" + taskNumber++);
    }
}
