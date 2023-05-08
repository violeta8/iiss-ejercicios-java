import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ShoppingCart {
	
	Map<Product, Integer> shoppingCart;
	
	public ShoppingCart() {
		shoppingCart = new HashMap<Product, Integer>();
	}
	
	public void addProduct(Product product, int number) {
		Optional<Product> optionalProduct = Optional.ofNullable(product);
		if(number > 0 && optionalProduct.isPresent() && shoppingCart.keySet().stream().noneMatch(element -> element.getCode() == product.getCode())) {
			shoppingCart.put(product, number);
		}
	}
	
	public Optional<Product> removeProduct(Product product) {
		Optional<Product> optionalProduct = Optional.ofNullable(product);
		if(optionalProduct.isPresent() && shoppingCart.containsKey(product)) {
			shoppingCart.remove(product);
			return optionalProduct;
		}  else {
			return Optional.empty();
		}
	}
	
	public void printShoppingCartContent() {
		System.out.println("The shopping cart content is: ");
		
		for(Product product: shoppingCart.keySet()) {
			System.out.println(product.getCode() + " - " + product.getName() + " : " + shoppingCart.get(product));
		}
		
	}
}