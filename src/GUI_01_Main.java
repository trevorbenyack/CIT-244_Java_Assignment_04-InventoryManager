import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

/*
 * Trevor Benyack
 * CIT-244
 * Assignment 04
 * Due 2020-06-27
 */


// This BorderPane class sets up the base pane for the program. It utilizes a menu bar for navigation through the UI.
public class GUI_01_Main extends BorderPane {

    // Menu bar items
    MenuBar menuBar = new MenuBar();
    Menu menuInventory = new Menu("Inventory Manager");
        MenuItem menuitemAddProduct = new MenuItem("Add New Products to Inventory");
        MenuItem menuitemExit = new MenuItem("Exit");
    Menu menuInventoryQueries = new Menu("Inventory Queries");
        MenuItem menuitemAllRecords = new MenuItem("Show All Inventory Items");
        MenuItem menuitemInventoryOver75 = new MenuItem("Products w/ Stock Count Over 75");
        MenuItem menuitemInventory75orLess = new MenuItem("Products w/ Stock Count of 75 or Less");
        MenuItem menuitemInventoryProdValueOver250 = new MenuItem("Products w/ inventory value over $250.00");

    public GUI_01_Main(Inventory inventory){

        // sets class ID to "mainPane" for use in stylesheet.css
        this.setId("mainPane");

        // adds the menu bar to the main pane
        this.setTop(getMenuBar());

        // sets up event handlers for the Inventory Manager menuItems
        menuitemExit.setOnAction(e -> System.exit(0));
        menuitemAddProduct.setOnAction(e -> {
            this.setCenter(new GUI_02_AddInventoryItems(inventory));
        });

        // sets up event handlers for the Inventory Queries menuItems
        menuitemAllRecords.setOnAction(e -> {
            this.setCenter(new GUI_03_InventoryQueryPane(inventory, GUI_03_InventoryQueryPane.QueryType.ALL));
        });
        menuitemInventoryOver75.setOnAction((e -> {
            this.setCenter(new GUI_03_InventoryQueryPane(inventory, GUI_03_InventoryQueryPane.QueryType.MORE75));
        }));
        menuitemInventory75orLess.setOnAction(e -> {
            this.setCenter(new GUI_03_InventoryQueryPane(inventory, GUI_03_InventoryQueryPane.QueryType.LESSOR75));
        });
        menuitemInventoryProdValueOver250.setOnAction(e -> {
            this.setCenter(new GUI_03_InventoryQueryPane(inventory, GUI_03_InventoryQueryPane.QueryType.INVTVALUEOVER250));
        });
    }

    // creates and returns the main menu bar
    private MenuBar getMenuBar() {

        // adds top-level menus to the menu bar
        menuBar.getMenus().addAll(menuInventory,menuInventoryQueries);

        // adds submenu items
        menuInventory.getItems().addAll(menuitemAddProduct, menuitemExit);
        menuInventoryQueries.getItems().addAll(
                menuitemAllRecords,
                menuitemInventoryOver75,
                menuitemInventory75orLess,
                menuitemInventoryProdValueOver250);

        return menuBar;
    }


}
