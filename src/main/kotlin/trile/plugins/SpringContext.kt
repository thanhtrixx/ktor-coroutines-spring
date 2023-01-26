package trile.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.BaseApplicationPlugin
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.util.AttributeKey
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

fun Application.configureSpringContext() {
  install(SpringContextPlugin)
}

val springContextAttributeKey = AttributeKey<ApplicationContext>("springContext")

val ApplicationCall.springContext: ApplicationContext
  get() = attributes[springContextAttributeKey]

object SpringContextPlugin :
  BaseApplicationPlugin<Application, Unit, ApplicationContext> {

  override val key: AttributeKey<ApplicationContext> = springContextAttributeKey

  override fun install(
    pipeline: Application,
    configure: Unit.() -> Unit
  ): ApplicationContext {

    val applicationContext = AnnotationConfigApplicationContext(AppConfig::class.java)

    pipeline.intercept(ApplicationCallPipeline.Plugins) {
      call.attributes.put(springContextAttributeKey, applicationContext)
      proceed()
    }
    return applicationContext
  }
}

@Configuration
open class AppConfig {

  @Bean
  open fun helloMessage() = "Hello from Spring"
}
