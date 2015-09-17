package com.badun.akkakamondemo.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.badun.akkakamondemo.message.Msg;
import com.badun.akkakamondemo.util.Sleeper;

import java.util.concurrent.TimeUnit;

/**
 * Created by Artsiom Badun.
 */
public class WorkerActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public WorkerActor() {
        receive(ReceiveBuilder
                .match(Msg.PieceOfWork.class, this::handleWorkMessage)
                .matchAny(this::unhandled)
                .build());
        log.debug("Created worker actor.");
    }

    private void handleWorkMessage(Msg.PieceOfWork message) {
        Sleeper.sleep(5);
        log.info("[WORKER] Actor " + this + " handled message: " + message);
        sender().tell(new Msg.WorkDone(), self());
    }
}
