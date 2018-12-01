package contracts

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        urlPath('/api/welcome') {
        }
        headers {
            accept('application/json')
        }
    }
    response {
        status 400
    }
}
