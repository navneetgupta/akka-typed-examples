package com.navneetgupta.typed

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorSystem

sealed trait AlarmMessage
case class EnableAlarm(pincode: String) extends AlarmMessage
case class DisableAlarm(pincode: String) extends AlarmMessage
case object ActivityEvent extends AlarmMessage
case class ActivitySeen(what: String) extends AlarmMessage

object BurglarAlarm extends App {

  def enabledAlarm(pinCode: String): Behavior[AlarmMessage] =
    Behaviors.receive { (context, message) =>
      message match {
        case ActivityEvent =>
          context.log.warning("EOEOEOEOEOE ALARM ALARM!!!")
          Behaviors.same

        case DisableAlarm(`pinCode`) =>
          context.log.info("Correct pin entered, disabling alarm");
          disabledAlarm(pinCode)
        case _ => Behaviors.unhandled
      }

    }

  def disabledAlarm(pinCode: String): Behavior[AlarmMessage] =
    Behaviors.receivePartial {
      case (context, EnableAlarm(`pinCode`)) =>
        context.log.info("Correct pin entered, enabling alarm")
        enabledAlarm(pinCode)
    }

  val system = ActorSystem.create(enabledAlarm("0000"), "my-system")
  system.tell(ActivityEvent)
  system.tell(DisableAlarm("1234"))
  system.tell(ActivityEvent)
  system.tell(DisableAlarm("0000"))
  system.tell(ActivityEvent)
  system.tell(EnableAlarm("0000"))
  system.tell(ActivityEvent)
}
