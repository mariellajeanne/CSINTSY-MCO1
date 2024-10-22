// C.java
package solver;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

class C<T> {
    private final T object;

    public C(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        C<?> that = (C<?>) other;
        return EqualsBuilder.reflectionEquals(this.object, that.object);  // Deep comparison of C
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this.object);  // Hashcode based on C
    }
}
