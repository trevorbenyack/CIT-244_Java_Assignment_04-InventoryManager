import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Trevor Benyack
 * CIT-244
 * Assignment 04
 * Due 2020-06-27
 */


// This class contains all the file I/O logic for the inventory.dat file that the program uses to store the inventory
// data
public class Inventory {

    private DataInputStream inputRecord;

    private DataOutputStream outputRecord;

    static File inventoryFile;

    public Inventory() {

        // creates a new inventory.dat file if one does not already exist.
        try {
            inventoryFile = new File("inventory.dat");
            if(!inventoryFile.exists()) {
                inventoryFile.createNewFile();
            }
        } catch (IOException ioException) {
            System.out.println("An error occurred.");
            ioException.printStackTrace();
        }
    }

    // Returns a DataInputStream object for the inventory file if it is available. If not, program exits.
    public void openInputDataStream() {
        // Define the DataInputStream
        try {
            inputRecord = new DataInputStream(new FileInputStream(inventoryFile));
        } catch (SecurityException securityError) {
            System.out.println("Security Exception Error: " + securityError);
            System.exit(1);
        } catch (IOException ioExceptionError) {
            System.out.println("IO Exception Error: " + ioExceptionError);
            System.exit(2);
        }
    }

    // Returns a DataOutputStream object for the inventory file if it is available. If not, program exits.
    public void openOutputDataStreamAppend() {
        // Define the DataOutputStream
        try {
            outputRecord = new DataOutputStream(new FileOutputStream(inventoryFile, true));
        } catch (SecurityException securityException) {
            System.out.println("Security Exception Error " + securityException);
            System.exit(1);
        } catch (IOException ioException) {
            System.out.println("IO Exception Error " + ioException);
            System.exit(2);
        }
    }

    // Reads enough of inputRecord to assign the data for one Product to a new instance of Product and returns it.
    // Returns null at end because this function should never get to the last return statement:
    // it throws an EOFException to its calling function to allow the calling function to know whether to call this
    // function again or not, otherwise it returns the product or throws an IOException and exits the program.
    public Product readInputDataStream() throws EOFException{

            try {
                Product product = new Product();
                product.setProdId(inputRecord.readInt());
                product.setProdDesc(inputRecord.readUTF());
                product.setProdPrice(inputRecord.readUTF());
                product.setQntyOnHand(inputRecord.readInt());
                product.setQntyOnOrder(inputRecord.readInt());
                product.setQntyOnOrder(inputRecord.readInt());
                return product;
            } catch (EOFException eofException) {
                throw new EOFException();
            } catch (IOException ioExceptionError) {
                System.out.println("IO Exception Error " + ioExceptionError);
                System.exit(2);
            }
            return null;
        }

    // Receives a Product object and appends its data fields to the inventory.dat file
    public void writeRecordsOutputStream(Product product) {

        try {
            outputRecord.writeInt(product.getProdId());
            outputRecord.writeUTF(product.getProdDesc());
            outputRecord.writeUTF(product.getProdPrice());
            outputRecord.writeInt(product.getQntyOnHand());
            outputRecord.writeInt(product.getQntyOnOrder());
            outputRecord.writeInt(product.getQntySold());
        } catch (IOException ioExceptionError) {
            System.out.println("IO Exception Error " + ioExceptionError);
            System.exit(2);
        }

    }

    public void closeInputStream()
    {
        try
        {
            inputRecord.close();
        }
        catch (IOException exceptionError)
        {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, exceptionError);
        }
    }

    public void closeOutputStream()
    {
        try
        {
            outputRecord.close();
        }
        catch (IOException exceptionError)
        {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, exceptionError);
        }

    }

} // end Inventory class



