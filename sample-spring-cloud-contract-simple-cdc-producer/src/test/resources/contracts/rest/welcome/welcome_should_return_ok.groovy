package contracts

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        urlPath('/api/welcome') {
            queryParameters {
                parameter 'name': value(consumer(regex('[a-z]{5}')))
            }
        }
        headers {
            accept('application/json')
        }
    }
    response {
        status 200
        body([
            message: "Welcome ${fromRequest().query('name')}!"
        ])
        headers {
            contentType('application/json')
        }
    }
}
