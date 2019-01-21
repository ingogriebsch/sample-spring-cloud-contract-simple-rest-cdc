package contracts
import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method("POST")
        urlPath("/api/participants") {
            body([
                name: value(regex("[A-Za-z]{1,}"))
            ])
        }
        headers {[
            accept(applicationJsonUtf8()),
            contentType(applicationJsonUtf8())
        ]}
    }
    response {
        status(201)
        body([
            name: "${fromRequest().body('$.name')}"
        ])
        headers {[
            contentType(applicationJsonUtf8())
        ]}
    }
}
