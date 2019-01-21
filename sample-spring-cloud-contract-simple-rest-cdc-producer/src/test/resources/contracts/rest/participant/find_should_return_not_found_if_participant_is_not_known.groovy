package contracts
import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority: 1
    request {
        method("GET")
        urlPath("/api/participants") {
            queryParameters {[
                parameter("name", value("__not_known__"))
            ]}
        }
        headers {[
            accept(applicationJsonUtf8())
        ]}
    }
    response {
        status(404)
    }
}
