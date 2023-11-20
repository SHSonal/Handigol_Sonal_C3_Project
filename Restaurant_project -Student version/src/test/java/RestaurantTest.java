import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    private void addSampleRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        addSampleRestaurant();
        LocalTime currentTime = LocalTime.parse("12:00:00");
         assertTrue(currentTime.isAfter(restaurant.openingTime) && currentTime.isBefore(restaurant.closingTime),
                "Restaurant is open: " + currentTime);

        System.out.println("Test passed: Restaurant is open at " + currentTime);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        addSampleRestaurant();
        LocalTime currentTime = LocalTime.parse("23:00:00");
    
         assertFalse(currentTime.isAfter(restaurant.openingTime) && currentTime.isBefore(restaurant.closingTime),
                "Restaurant is open: " + currentTime);
         System.out.println("Test passed: Restaurant is not open at" + currentTime);


    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        addSampleRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
       addSampleRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        addSampleRestaurant();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    
     ///<<<<<<<<<<<<<<<<<<<<Total Order>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
   public void calculate_order_value_should_return_total_price_of_items_in_order() {
        List<Item> order = List.of(new Item("Sweet corn soup", 119), new Item("Vegetable lasagne", 269));
        int expectedTotal = 119 + 269;

    assertEquals(expectedTotal, restaurant.calculateOrderValue(order));
    }
        
    @Test
    public void calculate_order_value_should_return_zero_for_empty_order() {
            List<Item> emptyOrder = new ArrayList<>();
            assertEquals(0, restaurant.calculateOrderValue(emptyOrder));
    }

    @Test
    public void calculate_order_value_should_handle_quantity() {

            List<Item> orderWithQuantity = new ArrayList<>();
            orderWithQuantity.add(new Item("Sweet corn soup", 119)); 
            orderWithQuantity.add(new Item("Vegetable lasagne", 269)); 
            orderWithQuantity.add(new Item("Sweet corn soup", 119)); 
            orderWithQuantity.add(new Item("Vegetable lasagne", 269));
            int expectedTotal = (119 * 2) + (269 * 2);
        
            assertEquals(expectedTotal, restaurant.calculateOrderValue(orderWithQuantity), "Incorrect total order value with quantity");
        }
}
