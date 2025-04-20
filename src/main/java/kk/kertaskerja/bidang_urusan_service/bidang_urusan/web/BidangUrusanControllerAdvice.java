package kk.kertaskerja.bidang_urusan_service.bidang_urusan.web;

import kk.kertaskerja.bidang_urusan_service.bidang_urusan.domain.BidangUrusanAlreadyExistsException;
import kk.kertaskerja.bidang_urusan_service.common.exception.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.support.ContextExposingHttpServletRequest;
import org.springframework.web.service.invoker.ReactiveHttpRequestValues;

import java.time.Instant;

@RestControllerAdvice
public class BidangUrusanControllerAdvice {
    @ExceptionHandler(BidangUrusanAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    ApiError handleBidangUrusanAlreadyExistsException(BidangUrusanAlreadyExistsException ex, ServerHttpRequest request) {
        return new ApiError(
          HttpStatus.UNPROCESSABLE_ENTITY.value(),
                ex.getMessage(),
                Instant.now(),
                request.getPath().pathWithinApplication().value()
        );
    }
}
