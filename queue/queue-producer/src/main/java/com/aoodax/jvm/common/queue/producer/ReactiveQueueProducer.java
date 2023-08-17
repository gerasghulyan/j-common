package com.aoodax.jvm.common.queue.producer;

import com.aoodax.jvm.common.queue.message.Queueable;
import reactor.core.publisher.Mono;

public interface ReactiveQueueProducer {
    Mono<Void> produce(final String topicName, final Queueable event, final String subject);
}
