package contracts
import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority: 5
    request {
        method("GET")
        urlPath("/api/participants")
        headers {[
            accept(applicationJsonUtf8())
        ]}
    }
    response {
        status(400)
    }
}
