package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UppercaseTaskStorage {

    public static final UppercaseTaskStorage INSTANCE = new UppercaseTaskStorage();

    private final Map<String, UppercaseTask> storage = new HashMap<>();

    private UppercaseTaskStorage() {
    }

    public Optional<UppercaseTask> read(final String id) {
        final var uppercaseTask = this.storage.get(id);

        return Optional.ofNullable(uppercaseTask);
    }

    public String create(final String text) {
        final var id = UUID.randomUUID().toString();
        final var uppercaseTask = new UppercaseTask(id, text, null);
        this.storage.put(id, uppercaseTask);

        return id;
    }

    public void update(final String id, final String text) {
        this.read(id)
                .map(uppercaseTask -> new UppercaseTask(id, uppercaseTask.request(), text))
                .ifPresent(uppercaseTask -> this.storage.put(id, uppercaseTask));
    }
}
