package contracts
import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method("POST")
        urlPath("/api/participants") {
            body([
                name: ""
            ])
        }
        headers {[
            accept(applicationJsonUtf8()),
            contentType(applicationJsonUtf8())
        ]}
    }
    response {
        status(400)
    }
}
