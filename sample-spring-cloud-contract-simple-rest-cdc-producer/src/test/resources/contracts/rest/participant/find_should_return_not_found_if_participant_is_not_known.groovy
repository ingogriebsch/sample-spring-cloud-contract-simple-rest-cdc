package contracts
import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method("GET")
        urlPath("/api/participants") {
            queryParameters {[
                parameter("name", value("__unknown__"))
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
