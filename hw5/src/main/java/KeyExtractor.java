public interface KeyExtractor<E, T> {
    T extract(E entity);
}
