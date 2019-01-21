package contracts
import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority: 1
    request {
        method("GET")
        urlPath("/api/participants") {
            queryParameters {[
                parameter("name", value("_unknown_"))
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
