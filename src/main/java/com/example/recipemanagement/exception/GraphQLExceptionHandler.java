package com.example.recipemanagement.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    private static final Logger logger = LoggerFactory.getLogger(GraphQLExceptionHandler.class);

    @Override
    protected List<GraphQLError> resolveToMultipleErrors(Throwable exception, DataFetchingEnvironment environment) {
        if (exception instanceof EntityNotFoundException) {
            return Collections.singletonList(
                    GraphqlErrorBuilder.newError()
                            .message(exception.getMessage())
                            .location(environment.getField().getSourceLocation())
                            .path(environment.getExecutionStepInfo().getPath())
                            .errorType(ErrorType.DataFetchingException)
                            .build()
            );
        } else if (exception instanceof ValidationException) {
            // Handle validation errors
            return Collections.singletonList(
                    GraphqlErrorBuilder.newError()
                            .message(exception.getMessage())
                            .errorType(ErrorType.ValidationError)
                            .build()
            );
        }

        // Log unexpected errors
        logger.error("Unexpected error during GraphQL execution", exception);
        return Collections.singletonList(
                GraphqlErrorBuilder.newError()
                        .message("An unexpected error occurred")
                        .errorType(ErrorType.ExecutionAborted)
                        .build()
        );
    }
}