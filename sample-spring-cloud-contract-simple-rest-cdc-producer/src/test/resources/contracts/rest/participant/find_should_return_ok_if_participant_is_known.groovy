package contracts
import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method("GET")
        urlPath("/api/participants") {
            queryParameters {[
                parameter("name", value(regex("[A-Za-z]{1,}")))
            ]}
        }
        headers {[
            accept(applicationJsonUtf8())
        ]}
    }
    response {
        status(200)
        body([
            name: "${fromRequest().query('name')}"
        ])
        headers {[
            contentType(applicationJsonUtf8())
        ]}
    }
}
