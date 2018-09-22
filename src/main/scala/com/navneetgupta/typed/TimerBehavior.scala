package com.navneetgupta.typed

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import scala.concurrent.duration._
import akka.actor.typed.ActorSystem

object TimerBehavior extends App {
  case class Message(text: String)

  val behaviorWithTimer: Behavior[Message] = {
    Behaviors.withTimers {
      timers =>
        timers.startSingleTimer(
          "Single",
          Message("Single"),
          1 seconds)

        timers.startPeriodicTimer(
          "Periodic",
          Message("Periodic"),
          2 seconds)

        Behaviors.receive {
          (ctx, msg) =>
            ctx.log.info("Got Message {}", msg)
            Behavior.same
        }
    }
  }

  ActorSystem(behaviorWithTimer, "timer-behavior")

}
