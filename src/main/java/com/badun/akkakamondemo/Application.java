package com.badun.akkakamondemo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.badun.akkakamondemo.actor.ManagerActor;
import com.badun.akkakamondemo.actor.WorkerActor;
import com.badun.akkakamondemo.message.Msg;
import com.typesafe.config.ConfigFactory;
import kamon.Kamon;

/**
 * Created by Artsiom Badun.
 */
public class Application {

    public static void main(String[] args) {
        Kamon.start();

        ActorSystem system = ActorSystem.create("ActorSystem", ConfigFactory.load());

        ActorRef worker1 = system.actorOf(Props.create(WorkerActor.class).withDispatcher("workerDispatcher"), "worker1");
        ActorRef worker2 = system.actorOf(Props.create(WorkerActor.class).withDispatcher("workerDispatcher"), "worker2");
        ActorRef worker3 = system.actorOf(Props.create(WorkerActor.class).withDispatcher("workerDispatcher"), "worker3");

        ActorRef manager = system.actorOf(Props.create(ManagerActor.class).withDispatcher("managerDispatcher"), "manager");

        manager.tell(new Msg.WorkDone(), worker1);
        manager.tell(new Msg.WorkDone(), worker2);
        manager.tell(new Msg.WorkDone(), worker3);
    }
}
