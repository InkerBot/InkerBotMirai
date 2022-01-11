package bot.inker.mirai.command

import bot.inker.api.command.permission
import bot.inker.api.event.message.MessageEvent
import com.eloli.inkcmd.ktdsl.LiteralArgumentBuilderDsl
import com.eloli.inkcmd.values.BoolValueType
import bot.inker.mirai.command.config.ConfigGroupCommand
import bot.inker.mirai.command.config.ConfigPasswordCommand
import bot.inker.mirai.command.config.ConfigUsernameCommand

object ConfigCommand{
  operator fun invoke(it: LiteralArgumentBuilderDsl<MessageEvent>) {
    it.apply {
      literal("config"){
        permission("mirai.command.config")
        describe = "配置 Mirai 所用的用户名，密码"
        option("login-now", BoolValueType.bool()){
          describe("在配置完成后立即应用")
          defaultValue(false)
          defineValue(true)
        }
        ConfigUsernameCommand(this)
        ConfigPasswordCommand(this)
        ConfigGroupCommand(this)
      }
    }
  }
}