import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/*
 * Trevor Benyack
 * CIT-244
 * Assignment 04
 * Due 2020-06-27
 */

// This BorderPane class displays all the products contained in the inventory.dat file as well as an additional pane
// where the user enters new products into the file.
public class GUI_02_AddInventoryItems extends BorderPane {

    // holds the inventory instance for the program
    Inventory inventory;

    // Textfields for product properties
    TextField tfProdId = new TextField();
    TextField tfProdDesc = new TextField();
    TextField tfProdPrice = new TextField();
    TextField tfQntyOnHand = new TextField();
    TextField tfQntyOnOrder = new TextField();
    TextField tfQntySold = new TextField();

    Button btSave = new Button("+");

    // creates a new instance of the QueryResults TableView to be displayed above ProductEntryPane
    GUI_03_InventoryQueryPane allProductsTableView;

    public GUI_02_AddInventoryItems(Inventory inventory) {

        this.inventory = inventory;

        // creates the pane that displays all of the products currently in the inventory.dat file
         allProductsTableView = new GUI_03_InventoryQueryPane(inventory, GUI_03_InventoryQueryPane.QueryType.ALL);

        // adds the TableView and data entry pane to this instance BorderPane
        this.setCenter(allProductsTableView);
        this.setBottom(getProductEntryPane());

        // Event handlers
        btSave.setOnAction(event -> {
            addEntry();
            clearFields();
            this.setCenter(new GUI_03_InventoryQueryPane(inventory, GUI_03_InventoryQueryPane.QueryType.ALL));
        });

    }

    // returns the dataEntryPane for product entry
    private GridPane getProductEntryPane() {
        GridPane gridPane = new GridPane();

        gridPane.add(new Label("ID:"), 0, 0);
        gridPane.add(tfProdId, 0, 1);
        gridPane.add(new Label("Description:"), 1, 0);
        gridPane.add(tfProdDesc, 1, 1);
        gridPane.add(new Label("Price:"), 2, 0);
        gridPane.add(tfProdPrice, 2, 1);
        gridPane.add(new Label("Qty On Hand:"), 3, 0);
        gridPane.add(tfQntyOnHand, 3, 1);
        gridPane.add(new Label("Qty On Order"), 4, 0);
        gridPane.add(tfQntyOnOrder, 4, 1);
        gridPane.add(new Label("Qty Sold:"), 5, 0);
        gridPane.add(tfQntySold, 5, 1);
        gridPane.add(btSave, 6, 1);

        // UI Properties
        btSave.setAlignment(Pos.BOTTOM_CENTER);

        return gridPane;
    }

    // copies the data from the dataEntryPane fields into a newly created Product object, and sends that Product object to
    // Inventory.writeRecordsOutputStream to be written to the inventory.dat file.
    private void addEntry() {

        // assigns data to product object properties
        Product product = new Product();
        product.setProdId(Integer.parseInt(tfProdId.getText()));
        product.setProdDesc(tfProdDesc.getText());
        // strips the $ if entered
        if (tfProdPrice.getText().startsWith("$")) {
            product.setProdPrice(tfProdPrice.getText().substring(1));
        } else {
            product.setProdPrice(tfProdPrice.getText());
        }
        product.setQntyOnHand(Integer.parseInt(tfQntyOnHand.getText()));
        product.setQntyOnOrder(Integer.parseInt(tfQntyOnOrder.getText()));
        product.setQntySold(Integer.parseInt(tfQntySold.getText()));

        // writes the data to file
        inventory.openOutputDataStreamAppend();
        inventory.writeRecordsOutputStream(product);
        inventory.closeOutputStream();

    }

    // clears the dataEntryPane fields after the data has been saved to the inventory.dat file.
    private void clearFields() {
        tfProdId.clear();
        tfProdDesc.clear();
        tfProdPrice.clear();
        tfQntyOnHand.clear();
        tfQntyOnOrder.clear();
        tfQntySold.clear();
    }
}
