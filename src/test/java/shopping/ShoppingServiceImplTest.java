package shopping;

import customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import product.Product;
import product.ProductDao;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ShoppingServiceImplTest {

    @Mock
    private ProductDao productDaoMock;

    @InjectMocks
    private ShoppingServiceImpl shoppingService;

    @BeforeEach
    void setUp() {
    }

    /**
     * Тест метода {@link ShoppingServiceImpl#getCart(Customer)}
     */
    @Test
    void getCart() {
        Cart actualCart = shoppingService.getCart(new Customer(1L, "11-11-11"));
        Cart expectedCart = new Cart(new Customer(1L, "11-11-11"));
        Cart wrongCart = new Cart(new Customer(2L, "22-22-22"));
        assertEquals(expectedCart, actualCart);
        assertNotEquals(wrongCart, actualCart);
    }

    /**
     * Тест метода {@link ShoppingServiceImpl#getAllProducts()}
     */
    @Test
    void getAllProducts() {
        Product product = new Product();
        List<Product> products = List.of(product);
        Mockito.when(productDaoMock.getAll()).thenReturn(products);
        List<Product> actualProducts = shoppingService.getAllProducts();
        List<Product> expectedProducts = List.of(new Product());
        Mockito.verify(productDaoMock, Mockito.times(1)).getAll();
        assertEquals(expectedProducts, actualProducts);
    }

    /**
     * Тест метода {@link ShoppingServiceImpl#getProductByName(String)}
     */
    @Test
    void getProductByName() {
        Product product = new Product();
        product.setName("Wheel");
        Mockito.when(productDaoMock.getByName("Wheel")).thenReturn(product);
        Product actualProduct = shoppingService.getProductByName("Wheel");
        Product expectedProduct = new Product();
        expectedProduct.setName("Wheel");
        Mockito.verify(productDaoMock, Mockito.times(1)).getByName("Wheel");
        assertEquals(expectedProduct, actualProduct);
    }

    /**
     * Тест метода {@link ShoppingServiceImpl#buy(Cart)}
     * Протестированы случаи: 1. Когда корзина пуста
     * 2. Когда все в порядке
     * 3. Когда товара не хватает
     */
    @Test
    void buy() throws BuyException {
        Product wheels = new Product();
        wheels.setName("Wheel");
        wheels.addCount(20);
        Product phones = new Product();
        phones.setName("Phone");
        phones.addCount(10);
        Cart cart = new Cart(new Customer(1L, "11-11-11"));
        boolean isSuccess = shoppingService.buy(cart);
        assertFalse(isSuccess);
        cart.add(wheels, 15);
        cart.add(phones, 8);

        isSuccess = shoppingService.buy(cart);

        assertTrue(isSuccess);
        assertEquals(5, wheels.getCount());
        assertEquals(2, phones.getCount());

        assertThrows(BuyException.class, () -> {
            boolean failBuy = shoppingService.buy(cart);
            assertFalse(failBuy);
        });

    }
}