package com.aoodax.jvm.common.queue.producer.sns;

import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertHasTextParameterArgument;
import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertNotNullParameterArgument;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.aoodax.jvm.common.queue.message.Queueable;
import com.aoodax.jvm.common.queue.producer.ReactiveQueueProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public class ReactiveSnsProducerIntegrationClient implements ReactiveQueueProducer {

    private final NotificationMessagingTemplate notificationMessagingTemplate;
    private final Executor executor;

    @Override
    public Mono<Void> produce(final String topicName, final Queueable event, final String subject) {
        assertHasTextParameterArgument(topicName, "topicName");
        assertHasTextParameterArgument(subject, "subject");
        assertNotNullParameterArgument(event, "event");
        log.debug("publishing event - {} into sns topic - {} with subject - {}", event, topicName, subject);
        return Mono.fromFuture(() -> CompletableFuture.runAsync(() -> {
            notificationMessagingTemplate.sendNotification(
                    topicName,
                    event,
                    subject
            );
            log.debug("Successfully published event - {} into sns topic - {} with subject - {}", event, topicName, subject);
        }, executor));
    }
}
