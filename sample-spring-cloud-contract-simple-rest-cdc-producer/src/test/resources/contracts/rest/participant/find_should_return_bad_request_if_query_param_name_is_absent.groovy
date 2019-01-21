package contracts
import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method("GET")
        urlPath("/api/participants") {
            queryParameters {[
                parameter("name", absent())
            ]}
        }
        headers {[
            accept(applicationJsonUtf8())
        ]}
    }
    response {
        status(400)
    }
}
