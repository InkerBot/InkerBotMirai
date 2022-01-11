package bot.inker.mirai.event

import bot.inker.api.event.Event
import bot.inker.api.event.EventContext

class MiraiBoxEvent(
  val event: net.mamoe.mirai.event.Event
):Event {
  override val context = EventContext.empty()
  override fun toString(): String {
    return "MiraiBoxEvent($event)"
  }
}