package contracts
import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method("GET")
        urlPath("/api/participant") {
            queryParameters {
                parameter("name", "")
            }
        }
        headers {
            accept(applicationJsonUtf8())
        }
    }
    response {
        status(400)
    }
}
