package product;

import java.util.Objects;

/**
 * Товар
 *
 * @author vpyzhyanov
 * @since 16.05.2023
 */
public class Product {
    /**
     * Название
     */
    private String name;
    /**
     * Доступное количество
     */
    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    /**
     * Добавить количество товаров
     * @param count значение, на которое нужно увеличить количество товаров
     */
    public void addCount(int count) {
        this.count += count;
    }

    /**
     * Вычесть количество товаров
     * @param count значение, на которое нужно уменьшить количество товаров
     */
    public void subtractCount(int count) {
        addCount(-count);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return count == product.count && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, count);
    }
}
