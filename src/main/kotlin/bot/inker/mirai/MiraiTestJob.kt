package bot.inker.mirai

import org.quartz.Job
import org.quartz.JobExecutionContext

class MiraiTestJob:Job {
    override fun execute(context: JobExecutionContext) {
        System.out.println("ONE TIME.")
    }
}