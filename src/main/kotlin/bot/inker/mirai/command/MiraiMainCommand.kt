package bot.inker.mirai.command

import bot.inker.api.command.permission
import bot.inker.api.event.AutoComponent
import bot.inker.api.event.EventHandler
import bot.inker.api.event.lifestyle.LifecycleEvent
import javax.inject.Singleton

@Singleton
@AutoComponent
class MiraiMainCommand {
  @EventHandler
  fun onRegisterCommand(event:LifecycleEvent.RegisterCommand){
    event.register("bot/inker/mirai"){
      describe = "管理 Mirai 的Bot"
      permission("mirai.command")
      ConfigCommand(this)
      LoginCommand(this)
    }
  }
}