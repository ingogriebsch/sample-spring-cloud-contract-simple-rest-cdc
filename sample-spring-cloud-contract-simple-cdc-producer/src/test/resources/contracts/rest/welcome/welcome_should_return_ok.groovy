package contracts

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/api/welcome'
        headers {
            accept('application/json')
        }
    }
    response {
        status 200
        body([
            message: "Welcome stranger!"
        ])
        headers {
            contentType('application/json')
        }
    }
}
