package com.example.pudge.configuration


import com.example.pudge.domain.exception.NotFoundException
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.*
import java.util.function.Consumer
import javax.servlet.http.HttpServletRequest
import javax.xml.bind.ValidationException


@ControllerAdvice
class GlobalExceptionHandler {
    private val logger = LogManager.getLogger()

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(
        request: HttpServletRequest, ex: NotFoundException
    ): ResponseEntity<ApiCallError<String?>> {
        logger.error("NotFoundException {}\n", request.requestURI, ex)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiCallError("Not found exception", listOf(ex.message)))
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(
        request: HttpServletRequest, ex: ValidationException
    ): ResponseEntity<ApiCallError<String?>> {
        logger.error("ValidationException {}\n", request.requestURI, ex)
        return ResponseEntity.badRequest().body(ApiCallError("Validation exception", listOf(ex.message)))
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(
        request: HttpServletRequest, ex: MissingServletRequestParameterException
    ): ResponseEntity<ApiCallError<String>> {
        logger.error("handleMissingServletRequestParameterException {}\n", request.requestURI, ex)
        return ResponseEntity.badRequest().body(ApiCallError("Missing request parameter", listOf(ex.message)))
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(
        request: HttpServletRequest, ex: MethodArgumentTypeMismatchException
    ): ResponseEntity<ApiCallError<Map<String, String?>>> {
        logger.error("handleMethodArgumentTypeMismatchException {}\n", request.requestURI, ex)
        val details: MutableMap<String, String?> = HashMap()
        details["paramName"] = ex.name
        details["paramValue"] = Optional.ofNullable(ex.value).map { obj: Any -> obj.toString() }.orElse("")
        details["errorMessage"] = ex.message
        return ResponseEntity.badRequest()
            .body(ApiCallError("Method argument type mismatch", listOf<Map<String, String?>>(details)))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        request: HttpServletRequest, ex: MethodArgumentNotValidException
    ): ResponseEntity<ApiCallError<Map<String, String?>>> {
        logger.error("handleMethodArgumentNotValidException {}\n", request.requestURI, ex)
        val details: MutableList<Map<String, String?>> = ArrayList()
        ex.bindingResult.fieldErrors.forEach(Consumer { fieldError: FieldError ->
                val detail: MutableMap<String, String?> = HashMap()
                detail["objectName"] = fieldError.objectName
                detail["field"] = fieldError.field
                detail["rejectedValue"] = "" + fieldError.rejectedValue
                detail["errorMessage"] = fieldError.defaultMessage
                details.add(detail)
            })
        return ResponseEntity.badRequest().body(ApiCallError("Method argument validation failed", details))
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(
        request: HttpServletRequest, ex: AccessDeniedException
    ): ResponseEntity<ApiCallError<String?>> {
        logger.error("handleAccessDeniedException {}\n", request.requestURI, ex)
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiCallError("Access denied!", listOf(ex.message)))
    }

    @ExceptionHandler(Exception::class)
    fun handleInternalServerError(request: HttpServletRequest, ex: Exception): ResponseEntity<ApiCallError<String?>> {
        logger.error("handleInternalServerError {}\n", request.requestURI, ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiCallError("Internal server error", listOf(ex.message)))
    }

    data class ApiCallError<T>(
        private val message: String? = null, private val details: List<T>? = null
    )
}

