@LandingPageTest
Feature: Landing Page tests
    As Customer, I can view and Select products from the page

    Background: Landing Page of Softwaretestingboard.com
        Given User is on Landing page

    @ProductSearch
    Scenario: User seacrhes for a product
        Given Landing page is displayed
        When User seacrhes product "Jeans" from the search box
        Then User should see key "Jeans" in all results

    @TopNavHover
    Scenario: User selects a product from top nav menu
        Given Landing page is displayed
        When User moves cursor to top nav men menu
        Then User sees "Tops" and "Bottoms" as suggestions from top nav
        When User mover cursor to Bottoms
        Then User sees "Pants" and "Shorts" as suggestions from submenu
        When User clicks on shorts from suggestions
        Then User lands on "Shorts" title page

    @E2EProductCheckout
    Scenario: User selects a product and place an order
        Given Landing page is displayed
        When User seacrhes product "Jacket" from the search box
        And User selects an item from suggestions
        Then User lands on "Jacket" title page
        When User selects a product and proceed to add it to cart
        And User selects size and color of the item and add it to the cart
        Then User sees item gets added to the cart
        And User performs proceed to checkout action
        Then User lands on "Checkout" title page
        And User clicks on add new address
        And User fill in address details
        

