package ru.sbt.mipt.hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleEntitiesStorage<K, V> implements BankEntitiesStorage<K, V> {
    private final Map<K, V> storage = new HashMap<>();
    private final KeyExtractor<V, K> keyExtractor;

    public SimpleEntitiesStorage(KeyExtractor<V, K> keyExtractor) {
        this.keyExtractor = keyExtractor;
    }

    @Override
    public void save(V entity) {
        storage.put(keyExtractor.extract(entity), entity);
    }

    @Override
    public void saveAll(List<? extends V> entities) {
        entities.forEach(entity -> storage.put(keyExtractor.extract(entity), entity));
    }

    @Override
    public V findByKey(K key) {
        return storage.get(key);
    }

    @Override
    public List<V> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteByKey(K key) {
        storage.remove(key);
    }

    @Override
    public void deleteAll(List<? extends V> entities) {
        entities.forEach(entity -> storage.remove(keyExtractor.extract(entity)));
    }

    @Override
    public String toString() {
        return '{' + "storage=" + storage + '}';
    }
}
