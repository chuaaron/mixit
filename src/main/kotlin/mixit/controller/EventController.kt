package mixit.controller

import mixit.repository.EventRepository
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.web.reactive.function.BodyInsertersExtension.fromPublisher
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

class EventController(val repository: EventRepository) : RouterFunction<ServerResponse> {

    @Suppress("UNCHECKED_CAST") // TODO Relax generics check to avoid explicit casting
    override fun route(request: ServerRequest) = RouterFunctions
            .route(GET("/api/mixit{eventId}"), findOne())
            .andRoute(GET("/api/event/"), findAll())
            .route(request) as Mono<HandlerFunction<ServerResponse>>

    fun findOne() = HandlerFunction { req ->
        ok().contentType(APPLICATION_JSON_UTF8).body(fromPublisher(repository.findOne(req.pathVariable("eventId"))))
    }

    fun findAll() = HandlerFunction {
        ok().contentType(APPLICATION_JSON_UTF8).body(fromPublisher(repository.findAll()))
    }
}
