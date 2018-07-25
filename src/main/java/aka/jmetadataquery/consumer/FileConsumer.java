package aka.jmetadataquery.consumer;

import java.util.function.Consumer;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Consumer.
 *
 * @author charlottew
 */
public class FileConsumer implements Consumer<@NonNull FileConsumerContext> {

    @Override
    public void accept(@NonNull final FileConsumerContext fileConsumerContext) {
        fileConsumerContext.process();
    }
}
