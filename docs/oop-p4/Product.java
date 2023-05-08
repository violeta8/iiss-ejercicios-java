import java.util.Optional;

public class Product {
	
	private int code;
	private String name;
	private String category;
	private double weight;
	private double height;
	
	public Product(int code, String name, String category, double weight, double height) {
		
		if (code < 0 || weight < 0 || height < 0) {
			throw new IllegalArgumentException("Invalid argument: code, weight and height must be positive.");
		}
		
		Optional<String> optName = Optional.ofNullable(name);
		this.name = optName.orElse("");
		
		Optional<String> optCategory = Optional.ofNullable(category);
		this.category = optCategory.orElse("");
		
		this.code = code;
		this.weight = weight;
		this.height = height;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setName(String name) {
		Optional<String> optName = Optional.ofNullable(name);
		this.name = optName.orElse("");
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setCategory(String category) {
		Optional<String> optCategory = Optional.ofNullable(category);
		this.category = optCategory.orElse("");
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public void setWeight(double weight) {
		if (weight < 0) {
			throw new IllegalArgumentException("Invalid argument: weight must be positive.");
		}
		
		this.weight = weight;
	}
	
	public double getWeight() {
		return this.weight;
	}
	
	public void setHeight(double height) {
		if (height < 0) {
			throw new IllegalArgumentException("Invalid argument: height must be positive.");
		}
		
		this.height = height;
	}
	
	public double getHeight() {
		return this.height;
	}
}