import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.io.EOFException;
import java.math.BigDecimal;

/*
 * Trevor Benyack
 * CIT-244
 * Assignment 04
 * Due 2020-06-27
 */

// this StackPane class creates a TableView that displays products that meet the query criteria defined
// in the assignment handout:
//  - All products
//  - Products that have a current inventory stock over 75
//  - Products that have a current inventory of 75 or less
//  - Products whose current inventory value is over $250.00
public class GUI_03_InventoryQueryPane extends StackPane {

    Inventory inventory;

    TableView<ProductObservable> tableQueryResults = new TableView<>();

    ObservableList<ProductObservable> data = FXCollections.observableArrayList();

    public enum QueryType { ALL, LESSOR75, MORE75, INVTVALUEOVER250}

    // receives the program's inventory instance so that the GUI can call it to read/write to file
    // receives queryType in order to populate the TableView with the corresponding query results.
    public GUI_03_InventoryQueryPane(Inventory inventory, QueryType queryType) {

        this.inventory = inventory;

        // links the data ObservableList to the table
        tableQueryResults.setItems(data);

        // seeds data ObservableList with products that meet query criteria
        inventoryQueries(queryType);

        // creates columns
        TableColumn productNumColumn = new TableColumn("ID");
        productNumColumn.setCellValueFactory(new PropertyValueFactory<>("prodId"));
        TableColumn prodDescColumn = new TableColumn("Description");
        prodDescColumn.setCellValueFactory(new PropertyValueFactory<>("prodDesc"));
        TableColumn prodPriceColumn = new TableColumn("Price");
        prodPriceColumn.setCellValueFactory(new PropertyValueFactory<>("prodPrice"));
        TableColumn qntyOnHandColumn = new TableColumn("Quantity\non Hand");
        qntyOnHandColumn.setCellValueFactory(new PropertyValueFactory<>("qntyOnHand"));
        TableColumn qntyOnOrderColumn = new TableColumn("Quantity\non Order");
        qntyOnOrderColumn.setCellValueFactory(new PropertyValueFactory<>("qntyOnOrder"));
        TableColumn qntySoldColumn = new TableColumn("Quantity\nSold");
        qntySoldColumn.setCellValueFactory(new PropertyValueFactory<>("qntySold"));
        TableColumn curProdInvtColumn = new TableColumn("Current \nInventory");
        curProdInvtColumn.setCellValueFactory(new PropertyValueFactory<>("curProdInvt"));
        TableColumn totalProdInvtValColumn = new TableColumn("Inventory\nValue");
        totalProdInvtValColumn.setCellValueFactory(new PropertyValueFactory<>("totalProdInvtVal"));


        // adds columns to table
        tableQueryResults.getColumns().addAll(
                productNumColumn,
                prodDescColumn,
                prodPriceColumn,
                qntyOnHandColumn,
                qntyOnOrderColumn,
                qntySoldColumn,
                curProdInvtColumn,
                totalProdInvtValColumn);

        // adds queryResults table to the pane
        this.getChildren().add(tableQueryResults);

        // UI Properties
        tableQueryResults.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableQueryResults.prefHeightProperty().bind(this.heightProperty());
        productNumColumn.maxWidthProperty().bind(this.widthProperty().multiply(0.05));
        prodDescColumn.maxWidthProperty().bind(this.widthProperty().multiply(.35));
        prodPriceColumn.maxWidthProperty().bind(this.widthProperty().multiply(.1));
        qntyOnHandColumn.maxWidthProperty().bind(this.widthProperty().multiply(.1));
        qntyOnOrderColumn.maxWidthProperty().bind(this.widthProperty().multiply(.1));
        qntySoldColumn.maxWidthProperty().bind(this.widthProperty().multiply(.1));
        curProdInvtColumn.maxWidthProperty().bind(this.widthProperty().multiply(.1));
        totalProdInvtValColumn.maxWidthProperty().bind(this.widthProperty().multiply(.1));

    } // end GUI_03_InventoryQueryPane constructor


    // Provides logic for pre-defined queries
    // The function reads from the inventory file in a Product by Product order and then sends the product
    // to the addProductToView function which adds it to the data ObservableList that automatically updates the
    // tableQueryResults TableView
    // This function catches the EOFException thrown by readInputDataStream() when the end of the file is reached
    // in order to exit the while-loop as well as the function itself.
    public void inventoryQueries(QueryType queryType) {
        inventory.openInputDataStream();
        try {
            while (true) {
                Product product = inventory.readInputDataStream();

                switch (queryType) {
                    case ALL: addProductToView(product); break;
                    case MORE75: {
                        if (product.getCurProdInvt() > 75)
                            addProductToView(product);
                    } break;
                    case LESSOR75: {
                        if (product.getCurProdInvt() <= 75)
                            addProductToView(product);
                    } break;
                    case INVTVALUEOVER250: {
                        if (product.getTotalProdInvtValue().compareTo(new BigDecimal(250)) > 0)
                            addProductToView(product);
                    } break;
                }
            }
        } catch (EOFException endOfFileException) {
            System.out.println("End of file reached");
        }
        inventory.closeInputStream();

    }

    // adds a product object to the data ObservableList which then automatically updates the data TableView
    // with the product properties
    public void addProductToView(Product product) {
        data.add(new ProductObservable(
                product.getProdId(),
                product.getProdDesc(),
                "$" + product.getProdPrice(),
                product.getQntyOnHand(),
                product.getQntyOnOrder(),
                product.getQntySold(),
                product.getCurProdInvt(),
                product.getTotalProdInvtValue().toString()));
    }

    // model class for table data
    public static class ProductObservable{

        private final SimpleIntegerProperty prodId;
        private final SimpleStringProperty prodDesc;
        private final SimpleStringProperty prodPrice;
        private final SimpleIntegerProperty qntyOnHand;
        private final SimpleIntegerProperty qntyOnOrder;
        private final SimpleIntegerProperty qntySold;
        private final SimpleIntegerProperty curProdInvt;
        private final SimpleStringProperty totalProdInvtVal;

        public ProductObservable(
                int prodId,
                String prodDesc,
                String prodPrice,
                int qntyOnHand,
                int qntyOnOrder,
                int qntySold,
                int curProdInvt,
                String totalProdInvValue) {
            this.prodId = new SimpleIntegerProperty(prodId);
            this.prodDesc = new SimpleStringProperty(prodDesc);
            this.prodPrice = new SimpleStringProperty(prodPrice);
            this.qntyOnHand = new SimpleIntegerProperty(qntyOnHand);
            this.qntyOnOrder = new SimpleIntegerProperty(qntyOnOrder);
            this.qntySold = new SimpleIntegerProperty(qntySold);
            this.curProdInvt = new SimpleIntegerProperty(curProdInvt);
            this.totalProdInvtVal = new SimpleStringProperty(totalProdInvValue);
        }

        public int getProdId() {
            return prodId.get();
        }

        public SimpleIntegerProperty prodIdProperty() {
            return prodId;
        }

        public void setProdId(int prodId) {
            this.prodId.set(prodId);
        }

        public String getProdDesc() {
            return prodDesc.get();
        }

        public SimpleStringProperty prodDescProperty() {
            return prodDesc;
        }

        public void setProdDesc(String prodDesc) {
            this.prodDesc.set(prodDesc);
        }

        public String getProdPrice() {
            return prodPrice.get();
        }

        public SimpleStringProperty prodPriceProperty() {
            return prodPrice;
        }

        public void setProdPrice(String prodPrice) {
            this.prodPrice.set(prodPrice);
        }

        public int getQntyOnHand() {
            return qntyOnHand.get();
        }

        public SimpleIntegerProperty qntyOnHandProperty() {
            return qntyOnHand;
        }

        public void setQntyOnHand(int qntyOnHand) {
            this.qntyOnHand.set(qntyOnHand);
        }

        public int getQntyOnOrder() {
            return qntyOnOrder.get();
        }

        public SimpleIntegerProperty qntyOnOrderProperty() {
            return qntyOnOrder;
        }

        public void setQntyOnOrder(int qntyOnOrder) {
            this.qntyOnOrder.set(qntyOnOrder);
        }

        public int getQntySold() {
            return qntySold.get();
        }

        public SimpleIntegerProperty qntySoldProperty() {
            return qntySold;
        }

        public void setQntySold(int qntySold) {
            this.qntySold.set(qntySold);
        }

        public int getCurProdInvt() {
            return curProdInvt.get();
        }

        public SimpleIntegerProperty curProdInvtProperty() {
            return curProdInvt;
        }

        public void setCurProdInvt(int curProdInvt) {
            this.curProdInvt.set(curProdInvt);
        }

        public String getTotalProdInvtVal() {
            return totalProdInvtVal.get();
        }

        public SimpleStringProperty totalProdInvtValProperty() {
            return totalProdInvtVal;
        }

        public void setTotalProdInvtVal(String totalProdInvtVal) {
            this.totalProdInvtVal.set(totalProdInvtVal);
        }
    }


}
