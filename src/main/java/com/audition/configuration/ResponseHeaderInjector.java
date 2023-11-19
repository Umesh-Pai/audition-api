package com.audition.configuration;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings({"PMD.BeanMembersShouldSerialize"})
public class ResponseHeaderInjector implements Filter {

    @Autowired
    Tracer trace;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
        final FilterChain chain) throws IOException, ServletException {
        final Span span = trace.currentSpan();
        if (null != span) {
            final HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("X-TraceId", span.context().traceId());
            res.setHeader("X-SpanId", span.context().spanId());
        }
        chain.doFilter(request, response);
    }

}
