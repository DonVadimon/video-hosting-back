package com.example.pudge.domain.exception

class NotFoundException : RuntimeException {
    constructor(message: String?) : super(message)

    constructor(clazz: Class<*>, id: Long) : super(
        String.format(
            "Entity %s with id %d not found", clazz.simpleName, id
        )
    )

    constructor(clazz: Class<*>, id: String?) : super(
        String.format(
            "Entity %s with id %s not found", clazz.simpleName, id
        )
    )
}
