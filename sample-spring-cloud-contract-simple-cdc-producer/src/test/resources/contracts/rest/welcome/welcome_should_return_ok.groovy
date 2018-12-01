package contracts
import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method("GET")
        urlPath("/api/welcome") {
            queryParameters {
                parameter("name", value(consumer(regex("[a-z]{5}"))))
            }
        }
        headers {
            accept(applicationJsonUtf8())
        }
    }
    response {
        status(200)
        body(message: "Welcome ${fromRequest().query('name')}!")
        headers {
            contentType(applicationJsonUtf8())
        }
    }
}
