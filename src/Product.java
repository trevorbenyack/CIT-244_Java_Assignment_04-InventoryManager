import java.math.BigDecimal;

/*
 * Trevor Benyack
 * CIT-244
 * Assignment 04
 * Due 2020-06-27
 */

// This class is used to hold the information for a given product. I chose to move the calculations fields
// into this class because the results for each were only relevant for each individual product instance.

public class Product{

    private int prodId;
    private String prodDesc;
    private String prodPrice;
    private int qntyOnHand;
    private int qntyOnOrder;
    private int qntySold;

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public int getQntyOnHand() {
        return qntyOnHand;
    }

    public void setQntyOnHand(int qntyOnHand) {
        this.qntyOnHand = qntyOnHand;
    }

    public int getQntyOnOrder() {
        return qntyOnOrder;
    }

    public void setQntyOnOrder(int qntyOnOrder) {
        this.qntyOnOrder = qntyOnOrder;
    }

    public int getQntySold() {
        return qntySold;
    }

    public void setQntySold(int qntySold) {
        this.qntySold = qntySold;
    }

    public int getCurProdInvt(){
        return qntyOnHand + qntyOnOrder - qntySold;
    }

    public BigDecimal getTotalProdInvtValue() {
        BigDecimal prodPriceBD = new BigDecimal(prodPrice);
        return prodPriceBD.multiply(new BigDecimal(getCurProdInvt()));
    }
}
