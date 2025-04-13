package com.example.simpletradingapp.model;

public class CompanyManager {
    private List<InventoryItem> items;
    private List<Category> categories;

    // Constructor with sample data
    public CompanyManager() {
        // Initialize lists
        items = new ArrayList<>();
        categories = new ArrayList<>();

        // Create sample categories
        Category electronics = new Category(1L, "Electronics");
        Category furniture = new Category(2L, "Furniture");
        Category office = new Category(3L, "Office Supplies");

        // Add categories to list
        categories.add(electronics);
        categories.add(furniture);
        categories.add(office);

        // Create sample items
        items.add(new InventoryItem(1L, "Laptop", "Dell XPS 13", 5, 999.99, electronics));
        items.add(new InventoryItem(2L, "Smartphone", "iPhone 14", 10, 799.99, electronics));
        items.add(new InventoryItem(3L, "Desk", "Oak Office Desk", 3, 249.99, furniture));
        items.add(new InventoryItem(4L, "Chair", "Ergonomic Chair", 8, 179.99, furniture));
        items.add(new InventoryItem(5L, "Notebook", "Spiral Bound", 20, 4.99, office));
    }

    // Get all inventory items
    public List<InventoryItem> getAllItems() {
        return items;
    }

    // Get all categories
    public List<Category> getAllCategories() {
        return categories;
    }

    // Find item by ID
    public InventoryItem getItemById(Long id) {
        for (InventoryItem item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    // Find items by category
    public List<InventoryItem> getItemsByCategory(Long categoryId) {
        List<InventoryItem> result = new ArrayList<>();

        for (InventoryItem item : items) {
            if (item.getCategory() != null && item.getCategory().getId().equals(categoryId)) {
                result.add(item);
            }
        }

        return result;
    }
}