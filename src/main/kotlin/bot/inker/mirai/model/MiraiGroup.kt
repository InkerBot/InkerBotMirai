package bot.inker.mirai.model

import bot.inker.api.InkerBot
import bot.inker.api.model.Group
import bot.inker.api.model.message.MessageComponent
import bot.inker.api.registry.Registries
import bot.inker.api.registry.UpdatableRegistrar
import bot.inker.api.util.Identity
import bot.inker.api.util.ResourceKey
import bot.inker.mirai.MiraiCore
import bot.inker.mirai.util.message.MiraiTranslator
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

class MiraiGroup(
  private val record:Record
):Group {
  override fun sendMessage(message: MessageComponent) {
    runBlocking {
      InkerBot(MiraiCore::class).bot.getGroupOrFail(record.groupNumber.toLong()).sendMessage(
        MiraiTranslator.toMirai(message)
      )
    }
  }

  override val identity: Identity = Identity.of(record.uuid)
  override val key: ResourceKey = KEY
  override val name: String = record.name

  companion object {
    val KEY = ResourceKey.of("bot/inker/mirai","group")
    val registrar: UpdatableRegistrar<Group, MiraiGroup, Record>
      get() {
        return Registries.group.get(KEY) as UpdatableRegistrar<Group, MiraiGroup, Record>
      }

    fun of(group: net.mamoe.mirai.contact.Group): Group {
      return update(group.id.toString()){
        it.name = group.name
      }
    }

    fun of(groupNumber: String): Optional<Group> {
      return registrar.get(Identity.of(KEY.toString()+groupNumber))
    }

    fun update(groupNumber: String, command: (Record) -> Unit): MiraiGroup {
      val identity = Identity.of(KEY.toString() + groupNumber)
      return registrar.update(identity) {
        it.uuid = identity.uuid
        it.groupNumber = groupNumber
        command(it)
      }
    }
  }

  @Entity(name = "mirai_group_record")
  class Record:Cloneable {
    @Id
    @Column
    lateinit var uuid: UUID

    @Column
    lateinit var name: String

    @Column
    lateinit var groupNumber: String
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as Record

      if (uuid != other.uuid) return false
      if (name != other.name) return false
      if (groupNumber != other.groupNumber) return false

      return true
    }

    override fun hashCode(): Int {
      var result = uuid.hashCode()
      result = 31 * result + name.hashCode()
      result = 31 * result + groupNumber.hashCode()
      return result
    }


  }
}