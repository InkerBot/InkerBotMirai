package bot.inker.mirai.command

import bot.inker.api.command.permission
import bot.inker.api.event.message.MessageEvent
import com.eloli.inkcmd.ktdsl.LiteralArgumentBuilderDsl
import com.eloli.inkcmd.values.BoolValueType
import bot.inker.mirai.command.config.ConfigGroupCommand
import bot.inker.mirai.command.config.ConfigPasswordCommand
import bot.inker.mirai.command.config.ConfigUsernameCommand

object LoginCommand{
  operator fun invoke(it: LiteralArgumentBuilderDsl<MessageEvent>) {
    it.apply {
      literal("login"){
        permission("mirai.command.login")
        describe = "应用 Mirai 所用的用户名，密码"
      }
    }
  }
}