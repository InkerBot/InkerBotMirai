package bot.inker.mirai.event

import bot.inker.api.event.*
import bot.inker.api.event.message.MessageEvent
import bot.inker.api.event.message.PrivateMessageEvent
import bot.inker.api.model.Member
import bot.inker.api.model.message.MessageComponent
import bot.inker.api.model.message.PlainTextComponent
import bot.inker.mirai.model.MiraiMember
import bot.inker.mirai.util.message.MiraiTranslator
import net.mamoe.mirai.contact.User
import javax.inject.Inject
import javax.inject.Singleton

class MiraiFriendMessageEvent(
  user: User,
  override val message: MessageComponent
): PrivateMessageEvent,MiraiMessageEvent {
  override var cancelled: Boolean = false
  override val sender: Member = MiraiMember.of(user)

  override fun sendMessage(message: MessageComponent) {
    sender.sendMessage(message)
  }

  override val context: EventContext = EventContext.empty()

  override fun toString(): String {
    return "MiraiFriendMessageEvent(message=$message, sender=$sender)"
  }

  @AutoComponent
  @Singleton
  class Resolver{
    @Inject
    private lateinit var eventManager: EventManager

    @EventHandler(order = Order.POST)
    fun onMiraiEvent(e:MiraiBoxEvent){
      if (e.event is net.mamoe.mirai.event.events.FriendMessageEvent) {
        eventManager.post(MiraiFriendMessageEvent(
          e.event.sender,
          MiraiTranslator.toInk(e.event.message)
        ))
      }
    }
  }
}